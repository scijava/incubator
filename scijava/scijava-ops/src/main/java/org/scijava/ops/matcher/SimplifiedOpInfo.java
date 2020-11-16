
package org.scijava.ops.matcher;

import com.google.common.collect.Streams;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scijava.log.Logger;
import org.scijava.ops.OpDependency;
import org.scijava.ops.OpDependencyMember;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpMethod;
import org.scijava.ops.OpUtils;
import org.scijava.ops.core.Op;
import org.scijava.ops.simplify.Identity;
import org.scijava.ops.simplify.SimplifiedMember;
import org.scijava.ops.simplify.Simplifier;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.types.Types;

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

	final OpInfo srcInfo;
	final List<Simplifier<?, ?>> simplifiers;

	private Struct struct;
	private ParameterizedType simplifiedType;
	private ValidityException validityException;

	public SimplifiedOpInfo(OpInfo info, List<Simplifier<?, ?>> simplification) {
		this.srcInfo = info;
		this.simplifiers = simplification;

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

		// generate simplified type
		List<Type> simplifiedTypes = simplifiers.stream().map(s -> s.simpleType())
			.collect(Collectors.toList());
		if (!(originalInfo.opType() instanceof ParameterizedType))
			throw new UnsupportedOperationException(
				"I am not smart enough to handle this yet.");
		simplifiedTypes.add(srcInfo.output().getType());
		Class<?> rawType = Types.raw(originalInfo.opType());
		simplifiedType = Types.parameterize(rawType, simplifiedTypes.toArray(Type[]::new));
	}

	@Override
	public Type opType() {
			if (simplifiedType == null)
				generateSimplifiedType(srcInfo);
			return simplifiedType;
	}

	@Override
	public Struct struct() {
		if (struct == null) generateStruct();
		return struct;
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
		Iterator<Simplifier<?, ?>> itr = simplifiers.iterator();
			final List<Member<?>> originalItems = srcInfo.struct().members();
			final List<Member<?>> newItems = new ArrayList<>();
			for (Member<?> m : originalItems) {
				if(m.isInput()) {
					try {
						SimplifiedMember<?> sm = new SimplifiedMember<>(m, itr.next().simpleType());
						newItems.add(sm);
					} catch(NoSuchElementException e) {
						throw e;
					}
				}
				else {
					newItems.add(m);
				}
			}
		struct = () -> newItems;
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
		final Object op = srcInfo.createOpInstance(dependencies).object();
		try {
			return struct().createInstance(javassistOp(op));
		}
		catch (Throwable ex) {
			throw new IllegalStateException(
				"Failed to invoke simplification of Op: \n" + srcInfo +
					"\nProvided Op dependencies were: " + Objects.toString(dependencies),
				ex);
		}
	}

	/**
	 * Creates a <b>simplified</b> version of the original Op, whose parameter
	 * types are dictated by the {@code focusedType}s of this info's
	 * {@link Simplifier}s. The resulting Op will use {@code refSimplifiers} to
	 * simplify the inputs, and then will use this info's {@code simplifier}s to
	 * focus the simplified inputs into types suitable for the original Op.
	 * 
	 * @param dependencies - this Op's dependencies
	 * @param refSimplifiers - the simplifiers whose {@code focusedType}s should
	 *          define the input parameter types.
	 * @see #createOpInstance(List) - used when there are no associated
	 *      {@code refSimplifier}s.
	 */
	public StructInstance<?> createOpInstance(List<?> dependencies, List<Simplifier<?, ?>> refSimplifiers) {
		final Object op = srcInfo.createOpInstance(dependencies).object();
		try {
			return struct().createInstance(javassistOp(op, refSimplifiers));
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
	private Object javassistOp(Object originalOp, List<Simplifier<?, ?>> refSimplifiers) throws Throwable {
		ClassPool pool = ClassPool.getDefault();

		// Create wrapper class
		String className = formClassName(srcInfo);
		Class<?> opType = Types.raw(srcInfo.opType());
		Class<?> c;
		try {
			c = pool.getClassLoader().loadClass(className);
		}
		catch (ClassNotFoundException e) {
			CtClass cc = generateSimplifiedWrapper(pool, className, opType, refSimplifiers);
			c = cc.toClass(MethodHandles.lookup());
		}

		// Return Op instance
		List<Class<?>> constructorArgs = Streams.concat(refSimplifiers.stream(), simplifiers.stream()).map(
			simplifier -> Simplifier.class).collect(Collectors.toList());
		constructorArgs.add(opType);
		List<Object> args = new ArrayList<>(refSimplifiers);
		args.addAll(simplifiers);
		args.add(originalOp);
		return c.getDeclaredConstructor(constructorArgs.toArray(Class[]::new))
			.newInstance(args.toArray());
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
	 * @param m - the {@link OpMethod}
	 * @param dependencies - the {@OpDependency}s associated with {@code m}
	 * @return a partial application of {@code m} with all {@link OpDependency}s
	 *         injected.
	 * @throws Throwable
	 */
	private Object javassistOp(Object originalOp) throws Throwable {
		ClassPool pool = ClassPool.getDefault();

		// Create wrapper class
		String className = formClassName(srcInfo);
		Class<?> opType = Types.raw(srcInfo.opType());
		Class<?> c;
		try {
			c = pool.getClassLoader().loadClass(className);
		}
		catch (ClassNotFoundException e) {
			CtClass cc = generateSimplifiedWrapper(pool, className, opType);
			c = cc.toClass(MethodHandles.lookup());
		}

		// Return Op instance
		List<Class<?>> constructorArgs = simplifiers.stream().map(
			simplifier -> Simplifier.class).collect(Collectors.toList());
		constructorArgs.add(opType);
		List<Object> args = new ArrayList<>(simplifiers);
		args.add(originalOp);
		return c.getDeclaredConstructor(constructorArgs.toArray(Class[]::new))
			.newInstance(args.toArray());
	}

	private CtClass generateSimplifiedWrapper(ClassPool pool, String className,
		Class<?> opType) throws Throwable
	{
		CtClass cc = pool.makeClass(className);

		// Add implemented interface
		CtClass jasOpType = pool.get(opType.getName());
		cc.addInterface(jasOpType);

		// Add Simplifier fields
		for (int i = 0; i < simplifiers.size(); i++) {
			CtField f = createSimplifierField(pool, cc, "simplifier", i);
			cc.addField(f);
		}

		// Add Op field
		CtField opField = createOpField(pool, cc, opType);
		cc.addField(opField);

		// Add constructor to take the Simplifiers, as well as the original op.
		CtConstructor constructor = CtNewConstructor.make(createConstructor(cc,
			opType), cc);
		cc.addConstructor(constructor);

		// add functional interface method
		CtMethod functionalMethod = CtNewMethod.make(createFunctionalMethod(opType),
			cc);
		cc.addMethod(functionalMethod);
		return cc;
	}

	private CtClass generateSimplifiedWrapper(ClassPool pool, String className,
		Class<?> opType, List<Simplifier<?, ?>> refSimplifiers) throws Throwable
	{
		CtClass cc = pool.makeClass(className);

		// Add implemented interface
		CtClass jasOpType = pool.get(opType.getName());
		cc.addInterface(jasOpType);

		// Add ref Simplifier fields
		for (int i = 0; i < refSimplifiers.size(); i++) {
			CtField f = createSimplifierField(pool, cc, "refSimplifier", i);
			cc.addField(f);
		}

		// Add info Simplifier fields
		for (int i = 0; i < simplifiers.size(); i++) {
			CtField f = createSimplifierField(pool, cc, "infoSimplifier", i);
			cc.addField(f);
		}

		// Add Op field
		CtField opField = createOpField(pool, cc, opType);
		cc.addField(opField);

		// Add constructor to take the Simplifiers, as well as the original op.
		CtConstructor constructor = CtNewConstructor.make(createConstructor(cc,
			opType, refSimplifiers.size()), cc);
		cc.addConstructor(constructor);

		// add functional interface method
		CtMethod functionalMethod = CtNewMethod.make(createFunctionalMethod(opType, refSimplifiers),
			cc);
		cc.addMethod(functionalMethod);
		return cc;
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

	private CtField createSimplifierField(ClassPool pool, CtClass cc, String name, int i) throws NotFoundException,
		CannotCompileException
	{
		// TODO: can we just say Simplifier.class?
		Class<?> simplifierClass = Simplifier.class;
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
		for (int i = 0; i < simplifiers.size(); i++) {
			// TODO: can this just be Simplifier.class?
			Class<?> depClass = Simplifier.class;
			sb.append(depClass.getName() + " simplifier" + i);
			sb.append(",");
		}
		sb.append(" " + opClass.getName() + " op");
		sb.append(") {");

		// assign dependencies to field
		for (int i = 0; i < simplifiers.size(); i++) {
			sb.append("this.simplifier" + i + " = simplifier" + i + ";");
		}
		sb.append("this.op = op;");
		sb.append("}");
		return sb.toString();
	}

	private String createConstructor(CtClass cc, Class<?> opClass, int numRefSimplifiers) {
		StringBuilder sb = new StringBuilder();
		// constructor signature
		sb.append("public " + cc.getSimpleName() + "(");
		for (int i = 0; i < numRefSimplifiers; i++) {
			// TODO: can this just be Simplifier.class?
			Class<?> depClass = Simplifier.class;
			sb.append(depClass.getName() + " refSimplifier" + i);
			sb.append(",");
		}
		for (int i = 0; i < simplifiers.size(); i++) {
			// TODO: can this just be Simplifier.class?
			Class<?> depClass = Simplifier.class;
			sb.append(depClass.getName() + " infoSimplifier" + i);
			sb.append(",");
		}
		sb.append(" " + opClass.getName() + " op");
		sb.append(") {");

		// assign dependencies to field
		for (int i = 0; i < numRefSimplifiers; i++) {
			sb.append("this.refSimplifier" + i + " = refSimplifier" + i + ";");
		}
		for (int i = 0; i < simplifiers.size(); i++) {
			sb.append("this.infoSimplifier" + i + " = infoSimplifier" + i + ";");
		}
		sb.append("this.op = op;");
		sb.append("}");
		return sb.toString();
	}

	private String createFunctionalMethod(Class<?> opType) {
		StringBuilder sb = new StringBuilder();

		// determine the name of the functional method
		Method m = ParameterStructs.singularAbstractMethod(opType);
		String methodName = m.getName();

		// method modifiers
		boolean isVoid = m.getReturnType() == void.class;
		sb.append("public " + (isVoid ? "void" : "Object") + " " + methodName +
			"(");

		// method inputs
		int applyInputs = OpUtils.inputs(struct()).size();
		for (int i = 0; i < applyInputs; i++) {
			sb.append(" Object in" + i);
			if (i < applyInputs - 1) sb.append(",");
		}

		// method body
		sb.append(" ) {");
		// focus all inputs
		for (int i = 0; i < simplifiers.size(); i++) {
			Type focused = Types.raw(simplifiers.get(i).focusedType());
			Type simple = Types.raw(simplifiers.get(i).simpleType());
			sb.append(focused.getTypeName() + " focused" + i + " = (" + focused.getTypeName() + ") simplifier" + i +
				".focus((" + simple.getTypeName() + ") in" + i + ");");
		}

		// call the op, return the output
		sb.append("return op." + methodName + "(");
		for (int i = 0; i < simplifiers.size(); i++) {
			sb.append(" focused" + i);
			if (i + 1 < simplifiers.size()) sb.append(",");
		}

		sb.append("); }");
		return sb.toString();
	}

	private String createFunctionalMethod(Class<?> opType, List<Simplifier<?, ?>> refSimplifiers) {
		StringBuilder sb = new StringBuilder();

		// determine the name of the functional method
		Method m = ParameterStructs.singularAbstractMethod(opType);
		String methodName = m.getName();

		// method modifiers
		boolean isVoid = m.getReturnType() == void.class;
		sb.append("public " + (isVoid ? "void" : "Object") + " " + methodName +
			"(");

		// method inputs
		int applyInputs = OpUtils.inputs(struct()).size();
		for (int i = 0; i < applyInputs; i++) {
			sb.append(" Object in" + i);
			if (i < applyInputs - 1) sb.append(",");
		}

		// method body
		sb.append(" ) {");
		// focus all inputs
		for (int i = 0; i < refSimplifiers.size(); i++) {
			Type focused = Types.raw(refSimplifiers.get(i).focusedType());
			Type simple = Types.raw(refSimplifiers.get(i).simpleType());
			sb.append(simple.getTypeName() + " simple" + i + " = (" + simple.getTypeName() + ") refSimplifier" + i +
				".simplify((" + focused.getTypeName() + ") in" + i + ");");
		}
		// focus all inputs
		for (int i = 0; i < simplifiers.size(); i++) {
			Type focused = Types.raw(simplifiers.get(i).focusedType());
			Type simple = Types.raw(simplifiers.get(i).simpleType());
			sb.append(focused.getTypeName() + " focused" + i + " = (" + focused.getTypeName() + ") infoSimplifier" + i +
				".focus((" + simple.getTypeName() + ") simple" + i + ");");
		}

		// call the op, return the output
		sb.append("return op." + methodName + "(");
		for (int i = 0; i < simplifiers.size(); i++) {
			sb.append(" focused" + i);
			if (i + 1 < simplifiers.size()) sb.append(",");
		}

		sb.append("); }");
		return sb.toString();
	}

}
