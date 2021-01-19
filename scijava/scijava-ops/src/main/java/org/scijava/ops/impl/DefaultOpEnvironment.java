/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2018 ImageJ developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.ops.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.scijava.AbstractContextual;
import org.scijava.Context;
import org.scijava.InstantiableException;
import org.scijava.Priority;
import org.scijava.log.LogService;
import org.scijava.ops.OpDependency;
import org.scijava.ops.OpDependencyMember;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpMethod;
import org.scijava.ops.OpUtils;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.matcher.DefaultOpMatcher;
import org.scijava.ops.matcher.MatchingUtils;
import org.scijava.ops.matcher.OpAdaptationInfo;
import org.scijava.ops.matcher.OpCandidate;
import org.scijava.ops.matcher.OpCandidate.StatusCode;
import org.scijava.ops.matcher.OpClassInfo;
import org.scijava.ops.matcher.OpFieldInfo;
import org.scijava.ops.matcher.OpMatcher;
import org.scijava.ops.matcher.OpMatchingException;
import org.scijava.ops.matcher.OpMethodInfo;
import org.scijava.ops.matcher.OpRef;
import org.scijava.ops.matcher.SimplifiedOpInfo;
import org.scijava.ops.simplify.Identity;
import org.scijava.ops.simplify.Simplifier;
import org.scijava.ops.util.OpWrapper;
import org.scijava.param.FunctionalMethodType;
import org.scijava.param.ParameterStructs;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.PluginInfo;
import org.scijava.plugin.PluginService;
import org.scijava.struct.ItemIO;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;
import org.scijava.types.Types;
import org.scijava.util.ClassUtils;

/**
 * Default implementation of {@link OpEnvironment}, whose ops and related state
 * are discovered from a SciJava application context.
 * 
 * @author Curtis Rueden
 */
public class DefaultOpEnvironment extends AbstractContextual implements OpEnvironment {

	@Parameter
	private PluginService pluginService;

	private OpMatcher matcher;

	@Parameter
	private LogService log;

	@Parameter
	private TypeService typeService;

	/**
	 * Data structure storing all known Ops, grouped by name. This reduces the
	 * search size for any Op request to the number of known Ops with the name
	 * given in the request.
	 */
	private Map<String, Set<OpInfo>> opDirectory;

	/**
	 * Map containing pairs of {@link OpRef} op requests and the {@code Op}s that
	 * matched those requests. Used to quickly return Ops when an {@OpRef} matches
	 * a previous call.
	 *
	 * @see OpRef#equals(Object)
	 */
	private Map<OpRef, Object> opCache;

	/**
	 * Data structure storing all known {@link OpWrapper}s. Each {@link OpWrapper}
	 * is retrieved by providing the {@link Class} that it is able to wrap.
	 */
	private Map<Class<?>, OpWrapper<?>> wrappers;

	public DefaultOpEnvironment(final Context context) {
		context.inject(this);
		matcher = new DefaultOpMatcher(log);
	}

	@Override
	public Iterable<OpInfo> infos() {
		if (opDirectory == null) initOpDirectory();
		return opDirectory.values().stream().flatMap(list -> list.stream()).collect(Collectors.toList());
	}

	@Override
	public Iterable<OpInfo> infos(String name) {
		if (opDirectory == null) initOpDirectory();
		if (name == null || name.isEmpty()) return infos();
		return opsOfName(name);
	}

	@Override
	public <T> T op(final String opName, final Nil<T> specialType, final Nil<?>[] inTypes, final Nil<?> outType) {
		try {
			return findOpInstance(opName, specialType, inTypes, outType);
		} catch (OpMatchingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public Type genericType(Object obj) {
		return typeService.reify(obj);
	}

	@Override
	public <T> T bakeLambdaType(final T op, Type reifiedType) {
		if (wrappers == null) initWrappers();
		@SuppressWarnings("unchecked")
		final OpWrapper<T> wrapper = (OpWrapper<T>) wrappers.get(Types.raw(reifiedType));
		return wrapper.wrap(op, reifiedType);
	}

	@Override
	public OpInfo opify(final Class<?> opClass) {
		return opify(opClass, Priority.NORMAL);
	}

	@Override
	public OpInfo opify(final Class<?> opClass, final double priority) {
		return new OpClassInfo(opClass, priority);
	}

	@Override
	public void register(final OpInfo info, final String name) {
		if (opDirectory == null) initOpDirectory();
		addToOpIndex(info, name);
	}

	@SuppressWarnings("unchecked")
	private <T> T findOpInstance(final String opName, final Nil<T> specialType, final Nil<?>[] inTypes,
			final Nil<?> outType) throws OpMatchingException {
		final OpRef ref = OpRef.fromTypes(opName, toTypes(specialType), outType != null ? outType.getType() : null,
				toTypes(inTypes));
		return (T) findOpInstance(ref, true);
	}

	private Type[] toTypes(Nil<?>... nils) {
		return Arrays.stream(nils).filter(n -> n != null).map(n -> n.getType()).toArray(Type[]::new);
	}
	
	/**
	 * Finds an Op instance matching the request described by {@link OpRef}
	 * {@code ref}. NB the return must be an {@link Object} here (instead of some
	 * type variable T where T is the Op type} since there is no way to ensure
	 * that the {@code OpRef} can provide that T (since the OpRef could require
	 * that the Op returned is of multiple types).
	 * 
	 * @param ref
	 * @param adaptable
	 * @return an Op satisfying the request described by {@code ref}.
	 * @throws OpMatchingException
	 */
	private Object findOpInstance(final OpRef ref,
		boolean adaptable) throws OpMatchingException
	{
		// see if the ref has been matched already
		Object cachedOp = checkCacheForRef(ref);
		if (cachedOp != null) return cachedOp;

		// obtain suitable OpCandidate
		OpCandidate candidate = findOpCandidate(ref, adaptable);

		// obtain Op instance (with dependencies)
		Object op = instantiateOp(candidate);
		
		// wrap Op
		Object wrappedOp = wrapOp(op, candidate.opInfo(), candidate.typeVarAssigns());

		// cache instance
		opCache.putIfAbsent(ref, wrappedOp);

		return wrappedOp;
	}

	private Object checkCacheForRef(OpRef ref) {
		if (opCache == null) {
			opCache = new HashMap<>();
		}
		if (opCache.containsKey(ref))
			return opCache.get(ref);
		return null;
	}
	
	private OpCandidate findOpCandidate(OpRef ref, boolean adaptable) throws OpMatchingException{
		try {
			// attempt to find a direct match
			return matcher.findSingleMatch(this, ref);
		}
		catch (OpMatchingException e1) {
			// no direct match; find an adapted match
			if (!adaptable) throw new OpMatchingException(
				"No matching Op for request: " + ref + "\n(adaptation is disabled)",
				e1);
			try {
				return adaptOp(ref);
			}
			catch (OpMatchingException e2) {
				try {
					//TODO: fix
					return simplifyOp(ref).get(0);
				}
				catch (OpMatchingException e3) {
					// TODO: do something
				}
				// no adapted match
				OpMatchingException adaptedMatchException = new OpMatchingException(
					"No Op available for request: " + ref, e2);
				adaptedMatchException.addSuppressed(e1);
				throw adaptedMatchException;
			}
		}
	}

	private List<OpCandidate> simplifyOp(OpRef ref) throws OpMatchingException {
		
		List<OpCandidate> candidates = new ArrayList<>();

		// simplify all inputs and outputs
		List<Type> typeList = Arrays.asList(ref.getArgs());
		List<List<Simplifier<?, ?>>> simplifications = simplifyArgs(typeList);
		
		// make new OpRef based on simplified inputs
		for (List<Simplifier<?, ?>> simplification: simplifications) {
			
			// avoid recursion by throwing out the identity simplification.
			// TODO there is probably a better place for this.
			if(isIdentity(simplification)) continue;

			Class<?> opClass = Types.raw(ref.getTypes()[0]);
			// TODO: ensure that the given type parameters are within the bounds of
			// the op type's type parameters.
			// For all built-in op types (e.g. Function, Computer), the type
			// parameters are unbounded. But, for extensibility, we should check.
			List<Type> newArgsList = simplification.stream().map(s -> s.simpleType()).collect(Collectors.toList());
			// TODO: not correct whenever there is a return type
			Type newType = retype(ref.getTypes()[0], newArgsList);
			//HACK: we assume that the output is a pure output and does not belong within the args
			OpRef simplifiedRef = new OpRef(ref.getName(), new Type[] { newType }, ref
				.getOutType(), newArgsList.toArray(Type[]::new));
			try{
				// TODO: consider adaptability
				candidates.add(findOpCandidate(simplifiedRef, true));
			}
			catch (OpMatchingException e) {
				continue;
			}
		}
		
		// find new Op 
		// TODO: fix
		if (candidates.size() != 1) throw new OpMatchingException("Mutliple viable simplifications for ref.");
		return candidates;
	}
	
	private boolean isIdentity(List<Simplifier<?, ?>> simplification) {
		return simplification
				.parallelStream()
				.allMatch(simplifier -> simplifier instanceof Identity);
	}

	/**
	 * FIXME: We assume that the last type parameter is a pure output and all
	 * other parameters are pure inputs
	 * 
	 * @param oldType
	 * @param newArgList
	 * @return - a new type
	 */
	private Type retype(Type oldType, List<Type> newArgList) {
			Class<?> opClass = Types.raw(oldType);
			if (!(oldType instanceof ParameterizedType))
				throw new IllegalStateException("We hadn't thought about this yet.");
			ParameterizedType pType = (ParameterizedType) oldType;
			Type[] typeArgs = pType.getActualTypeArguments();
			Type returnType = typeArgs[typeArgs.length - 1];

			List<Type> newTypeParams = new ArrayList<>(newArgList);
			newTypeParams.add(returnType);
			return Types.parameterize(opClass, newTypeParams.toArray(Type[]::new));
	}
	
	private List<List<Simplifier<?, ?>>> simplifyArgs(List<Type> t){
		return simplifyArgs(t, 0, new ArrayList<Simplifier<?, ?>>());
	}
	
	private List<List<Simplifier<?, ?>>> simplifyArgs(List<Type> t, int i, List<Simplifier<?, ?>> simplifiers){
		if (i >= t.size()) return Collections.singletonList(simplifiers);
		List<List<Simplifier<?, ?>>> result = new ArrayList<>();
		Type original = t.get(i);
		List<Simplifier<?, ?>> simplifiedArgs = simplify(original);
		for (Simplifier<?, ?> simplified : simplifiedArgs) {
			List<Simplifier<?, ?>> copy = new ArrayList<>(simplifiers);
			copy.add(i, simplified);
			result.addAll(simplifyArgs(t, i + 1, copy));
		}
		return result;
	}
	
	private List<Simplifier<?, ?>> simplify(Type t) {
		if (t == Integer.class) {
			return Collections.singletonList(new Simplifier<Number, Integer> () {

				@Override
				public Number simplify(Integer p) {
					return p;
				}

				@Override
				public Integer focus(Number g) {
					return g.intValue();
				}
				
				@Override
				public String toString() {
					return "Integer Simplifier";
				}
			});
		}
		
		if (t == Double.class) {
			return Collections.singletonList(new Simplifier<Number, Double> () {

				@Override
				public Number simplify(Double p) {
					return p;
				}

				@Override
				public Double focus(Number g) {
					return g.doubleValue();
				}
				
				@Override
				public String toString() {
					return "Double Simplifier";
				}
			});
		}
		
		if (t == Long.class) {
			return Collections.singletonList(new Simplifier<Number, Long> () {

				@Override
				public Number simplify(Long p) {
					return p;
				}

				@Override
				public Long focus(Number g) {
					return g.longValue();
				}
				
				@Override
				public String toString() {
					return "Long Simplifier";
				}
			});
		}
		return Collections.singletonList(new Identity<>(t));
	}

	/**
	 * Creates an instance of the Op from the {@link OpCandidate} <b>with its
	 * required {@link OpDependency} fields</b>.
	 * 
	 * @param candidate
	 * @return an Op with all needed dependencies
	 * @throws OpMatchingException
	 */
	private Object instantiateOp(final OpCandidate candidate)
		throws OpMatchingException
	{
		final List<Object> dependencies = resolveOpDependencies(candidate);
		return candidate.createOp(dependencies);
	}

	/**
	 * Wraps the matched op into an {@link Op} that knows its generic typing.
	 * 
	 * @param op - the op to wrap.
	 * @param opInfo - from which we determine the {@link Type} of the {@code Op}
	 *            
	 * @return an {@link Op} wrapping of op.
	 */
	private <T> T wrapOp(T op, OpInfo opInfo, Map<TypeVariable<?>, Type> typeVarAssigns) {
		if (wrappers == null)
			initWrappers();

		Type opType = opInfo.opType();
		try {
			// find the opWrapper that wraps this type of Op
			Class<?> wrapper = getWrapperClass(op, opInfo);
			// obtain the generic type of the Op w.r.t. the Wrapper class 
			Type exactSuperType = Types.getExactSuperType(opType, wrapper);
			Type reifiedSuperType = Types.substituteTypeVariables(exactSuperType, typeVarAssigns);
			// wrap the Op
			return bakeLambdaType(op, reifiedSuperType);
		} catch (IllegalArgumentException | SecurityException exc) {
			log.error(exc.getMessage() != null ? exc.getMessage() : "Cannot wrap " + op.getClass());
			return op;
		} catch (NullPointerException e) {
			log.error("No wrapper exists for " + Types.raw(opType).toString() + ".");
			return op;
		}
	}

	private Class<?> getWrapperClass(Object op, OpInfo info) {
			Class<?>[] suitableWrappers = wrappers.keySet().stream().filter(wrapper -> wrapper.isInstance(op))
					.toArray(Class[]::new);
			if (suitableWrappers.length == 0)
				throw new IllegalArgumentException(info.implementationName() + ": matched op Type " + info.opType().getClass()
						+ " does not match a wrappable Op type.");
			if (suitableWrappers.length > 1)
				throw new IllegalArgumentException(
						"Matched op Type " + info.opType().getClass() + " matches multiple Op types: " + wrappers.toString());
			if (!Types.isAssignable(Types.raw(info.opType()), suitableWrappers[0]))
				throw new IllegalArgumentException(Types.raw(info.opType()) + "cannot be wrapped as a " + suitableWrappers[0].getClass());
			return suitableWrappers[0];
	}

	private List<Object> resolveOpDependencies(OpCandidate candidate) throws OpMatchingException {
		return resolveOpDependencies(candidate.opInfo(), candidate.typeVarAssigns());
	}

	private void initWrappers() {
		wrappers = new HashMap<>();
		for (OpWrapper<?> wrapper : pluginService.createInstancesOfType(OpWrapper.class)) {
			wrappers.put(wrapper.type(), wrapper);
		}
	}

	/**
	 * Attempts to inject {@link OpDependency} annotated fields of the specified
	 * object by looking for Ops matching the field type and the name specified in
	 * the annotation. The field type is assumed to be functional.
	 *
	 * @param info - the {@link OpInfo} whose {@link OpDependency}s will be
	 *          injected
	 * @param typeVarAssigns - the mapping of {@link TypeVariable}s in the
	 *          {@code OpInfo} to {@link Type}s given in the request.
	 * @throws OpMatchingException if the type of the specified object is not
	 *           functional, if the Op matching the functional type and the name
	 *           could not be found, if an exception occurs during injection
	 */
	private List<Object> resolveOpDependencies(OpInfo info, Map<TypeVariable<?>, Type> typeVarAssigns) throws OpMatchingException {

		final List<OpDependencyMember<?>> dependencies = info.dependencies();
		final List<Object> resolvedDependencies = new ArrayList<>(dependencies.size());

		for (final OpDependencyMember<?> dependency : dependencies) {
			final OpRef dependencyRef = inferOpRef(dependency, typeVarAssigns);
			try {
				resolvedDependencies.add(findOpInstance(dependencyRef, dependency.isAdaptable()));
			} catch (final Exception e) {
				throw new OpMatchingException("Could not find Op that matches requested Op dependency:" + "\nOp class: "
						+ info.implementationName() + //
						"\nDependency identifier: " + dependency.getKey() + //
						"\n\n Attempted request:\n" + dependencyRef, e);
			}
		}
		return resolvedDependencies;
	}

	/**
	 * Adapts an Op with the name of ref into a type that can be SAFELY cast to
	 * ref.
	 * <p>
	 * NB This method <b>cannot</b> use the {@link OpMatcher} to find a suitable
	 * {@code adapt} Op. The premise of adaptation depends on the ability to
	 * examine the applicability of all {@code adapt} Ops with the correct output
	 * type. We need to check all of them because we do not know whether:
	 * <ul>
	 * <li>The dependencies will exist for a particular {@code adapt} Op
	 * <li>The Op we want exists with the correct type for the input of the
	 * {@code adapt} Op.
	 * </ul>
	 * 
	 * @param ref - the type of Op that we are looking to adapt to.
	 * @return {@link OpCandidate} - an Op that has been adapted to conform
	 *         the the ref type (if one exists).
	 * @throws OpMatchingException
	 */
	private OpCandidate adaptOp(OpRef ref) throws OpMatchingException {

		for (final OpInfo adaptor : infos("adapt")) {
			Type adaptTo = adaptor.output().getType();
			Map<TypeVariable<?>, Type> map = new HashMap<>();
			// make sure that the adaptor outputs the correct type
			if (!adaptOpOutputSatisfiesRefTypes(adaptTo, map, ref)) continue;
			// make sure that the adaptor is a Function (so we can cast it later)
			if (Types.isInstance(adaptor.opType(), Function.class)) {
				log.debug(adaptor + " is an illegal adaptor Op: must be a Function");
				continue;
			}

			try {
				// resolve adaptor dependencies
				final List<Object> dependencies = resolveOpDependencies(adaptor, map);

				@SuppressWarnings("unchecked")
				Function<Object, Object> adaptorOp = //
					(Function<Object, Object>) adaptor.createOpInstance(dependencies) //
						.object(); //

				// grab the first type parameter from the OpInfo and search for
				// an Op that will then be adapted (this will be the only input of the
				// adaptor since we know it is a Function)
				Type srcOpType = Types.substituteTypeVariables(adaptor.inputs().get(0)
					.getType(), map);
				final OpRef srcOpRef = inferOpRef(srcOpType, ref.getName(), map);
				final OpCandidate srcCandidate = matcher.findSingleMatch(this,
					srcOpRef);
				map.putAll(srcCandidate.typeVarAssigns());
				Type adapterOpType = Types.substituteTypeVariables(adaptor.output()
					.getType(), map);
				OpAdaptationInfo adaptedInfo = new OpAdaptationInfo(srcCandidate
					.opInfo(), adapterOpType, adaptorOp);
				OpCandidate adaptedCandidate = new OpCandidate(this, log, ref, adaptedInfo, map);
				adaptedCandidate.setStatus(StatusCode.MATCH);
				return adaptedCandidate;
			}
			catch (OpMatchingException e1) {
				log.trace(e1);
			}
		}

		// no adaptors available.
		throw new OpMatchingException(
			"Op adaptation failed: no adaptable Ops of type " + ref.getName());
	}

	private boolean adaptOpOutputSatisfiesRefTypes(Type adaptTo, Map<TypeVariable<?>, Type> map, OpRef ref) {
		for (Type opType : ref.getTypes()) {
			// TODO: clean this logic
			if (opType instanceof ParameterizedType) {
				if (!MatchingUtils.checkGenericAssignability(adaptTo,
					(ParameterizedType) opType, map, true))
				{
					return false;
				}
			}
			else if (!Types.isAssignable(opType, adaptTo, map)) {
				return false;
			}
		}
		return true;
	}

	private OpRef inferOpRef(OpDependencyMember<?> dependency,
		Map<TypeVariable<?>, Type> typeVarAssigns) throws OpMatchingException
	{
		final Type mappedDependencyType = Types.mapVarToTypes(new Type[] {
			dependency.getType() }, typeVarAssigns)[0];
		final String dependencyName = dependency.getDependencyName();
		final OpRef inferredRef = inferOpRef(mappedDependencyType, dependencyName,
			typeVarAssigns);
		if (inferredRef != null) return inferredRef;
		throw new OpMatchingException("Could not infer functional " +
			"method inputs and outputs of Op dependency field: " + dependency
				.getKey());
	}

	/**
	 * Tries to infer a {@link OpRef} from a functional Op type. E.g. the type:
	 * 
	 * <pre>
	 * Computer&lt;Double[], Double[]&gt
	 * </pre>
	 * 
	 * Will result in the following {@link OpRef}:
	 * 
	 * <pre>
	 * Name: 'specified name'
	 * Types:       [Computer&lt;Double, Double&gt]
	 * InputTypes:  [Double[], Double[]]
	 * OutputTypes: [Double[]]
	 * </pre>
	 * 
	 * Input and output types will be inferred by looking at the signature of the
	 * functional method of the specified type. Also see
	 * {@link ParameterStructs#findFunctionalMethodTypes(Type)}.
	 *
	 * @param type
	 * @param name
	 * @return null if the specified type has no functional method
	 */
	private OpRef inferOpRef(Type type, String name, Map<TypeVariable<?>, Type> typeVarAssigns)
			throws OpMatchingException {
		List<FunctionalMethodType> fmts = ParameterStructs.findFunctionalMethodTypes(type);
		if (fmts == null)
			return null;

		EnumSet<ItemIO> inIos = EnumSet.of(ItemIO.BOTH, ItemIO.INPUT);
		EnumSet<ItemIO> outIos = EnumSet.of(ItemIO.BOTH, ItemIO.OUTPUT);

		Type[] inputs = fmts.stream().filter(fmt -> inIos.contains(fmt.itemIO())).map(fmt -> fmt.type())
				.toArray(Type[]::new);

		Type[] outputs = fmts.stream().filter(fmt -> outIos.contains(fmt.itemIO())).map(fmt -> fmt.type())
				.toArray(Type[]::new);

		Type[] mappedInputs = Types.mapVarToTypes(inputs, typeVarAssigns);
		Type[] mappedOutputs = Types.mapVarToTypes(outputs, typeVarAssigns);

		final int numOutputs = mappedOutputs.length;
		if (numOutputs != 1) {
			String error = "Op '" + name + "' of type " + type + " specifies ";
			error += numOutputs == 0 //
					? "no outputs" //
					: "multiple outputs: " + Arrays.toString(outputs);
			error += ". This is not supported.";
			throw new OpMatchingException(error);
		}
		return new OpRef(name, new Type[] { type }, mappedOutputs[0], mappedInputs);
	}

	private void initOpDirectory() {
		opDirectory = new HashMap<>();

		// Add regular Ops
		for (final PluginInfo<Op> pluginInfo : pluginService.getPluginsOfType(Op.class)) {
			try {
				final Class<?> opClass = pluginInfo.loadClass();
				OpInfo opInfo = new OpClassInfo(opClass);
				addToOpIndex(opInfo, pluginInfo.getName());
				simplifyInfo(opInfo, pluginInfo.getName());
			} catch (InstantiableException exc) {
				log.error("Can't load class from plugin info: " + pluginInfo.toString(), exc);
			}
		}
		// Add Ops contained in an OpCollection
		for (final PluginInfo<OpCollection> pluginInfo : pluginService.getPluginsOfType(OpCollection.class)) {
			try {
				final Class<? extends OpCollection> c = pluginInfo.loadClass();
				final List<Field> fields = ClassUtils.getAnnotatedFields(c, OpField.class);
				Object instance = null;
				for (Field field : fields) {
					final boolean isStatic = Modifier.isStatic(field.getModifiers());
					if (!isStatic && instance == null) {
						instance = field.getDeclaringClass().newInstance();
					}
					OpInfo opInfo = new OpFieldInfo(isStatic ? null : instance, field);
					addToOpIndex(opInfo, field.getAnnotation(OpField.class).names());
					simplifyInfo(opInfo, field.getAnnotation(OpField.class).names());
				}
				final List<Method> methods = ClassUtils.getAnnotatedMethods(c, OpMethod.class);
				for (final Method method: methods) {
					OpInfo opInfo = new OpMethodInfo(method);
					addToOpIndex(opInfo, method.getAnnotation(OpMethod.class).names());
				}
			} catch (InstantiableException | InstantiationException | IllegalAccessException exc) {
				log.error("Can't load class from plugin info: " + pluginInfo.toString(), exc);
			}
		}
	}
	
	// Add Op simplifications
	//TODO: we currently only assume that all inputs are pure inputs and all outputs are pure outputs. This logic will have to be improved.
	// TODO: think of a better name
	private void simplifyInfo(OpInfo info, String names) {
		Type opType = info.opType();
		if (!(opType instanceof ParameterizedType)) return;
		ParameterizedType pType = (ParameterizedType) opType;
		List<Type> args = new ArrayList<>(Arrays.asList(pType.getActualTypeArguments()));
		args.remove(args.size() - 1);
		List<List<Simplifier<?, ?>>> simplifications = simplifyArgs(args);
		for(List<Simplifier<?, ?>> simplification: simplifications) {
			try {
				SimplifiedOpInfo simplifiedInfo = new SimplifiedOpInfo(info,
					simplification);
				// only add the simplification if it changes the signature.
				if (!simplifiedInfo.isIdentical())
					addToOpIndex(simplifiedInfo, names);
			} catch(UnsupportedOperationException e) {
				// TODO: do something
			}
		}	
	}

	private void addToOpIndex(final OpInfo opInfo, final String opNames) {
		String[] parsedOpNames = OpUtils.parseOpNames(opNames);
		if (parsedOpNames == null || parsedOpNames.length == 0) {
			log.error("Skipping Op " + opInfo.implementationName() + ":\n" + "Op implementation must provide name.");
			return;
		}
		if (!opInfo.isValid()) {
			log.error("Skipping invalid Op " + opInfo.implementationName() + ":\n"
					+ opInfo.getValidityException().getMessage());
			return;
		}
		for (String opName : parsedOpNames) {
			if (!opDirectory.containsKey(opName))
				opDirectory.put(opName, new TreeSet<>());
			opDirectory.get(opName).add(opInfo);
		}
	}

	private Set<OpInfo> opsOfName(final String name) {
		final Set<OpInfo> ops = opDirectory.getOrDefault(name, Collections.emptySet());
		return Collections.unmodifiableSet(ops);
	}

}
