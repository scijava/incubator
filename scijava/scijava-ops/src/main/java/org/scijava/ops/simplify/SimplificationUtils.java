package org.scijava.ops.simplify;

import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.matcher.MatchingUtils;
import org.scijava.ops.matcher.OpRef;
import org.scijava.ops.util.AnnotationUtils;
import org.scijava.param.Mutable;
import org.scijava.param.ParameterStructs;
import org.scijava.types.Nil;
import org.scijava.types.Types;

public class SimplificationUtils {

	/**
	 * Determines the {@link Type} of a retyped Op using its old {@code Type}, a
	 * new set of {@code args} and a new {@code outType}. Used to create
	 * {@link SimplifiedOpRef}s. This method assumes that:
	 * <ul>
	 * <li>{@code originalOpRefType} is (or is a subtype of) some
	 * {@link FunctionalInterface}
	 * <li>all {@link TypeVariable}s declared by that {@code FunctionalInterface}
	 * are present in the signature of that interface's single abstract method.
	 * </ul>
	 * 
	 * @param originalOpType - the {@link Type} declared by the source
	 *          {@link OpRef}
	 * @param newArgs - the new argument {@link Type}s requested by the
	 *          {@link OpRef}.
	 * @param newOutType - the new output {@link Type} requested by the
	 *          {@link OpRef}.
	 * @return - a new {@code type} for a {@link SimplifiedOpRef}.
	 */
	public static ParameterizedType retypeOpType(Type originalOpType, Type[] newArgs, Type newOutType) {
			// only retype types that we know how to retype
			if (!(originalOpType instanceof ParameterizedType))
				throw new IllegalStateException("We hadn't thought about this yet.");
			Class<?> opType = Types.raw(originalOpType);
			Method fMethod = findFMethod(opType);

			Map<TypeVariable<?>, Type> typeVarAssigns = new HashMap<>();

			// solve input types
			Type[] genericParameterTypes = paramTypesFromOpType(opType, fMethod);
			MatchingUtils.inferTypeVariables(genericParameterTypes, newArgs, typeVarAssigns);

			// solve output type
			Type genericReturnType = returnTypeFromOpType(opType, fMethod);
			if (genericReturnType != void.class) {
				MatchingUtils.inferTypeVariables(new Type[] {genericReturnType}, new Type[] {newOutType}, typeVarAssigns);
			}

			// build new (read: simplified) Op type
			return Types.parameterize(opType, typeVarAssigns);
	}

	static Type[] paramTypesFromOpType(Class<?> opType,
		Method fMethod)
	{
		Type[] genericParameterTypes = fMethod.getGenericParameterTypes();
		if(fMethod.getDeclaringClass().equals(opType))
			return genericParameterTypes;
		return typesFromOpType(opType, fMethod, genericParameterTypes);
		
	}

	static Type returnTypeFromOpType(Class<?> opType,
		Method fMethod)
	{
		Type genericReturnType = fMethod.getGenericReturnType();
		if(fMethod.getDeclaringClass().equals(opType))
			return genericReturnType;
		return typesFromOpType(opType, fMethod, genericReturnType)[0];
	}

	private static Type[] typesFromOpType(Class<?> opType, Method fMethod, Type... types) {
		Map<TypeVariable<?>, Type> map = new HashMap<>();
		Class<?> declaringClass = fMethod.getDeclaringClass();
		Type genericDeclaringClass = Types.parameterizeRaw(declaringClass);
		Type genericClass = Types.parameterizeRaw(opType);
		Type superGenericClass = Types.getExactSuperType(genericClass, declaringClass);
		MatchingUtils.inferTypeVariables(new Type[] {genericDeclaringClass}, new Type[] {superGenericClass}, map);

		return Types.mapVarToTypes(types, map);
	}

	public static Method findFMethod(Class<?> c) {
			Class<?> fIface = ParameterStructs.findFunctionalInterface(c);
			if(fIface == null) throw new IllegalArgumentException("Class " + c +" does not implement a functional interface!");
			return ParameterStructs.singularAbstractMethod(fIface);
	}

	/**
	 * Finds the {@link Mutable} argument of a {@link FunctionalInterface}'s
	 * singular abstract method. If there is no argument annotated with
	 * {@code Mutable}, then it is assumed that no arguments are mutable and that
	 * the output of the functional {@link Method} is its output. We also assume
	 * that only one argument is annotated.
	 * 
	 * @param c - the {@link Class} extending a {@link FunctionalInterface}
	 * @return the index of the mutable argument (or -1 iff the output is
	 *         returned).
	 */
	public static int findMutableArgIndex(Class<?> c) {
		Method fMethod = findFMethod(c);
		for (int i = 0; i < fMethod.getParameterCount(); i++) {
			if (AnnotationUtils.getMethodParameterAnnotation(fMethod, i,
				Mutable.class) != null) return i;
		}
		return -1;
	}

	/**
	 * Given a simplifier or focuser (henceforth called mutators), it is often
	 * helpful to discern its output/input type given its input/output type.
	 * We can discern the unknown type by
	 * first inferring the type variables of {@code mutatorInferFrom} from
	 * {@code inferFrom}. We can then use these mappings to resolve the type
	 * variables of {@code unresolvedType}. This method assumes that:
	 * <ul>
	 * <li> {@code inferFrom} is assignable to {@code mutatorInferFrom}
	 * <li> There are no vacuous generics in {@code unresolvedType}
	 * </ul>
	 * 
	 * @param inferFrom - the concrete input/output type that we would like to pass to the mutator.
	 * @param mutatorInferFrom - the (possibly generic) input/output type of the mutator {@link OpInfo}
	 * @param unresolvedType - the (possibly generic) output/input type of the mutator {@link OpInfo}
	 * @return - the concrete output/input type obtained/given by the mutator (restricted by {@code inferFrom}
	 */
	public static Type resolveMutatorTypeArgs(Type inferFrom, Type mutatorInferFrom, Type unresolvedType) {
		if(!Types.containsTypeVars(unresolvedType)) return unresolvedType;
		Map<TypeVariable<?>, Type> map = new HashMap<>();
		MatchingUtils.inferTypeVariables(new Type[] {mutatorInferFrom}, new Type[] {inferFrom}, map);
		return Types.mapVarToTypes(unresolvedType, map);
	}

	/**
	 * Finds required mutators (i.e. simplifier or focuser).
	 * 
	 * @param env - the {@link OpEnvironment} to query for the needed Op
	 * @param mutatorInfos - the {@link OpInfo}s for which Ops are needed
	 * @param originalInputs - the concrete input {@link Type} of the Op.
	 * @param mutatedInputs - the concrete output {@link Type} of the Op.
	 * @return a set of {@code Op}s, instantiated from {@code mutatorInfo}s, that
	 *         satisfy the prescribed input/output types.
	 */
	public static List<Function<?, ?>> findArgMutators(OpEnvironment env, List<OpInfo> mutatorInfos,
		Type[] originalInputs, Type[] mutatedInputs)
	{
		if (mutatorInfos.size() != originalInputs.length)
			throw new IllegalStateException(
				"Mismatch between number of argument mutators and arguments in ref:\n");
		
		List<Function<?, ?>> mutators = new ArrayList<>();
		for(int i = 0; i < mutatorInfos.size(); i++) {
			Function<?, ?> mutator = findArgMutator(env, mutatorInfos.get(i), originalInputs[i], mutatedInputs[i]);
			mutators.add(mutator);
		}
		return mutators;
	}
	
	public static Function<?, ?> findArgMutator(OpEnvironment env, OpInfo mutatorInfo, Type originalInput, Type mutatedInput){
			Type opType = Types.parameterize(Function.class, new Type[] {originalInput, mutatedInput});
			Function<?, ?> mutator = (Function<?, ?>) env.op(mutatorInfo,
				Nil.of(opType), new Nil<?>[] { Nil.of(originalInput) }, Nil.of(
					mutatedInput));
			return mutator;
	}

	/**
	 * Uses Google Guava to generate a list of permutations of each available
	 * simplification possibility
	 */
	public static List<List<OpInfo>> simplifyArgs(OpEnvironment env, Type... t){
		return Arrays.stream(t) //
				.map(type -> getSimplifiers(env, type)) //
				.collect(Collectors.toList());
	}

	/**
	 * Obtains all {@link Simplifier}s known to the environment that can operate
	 * on {@code t}. If no {@code Simplifier}s are known to explicitly work on
	 * {@code t}, an {@link Identity} simplifier will be created.
	 * 
	 * @param t - the {@link Type} we are interested in simplifying.
	 * @return a list of {@link Simplifier}s that can simplify {@code t}.
	 */
	public static List<OpInfo> getSimplifiers(OpEnvironment env, Type t) {
		// TODO: optimize
		Stream<OpInfo> infoStream = StreamSupport.stream(env.infos("simplify").spliterator(), true);
		List<OpInfo> list = infoStream//
				.filter(info -> Function.class.isAssignableFrom(Types.raw(info.opType()))) //
				.filter(info -> Types.isAssignable(t, info.inputs().get(0).getType())) //
				.collect(Collectors.toList());
		return list;
	}

	/**
	 * Uses Google Guava to generate a list of permutations of each available
	 * simplification possibility
	 */
	public static List<List<OpInfo>> focusArgs(OpEnvironment env, Type... t){
		return Arrays.stream(t) //
			.map(type -> getFocusers(env, type)) //
			.collect(Collectors.toList());
	}


	/**
	 * Obtains all {@link Simplifier}s known to the environment that can operate
	 * on {@code t}. If no {@code Simplifier}s are known to explicitly work on
	 * {@code t}, an {@link Identity} simplifier will be created.
	 * 
	 * @param t - the {@link Type} we are interested in simplifying.
	 * @return a list of {@link Simplifier}s that can simplify {@code t}.
	 */
	public static List<OpInfo> getFocusers(OpEnvironment env, Type t) {
		// TODO: optimize
		Stream<OpInfo> infoStream = StreamSupport.stream(env.infos("focus").spliterator(), true);
		List<OpInfo> list = infoStream//
				.filter(info -> Function.class.isAssignableFrom(Types.raw(info.opType()))) //
				.filter(info -> Types.isAssignable(t, info.output().getType())) //
				.collect(Collectors.toList());
		return list;
	}


}
