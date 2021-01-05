package org.scijava.ops.simplify;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.matcher.MatchingUtils;
import org.scijava.ops.matcher.OpRef;
import org.scijava.ops.matcher.SimplifiedOpRef;
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
			Class<?> fIface = ParameterStructs.findFunctionalInterface(opType);
			Method fMethod = ParameterStructs.singularAbstractMethod(fIface);
			Map<TypeVariable<?>, Type> typeVarAssigns = new HashMap<>();

			// solve input types
			Type[] genericParameterTypes = fMethod.getGenericParameterTypes();
			MatchingUtils.inferTypeVariables(genericParameterTypes, newArgs, typeVarAssigns);

			// solve output type
			Type genericReturnType = fMethod.getGenericReturnType();
			if (genericReturnType != void.class) {
				MatchingUtils.inferTypeVariables(new Type[] {genericReturnType}, new Type[] {newOutType}, typeVarAssigns);
			}
			// TODO: if the output is also an input (i.e. we have a void return), do
			// we need to ensure that the output focuser is the inverse of the input
			// simplifier pertaining to the input/output argument?

			// build new (read: simplified) Op type
			return Types.parameterize(opType, typeVarAssigns);
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

}
