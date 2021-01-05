
package org.scijava.ops.matcher;

import com.google.common.collect.Streams;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.scijava.log.Logger;
import org.scijava.ops.OpDependency;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.simplify.SimplificationMetadata;
import org.scijava.ops.simplify.SimplificationUtils;
import org.scijava.ops.simplify.SimplifiedMember;
import org.scijava.ops.simplify.Simplifier;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.types.Types;
import org.scijava.util.MiscUtils;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class SimplifiedOpInfo implements OpInfo {

	private final OpInfo srcInfo;
	private final List<OpInfo> focuserInfos;
	private final OpInfo outputSimplifier;

	private Struct struct;
	private ParameterizedType simplifiedType;
	private ValidityException validityException;

	/**
	 * Creates a new {@link SimplifiedOpInfo}
	 * 
	 * @param info the original {@link OpInfo}
	 * @param simpleType - the type to which this {@link SimplifiedOpInfo} conforms
	 * @param simplification - the set of Ops used to simplify an Op defined by
	 *          {@code info} into an Op of type {@code newType}
	 */
	public SimplifiedOpInfo(OpInfo info, List<OpInfo> focuserInfos, OpInfo outputSimplifier) {
		this.srcInfo = info;
		this.focuserInfos = focuserInfos;
		this.outputSimplifier = outputSimplifier;

		// NOTE: we lazily initialize this struct since we already know that it is fundamentally valid
//		try {
//			Iterator<Simplifier<?, ?>> itr = simplification.iterator();
//			final List<Member<?>> originalItems = info.struct().members();
//			final List<Member<?>> newItems = new ArrayList<>();
//			for (Member<?> m : originalItems) {
//				if(m.isInput()) {
//					try {
//						SimplifiedMember<?> sm = new SimplifiedMember<>(m, itr.next().simpleType());
//						newItems.add(sm);
//					} catch(NoSuchElementException e) {
//						throw e;
//					}
//				}
//				else {
//					newItems.add(m);
//				}
//			}
//			struct = () -> newItems;
//		}
//		catch (ValidityException e) {
//			validityException = e;
//		}
	}

	// TODO: We assume that this is a Function of some type.
	// We also assume that simplifiers exist for all inputs ONLY.
	// FIXME: generalize
	/**
	 * Generates the type of the Op where all inputs have been simplified by the
	 * use of {@link Simplifier}s. NB: This generation is done lazily to
	 * compensate for the vast number of {@link SimplifiedOpInfo}s that are
	 * generated as a part of the simplification process.
	 * 
	 * @param originalInfo - the original {@link OpInfo}
	 */
	private synchronized void generateSimplifiedType(OpInfo originalInfo) {
		if(simplifiedType != null) return;
		Type[] simpleTypeArgs = StreamSupport.stream(struct().spliterator(), false)
			.filter(m -> m.isInput()).map(m -> m.getType()).toArray(Type[]::new);
		Type[] simpleOutTypes = StreamSupport.stream(struct().spliterator(), false)
			.filter(m -> m.isOutput()).map(m -> m.getType()).toArray(Type[]::new);
		if (simpleOutTypes.length != 1) throw new IllegalStateException(this
			.toString() + "\n ERROR: Op has multiple outTypes!");
		Type simpleOutType = simpleOutTypes[0];

		simplifiedType = SimplificationUtils.retypeOpType(originalInfo.opType(), simpleTypeArgs, simpleOutType);

//		// generate simplified type
//		List<Type> simplifiedTypes = focuserInfos.stream().map(s -> s.output().getType())
//			.collect(Collectors.toList());
//		if (!(originalInfo.opType() instanceof ParameterizedType))
//			throw new UnsupportedOperationException(
//				"I am not smart enough to handle this yet.");
//		simplifiedTypes.add(srcInfo.output().getType());
//		Class<?> rawType = Types.raw(originalInfo.opType());
//		simplifiedType = Types.parameterize(rawType, simplifiedTypes.toArray(Type[]::new));
	}

	@Override
	public Type opType() {
		if (simplifiedType == null) generateSimplifiedType(srcInfo);
		return simplifiedType;
	}

	@Override
	public Struct struct() {
		if (struct == null) generateStruct();
		return struct;
	}

	public List<OpInfo> focuserInfos() {
		return focuserInfos;
	}
	
	public OpInfo simplifierInfo() {
		return outputSimplifier;
	}
	
	public OpInfo srcInfo() {
		return srcInfo;
	}
	/**
	 * Generates the {@link Struct} of the Op where all inputs have been
	 * simplified by the use of {@link Simplifier}s. NB: This generation is done
	 * lazily to compensate for the vast number of {@link SimplifiedOpInfo}s that
	 * are generated as a part of the simplification process.
	 */
	private synchronized void generateStruct() {
		if (struct != null) return;

		// generate the struct
		Iterator<OpInfo> itr = focuserInfos.iterator();
		final List<Member<?>> originalItems = srcInfo.struct().members();
		final List<Member<?>> newItems = new ArrayList<>();
		final Map<TypeVariable<?>, Type> map = new HashMap<>();
		Type[] originalMemberType = new Type[1];
		Type[] simpleInput = new Type[1];
		Type simpleOutput;
		Type simpleMemberType;
		for (Member<?> m : originalItems) {
			if (!m.isInput() && ! m.isOutput()) {
				newItems.add(m);
				continue;
			}
			simpleMemberType = null;

			if (m.isInput()) {
				originalMemberType[0] = m.getType();
				OpInfo focuser = itr.next();
				simpleInput[0] = focuser.output().getType();
				simpleOutput = focuser.inputs().get(0).getType();

				simpleMemberType = SimplificationUtils.resolveMutatorTypeArgs(
					originalMemberType[0], simpleInput[0], simpleOutput);
			}
			if(m.isOutput()) {
				originalMemberType[0] = m.getType();
				OpInfo simplifier = outputSimplifier;
				simpleInput[0] = simplifier.inputs().get(0).getType();
				simpleOutput = simplifier.output().getType();
				
				// if there are type variables, we have to reify them
				simpleOutput = SimplificationUtils.resolveMutatorTypeArgs(
					originalMemberType[0], simpleInput[0], simpleOutput);
				if(simpleMemberType != null && !simpleMemberType.equals(simpleOutput)) {
					throw new IllegalArgumentException("I/O argument must be focused from/simplified to the same type!");
				}
				simpleMemberType = simpleOutput;
			}
			// create simplifiedMember from m
			SimplifiedMember<?> sm = new SimplifiedMember<>(m, simpleMemberType);
			newItems.add(sm);
		}
		struct = () -> newItems;
	}
	
	private SimplifiedMember<?> createSimpleMember(Member<?> m, Type simpleInput, Type simpleOutput){
		Map<TypeVariable<?>, Type> map = new HashMap<>();
		// if there are type variables, we have to reify them
		if (Types.containsTypeVars(simpleInput)) {
			// we assume here that if there is a type variable somewhere in the
			// type, it is indicative of an identity simplification.
			// TODO: we might have to infer the type variables against the input
			// to the simplifier, NOT the output (which is SimpleMemberType)
			MatchingUtils.inferTypeVariables(new Type[] {simpleInput}, new Type[] {m.getType()}, map);
			simpleOutput = Types.mapVarToTypes(simpleOutput, map);
		}
		// create simplifiedMember from m
		return new SimplifiedMember<>(m, simpleOutput);	
	}

	@Override
	public double priority() {
		// The original info should hold priority over this one
		return srcInfo.priority() - 1;
	}

	@Override
	public String implementationName() {
		return srcInfo.implementationName();
	}

	// TODO: delete
	/**
	 * Creates a <b>simplified</b> version of the original Op, whose parameter
	 * types are dictated by the {@code simpleType}s of this info's
	 * {@link Simplifier}s.
	 * 
	 * @param dependencies - this Op's dependencies
	 * 
	 * @see #createOpInstance(List, List) - used when we want to simplify the
	 *      inputs <b>and then</b> focus them into the orignial Op's parameter
	 *      types.
	 */
	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		throw new UnsupportedOperationException(
			"This Op cannot be instantiated without simplifiers/focusers");
//		final Object op = srcInfo.createOpInstance(dependencies).object();
//		try {
//			return struct().createInstance(javassistOp(op));
//		}
//		catch (Throwable ex) {
//			throw new IllegalStateException(
//				"Failed to invoke simplification of Op: \n" + srcInfo +
//					"\nProvided Op dependencies were: " + Objects.toString(dependencies),
//				ex);
//		}
	}

	/**
	 * Creates a <b>simplified</b> version of the original Op, whose parameter
	 * types are dictated by the {@code focusedType}s of this info's
	 * {@link Simplifier}s. The resulting Op will use {@code simplifier}s to
	 * simplify the inputs, and then will use this info's {@code focuser}s to
	 * focus the simplified inputs into types suitable for the original Op.
	 * 
	 * @param dependencies - this Op's dependencies
	 * @param metadata - data required to correctly write the simplified Op
	 * @see #createOpInstance(List) - used when there are no associated
	 *      {@code refSimplifier}s.
	 */
	public StructInstance<?> createOpInstance(List<?> dependencies, SimplificationMetadata metadata) {
		final Object op = srcInfo.createOpInstance(dependencies).object();
		try {
			return struct().createInstance(javassistOp(op, metadata));
		}
		catch (Throwable ex) {
			throw new IllegalStateException(
				"Failed to invoke simplification of Op: \n" + srcInfo +
					"\nProvided Op dependencies were: " + Objects.toString(dependencies),
				ex);
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		// FIXME
		return true;
	}

	@Override
	public ValidityException getValidityException() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnnotatedElement getAnnotationBearer() {
		return srcInfo.getAnnotationBearer();
	}

	@Override
	public OpCandidate createCandidate(OpEnvironment env, Logger log, OpRef ref,
		Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		if (ref instanceof SimplifiedOpRef) {
			SimplifiedOpRef simpleRef = (SimplifiedOpRef) ref;
			return new SimplifiedOpCandidate(env, log, simpleRef, this,
				typeVarAssigns);
		}
		return new SimplifiedOpCandidate(env, log, ref, this, typeVarAssigns);
	}

	/**
	 * Creates a Class given an Op and a set of {@link Simplifier}s. This class:
	 * <ul>
	 * <li>is of the same functional type as the given Op
	 * <li>has type arguments that are of the simplified form of the type
	 * arguments of the given Op (these arguments are dictated by the list of
	 * {@code Simplifier}s.
	 * <li>
	 * 
	 * @param originalOp - the Op that will be simplified
	 * @param refSimplifiers - the {@code Simplifiers} that will simplify the
	 *          input before this info's {@code Simplifier}s focus the input.
	 * @return a partial application of {@code m} with all {@link OpDependency}s
	 *         injected.
	 * @throws Throwable
	 */
	private Object javassistOp(Object originalOp, SimplificationMetadata metadata) throws Throwable {
		ClassPool pool = ClassPool.getDefault();

		// Create wrapper class
		String className = formClassName(srcInfo);
		Class<?> c;
		try {
			c = pool.getClassLoader().loadClass(className);
		}
		catch (ClassNotFoundException e) {
			CtClass cc = generateSimplifiedWrapper(pool, className, metadata);
			c = cc.toClass(MethodHandles.lookup());
		}

		// Return Op instance
		return c.getDeclaredConstructor(metadata.constructorClasses())
			.newInstance(metadata.constructorArgs(originalOp));
	}
	
	// TODO: delete. We cannot instantiate the Op without having the simplifier/focuser Functions
//	/**
//	 * Creates a Class given an Op and a set of {@link Simplifier}s. This class:
//	 * <ul>
//	 * <li>is of the same functional type as the given Op
//	 * <li>has type arguments that are of the simplified form of the type
//	 * arguments of the given Op (these arguments are dictated by the list of
//	 * {@code Simplifier}s.
//	 * <li>
//	 * 
//	 * @param m - the {@link OpMethod}
//	 * @param dependencies - the {@OpDependency}s associated with {@code m}
//	 * @return a partial application of {@code m} with all {@link OpDependency}s
//	 *         injected.
//	 * @throws Throwable
//	 */
//	private Object javassistOp(Object originalOp) throws Throwable {
//		ClassPool pool = ClassPool.getDefault();
//
//		// Create wrapper class
//		String className = formClassName(srcInfo);
//		Class<?> opType = Types.raw(srcInfo.opType());
//		Class<?> c;
//		try {
//			c = pool.getClassLoader().loadClass(className);
//		}
//		catch (ClassNotFoundException e) {
//			CtClass cc = generateSimplifiedWrapper(pool, className, opType);
//			c = cc.toClass(MethodHandles.lookup());
//		}

//		// Return Op instance
//		List<Class<?>> constructorArgs = simplifiers.stream().map(
//			simplifier -> Simplifier.class).collect(Collectors.toList());
//		constructorArgs.add(opType);
//		List<Object> args = new ArrayList<>(simplifiers);
//		args.add(originalOp);
//		return c.getDeclaredConstructor(constructorArgs.toArray(Class[]::new))
//			.newInstance(args.toArray());
//	}

//	private CtClass generateSimplifiedWrapper(ClassPool pool, String className,
//		Class<?> opType) throws Throwable
//	{
//		CtClass cc = pool.makeClass(className);
//
//		// Add implemented interface
//		CtClass jasOpType = pool.get(opType.getName());
//		cc.addInterface(jasOpType);
//
//		// Add Simplifier fields
//		for (int i = 0; i < simplifiers.size(); i++) {
//			CtField f = createMutatorField(pool, cc, "simplifier", i);
//			cc.addField(f);
//		}
//
//		// Add Op field
//		CtField opField = createOpField(pool, cc, opType);
//		cc.addField(opField);
//
//		// Add constructor to take the Simplifiers, as well as the original op.
//		CtConstructor constructor = CtNewConstructor.make(createConstructor(cc,
//			opType), cc);
//		cc.addConstructor(constructor);
//
//		// add functional interface method
//		CtMethod functionalMethod = CtNewMethod.make(createFunctionalMethod(opType),
//			cc);
//		cc.addMethod(functionalMethod);
//		return cc;
//	}

	private CtClass generateSimplifiedWrapper(ClassPool pool, String className, SimplificationMetadata metadata) throws Throwable
	{
		CtClass cc = pool.makeClass(className);

		// Add implemented interface
		CtClass jasOpType = pool.get(metadata.opType().getName());
		cc.addInterface(jasOpType);

		// Add input simplifier fields
		generateNFields(pool, cc, "inputSimplifier", metadata.numInputs());

		// Add input focuser fields
		generateNFields(pool, cc, "inputFocuser", metadata.numInputs());
		
		// Add output simplifier field
		generateNFields(pool, cc, "outputSimplifier", 1);
		
		// Add output focuser field
		generateNFields(pool, cc, "outputFocuser", 1);

		// Add Op field
		CtField opField = createOpField(pool, cc, metadata.opType());
		cc.addField(opField);

		// Add constructor to take the Simplifiers, as well as the original op.
		CtConstructor constructor = CtNewConstructor.make(createConstructor(cc,
			metadata.opType(), metadata.numInputs()), cc);
		cc.addConstructor(constructor);

		// add functional interface method
		CtMethod functionalMethod = CtNewMethod.make(createFunctionalMethod(metadata),
			cc);
		cc.addMethod(functionalMethod);
		return cc;
	}

	private void generateNFields(ClassPool pool, CtClass cc, String base,
		int numFields) throws NotFoundException, CannotCompileException
	{
		for (int i = 0; i < numFields; i++) {
			CtField f = createMutatorField(pool, cc, base, i);
			cc.addField(f);
		}
	}

	// TODO: consider correctness
	private String formClassName(OpInfo original) {
		// package name - required to be this package for the Lookup to work
		String packageName = this.getClass().getPackageName();
		StringBuilder sb = new StringBuilder(packageName);

		// class name
		String implementationName = original.implementationName();
		String originalName = implementationName.substring(implementationName
			.lastIndexOf('.')); // we only want the class name (with a preceeding dot)
		sb.append(originalName + "_simplified_");
		Stream<String> memberNames = struct().members().stream().map(
			member -> member.getRawType().getSimpleName());
		Iterable<String> iterableNames = (Iterable<String>) memberNames::iterator;
		String simplifiedParameters = String.join("_", iterableNames);
		sb.append(simplifiedParameters);
		return sb.toString();
	}

	private CtField createMutatorField(ClassPool pool, CtClass cc, String name, int i) throws NotFoundException,
		CannotCompileException
	{
		Class<?> simplifierClass = Function.class;
		CtClass fType = pool.get(simplifierClass.getName());
		CtField f = new CtField(fType, name + i, cc);
		f.setModifiers(Modifier.PRIVATE + Modifier.FINAL);
		return f;
	}

	private CtField createOpField(ClassPool pool, CtClass cc, Class<?> opType)
		throws NotFoundException, CannotCompileException
	{
		CtClass fType = pool.get(opType.getName());
		CtField f = new CtField(fType, "op", cc);
		f.setModifiers(Modifier.PRIVATE + Modifier.FINAL);
		return f;
	}

	private String createConstructor(CtClass cc, Class<?> opClass) {
		StringBuilder sb = new StringBuilder();
		// constructor signature
		sb.append("public " + cc.getSimpleName() + "(");
		for (int i = 0; i < focuserInfos.size(); i++) {
			// TODO: can this just be Simplifier.class?
			Class<?> depClass = Simplifier.class;
			sb.append(depClass.getName() + " simplifier" + i);
			sb.append(",");
		}
		sb.append(" " + opClass.getName() + " op");
		sb.append(") {");

		// assign dependencies to field
		for (int i = 0; i < focuserInfos.size(); i++) {
			sb.append("this.simplifier" + i + " = simplifier" + i + ";");
		}
		sb.append("this.op = op;");
		sb.append("}");
		return sb.toString();
	}

	private String createConstructor(CtClass cc, Class<?> opClass, int numInputs) {
		StringBuilder sb = new StringBuilder();
		// constructor signature
		sb.append("public " + cc.getSimpleName() + "(");
		Class<?> depClass = Function.class;
		// input simplifiers
		for (int i = 0; i < numInputs; i++) {
			sb.append(depClass.getName() + " inputSimplifier" + i);
			sb.append(",");
		}
		// input focusers
		for (int i = 0; i < numInputs; i++) {
			sb.append(depClass.getName() + " inputFocuser" + i);
			sb.append(",");
		}
		// output simplifier
		sb.append(depClass.getName() + " outputSimplifier0" );
		sb.append(",");
		// output focuser
		sb.append(depClass.getName() + " outputFocuser0" );
		sb.append(",");
		// op
		sb.append(" " + opClass.getName() + " op");
		sb.append(") {");

		// assign dependencies to field
		for (int i = 0; i < numInputs; i++) {
			sb.append("this.inputSimplifier" + i + " = inputSimplifier" + i + ";");
		}
		for (int i = 0; i < numInputs; i++) {
			sb.append("this.inputFocuser" + i + " = inputFocuser" + i + ";");
		}
		sb.append("this.outputSimplifier0" + " = outputSimplifier0" + ";");
		sb.append("this.outputFocuser0" + " = outputFocuser0" + ";");
		sb.append("this.op = op;");
		sb.append("}");
		return sb.toString();
	}

//	private String createFunctionalMethod(Class<?> opType) {
//		StringBuilder sb = new StringBuilder();
//
//		// determine the name of the functional method
//		Method m = ParameterStructs.singularAbstractMethod(opType);
//		String methodName = m.getName();
//
//		// method modifiers
//		boolean isVoid = m.getReturnType() == void.class;
//		sb.append("public " + (isVoid ? "void" : "Object") + " " + methodName +
//			"(");
//
//		// method inputs
//		int applyInputs = OpUtils.inputs(struct()).size();
//		for (int i = 0; i < applyInputs; i++) {
//			sb.append(" Object in" + i);
//			if (i < applyInputs - 1) sb.append(",");
//		}
//
//		// method body
//		sb.append(" ) {");
//		// focus all inputs
//		for (int i = 0; i < simplifiers.size(); i++) {
//			Type focused = Types.raw(simplifiers.get(i).focusedType());
//			Type simple = Types.raw(simplifiers.get(i).simpleType());
//			sb.append(focused.getTypeName() + " focused" + i + " = (" + focused.getTypeName() + ") simplifier" + i +
//				".focus((" + simple.getTypeName() + ") in" + i + ");");
//		}
//
//		// call the op, return the output
//		sb.append("return op." + methodName + "(");
//		for (int i = 0; i < simplifiers.size(); i++) {
//			sb.append(" focused" + i);
//			if (i + 1 < simplifiers.size()) sb.append(",");
//		}
//
//		sb.append("); }");
//		return sb.toString();
//	}

	/**
	 * Creates the functional method of a simplified Op. This functional method
	 * must:
	 * <ol>
	 * <li>Simplify all inputs using the {@link Function}s provided by the
	 * {@link SimplifiedOpRef}
	 * <li>Focus the simplified inputs using the {@link Function}s provided by the
	 * {@link SimplifiedOpInfo}
	 * <li>Call the {@code Op} using the focused inputs.
	 * </ol>
	 * <b>NB</b> The Javassist compiler
	 * <a href="https://www.javassist.org/tutorial/tutorial3.html#generics">does
	 * not fully support generics</a>, so we must ensure that the types are raw.
	 * At compile time, the raw types are equivalent to the generic types, so this
	 * should not pose any issues.
	 * 
	 * @param opType - used to determine what the call to the original Op should
	 *          look like.
	 * @param typings - a {@link SimplificationTypings} object declaring the
	 *          typing transformation from original {@OpRef} input to original
	 *          {@link OpInfo} input.
	 * @return a {@link String} that can be used by
	 *         {@link CtMethod#make(String, CtClass)} to generate the functional
	 *         method of the simplified Op
	 */
	private String createFunctionalMethod(SimplificationMetadata metadata) {
		StringBuilder sb = new StringBuilder();

		// determine the name of the functional method
		Method m = ParameterStructs.singularAbstractMethod(metadata.opType());

		//-- signature -- //
		sb.append(generateSignature(m));

		//-- body --//

		// preprocessing
		sb.append(" {");
		sb.append(fMethodPreprocessing(metadata));

		// processing
		sb.append(metadata.originalOutput().getTypeName() + " originalOut = ");
		sb.append("op." + m.getName() + "(");
		for (int i = 0; i < metadata.numInputs(); i++) {
			sb.append(" focused" + i);
			if (i + 1 < metadata.numInputs()) sb.append(",");
		}
		sb.append(");");

		// postprocessing
		sb.append(fMethodPostProcessing(metadata));

		sb.append("return out;");
		sb.append("}");
		return sb.toString();
	}

	private String fMethodPostProcessing(SimplificationMetadata metadata) {
		StringBuilder sb = new StringBuilder();

		// simplify output
		Type original = Types.raw(metadata.originalOutput());
		Type simple = Types.raw(metadata.simpleOutput());
		sb.append(simple.getTypeName() + " simpleOut = (" + simple
			.getTypeName() + ") outputSimplifier0.apply((" + original
				.getTypeName() + ") originalOut);");	
	
		Type focused = Types.raw(metadata.focusedOutput());
		Type unfocused = Types.raw(metadata.unfocusedOutput());
		sb.append(focused.getTypeName() + " out = (" + focused
			.getTypeName() + ") outputFocuser0.apply((" + unfocused
				.getTypeName() + ") simpleOut);");
		
		return sb.toString();
	}

	private String fMethodPreprocessing(SimplificationMetadata metadata) {
		StringBuilder sb = new StringBuilder();

		// simplify all inputs
		for (int i = 0; i < metadata.numInputs(); i++) {
			Type focused = Types.raw(metadata.originalInputs()[i]);
			Type simple = Types.raw(metadata.simpleInputs()[i]);
			sb.append(simple.getTypeName() + " simple" + i + " = (" + simple
				.getTypeName() + ") inputSimplifier" + i + ".apply((" + focused
					.getTypeName() + ") in" + i + ");");
		}

		// focus all inputs
		for (int i = 0; i < metadata.numInputs(); i++) {
			Type focused = Types.raw(metadata.focusedInputs()[i]);
			Type unfocused = Types.raw(metadata.unfocusedInputs()[i]);
			sb.append(focused.getTypeName() + " focused" + i + " = (" + focused
				.getTypeName() + ") inputFocuser" + i + ".apply((" + unfocused
					.getTypeName() + ") simple" + i + ");");
		}

	return sb.toString();
}

	private String generateSignature(Method m) {
		StringBuilder sb = new StringBuilder();
		String methodName = m.getName();

		// method modifiers
		boolean isVoid = m.getReturnType() == void.class;
		sb.append("public " + (isVoid ? "void" : "Object") + " " + methodName +
			"(");

		int applyInputs = OpUtils.inputs(struct()).size();
		for (int i = 0; i < applyInputs; i++) {
			sb.append(" Object in" + i);
			if (i < applyInputs - 1) sb.append(",");
		}

		sb.append(" )");

		return sb.toString();
	}

	@Override
	public boolean isSimplifiable() {
		return false;
	}

	@Override
	public String toString() {
		return OpUtils.opString(this);
	}
	
	@Override
	public int compareTo(final OpInfo that) {
		// compare priorities
		if (this.priority() < that.priority()) return 1;
		if (this.priority() > that.priority()) return -1;

		// compare implementation names 
		int implNameDiff = MiscUtils.compare(this.implementationName(), that.implementationName());
		if(implNameDiff != 0) return implNameDiff; 

		// compare structs if the OpInfos are "sibling" SimplifiedOpInfos
		if(that instanceof SimplifiedOpInfo) return compareToSimplifiedInfo((SimplifiedOpInfo) that);

		return 0;
	}

	private int compareToSimplifiedInfo(SimplifiedOpInfo that) {
		// Compare structs
		List<Member<?>> theseMembers = new ArrayList<>();
		this.struct().forEach(theseMembers::add);
		List<Member<?>> thoseMembers = new ArrayList<>();
		that.struct().forEach(thoseMembers::add);
		return theseMembers.hashCode() - thoseMembers.hashCode();
	}

}
