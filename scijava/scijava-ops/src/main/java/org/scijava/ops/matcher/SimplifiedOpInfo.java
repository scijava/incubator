package org.scijava.ops.matcher;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scijava.ops.OpDependency;
import org.scijava.ops.OpDependencyMember;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpMethod;
import org.scijava.ops.OpUtils;
import org.scijava.ops.core.Op;
import org.scijava.ops.simplify.Identity;
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
	final ParameterizedType simplifiedType;
	
	private Struct struct;
	private ValidityException validityException;

	public SimplifiedOpInfo(OpInfo info, List<Simplifier<?, ?>> simplification) {
		this.srcInfo = info;
		this.simplifiers = simplification;
		this.simplifiedType = generateSimplifiedType(srcInfo);
		
		// NOTE: since the source Op has already been shown to be valid, there is
		// not much for us to do here.
		try {
			struct = ParameterStructs.structOf(srcInfo, simplifiedType);
			OpUtils.checkHasSingleOutput(struct);
		}
		catch (ValidityException e) {
			validityException = e;
		}
	}
	
	public boolean isIdentical() {
		return simplifiers.stream().allMatch(s -> s instanceof Identity);
	}

	// TODO: We assume that this is a Function of some type.
	// We also assume that simplifiers exist for all inputs ONLY.
	// FIXME: generalize
	private ParameterizedType generateSimplifiedType(OpInfo originalInfo)
	{
		List<Type> simplifiedTypes = simplifiers.stream().map(s -> s.simpleType()).collect(Collectors.toList());
		if (!(originalInfo.opType() instanceof ParameterizedType))
			throw new UnsupportedOperationException(
				"I am not smart enough to handle this yet.");
		simplifiedTypes.add(srcInfo.output().getType()); 
		Class<?> rawType = Types.raw(originalInfo.opType());
		return Types.parameterize(rawType, simplifiedTypes.toArray(Type[]::new));
	}

	@Override
	public Type opType() {
		return simplifiedType;
	}

	@Override
	public Struct struct() {
		return struct;
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

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		final Object op = srcInfo.createOpInstance(dependencies).object();
		try {
			return struct().createInstance(javassistOp(op));
		}
		catch (Throwable ex) {
			throw new IllegalStateException("Failed to invoke simplification of Op: \n" + srcInfo +
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
	private Object javassistOp(Object originalOp)
		throws Throwable
	{
		ClassPool pool = ClassPool.getDefault();

		// Create wrapper class
		String className = formClassName(srcInfo);
		Class<?> opType = Types.raw(simplifiedType);
		Class<?> c;
		try {
			c = pool.getClassLoader().loadClass(className);
		} catch(ClassNotFoundException e) {
			CtClass cc = generateSimplifiedWrapper(pool, className, opType);
			c = cc.toClass(MethodHandles.lookup());
		}

		// Return Op instance
		List<Class<?>> constructorArgs = simplifiers.stream().map(simplifier -> Simplifier.class)
			.collect(Collectors.toList());
		constructorArgs.add(opType);
		List<Object> args = new ArrayList<>(simplifiers);
		args.add(originalOp);
		return c.getDeclaredConstructor(constructorArgs.toArray(Class[]::new)).newInstance(args
			.toArray());
	}
	
	private CtClass generateSimplifiedWrapper(ClassPool pool, String className, Class<?> opType) throws Throwable{ 
		CtClass cc = pool.makeClass(className);

		// Add implemented interface
		CtClass jasOpType = pool.get(opType.getName());
		cc.addInterface(jasOpType);

		// Add Simplifier fields
		for (int i = 0; i < simplifiers.size(); i++) {
			CtField f = createSimplifierField(pool, cc, simplifiers.get(i), i);
			cc.addField(f);
		}
		
		// Add Op field
		CtField opField = createOpField(pool, cc, opType);
		cc.addField(opField);

		// Add constructor to take the Simplifiers, as well as the original op.
		CtConstructor constructor = CtNewConstructor.make(createConstructor(cc, opType), cc);
		cc.addConstructor(constructor);

		// add functional interface method
		CtMethod functionalMethod = CtNewMethod.make(createFunctionalMethod(opType), cc);
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
		Stream<String> memberNames = struct().members().stream().map(member -> member.getRawType().getSimpleName());
		Iterable<String> iterableNames = (Iterable<String>) memberNames::iterator;
		String simplifiedParameters = String.join("_", iterableNames);
		sb.append(simplifiedParameters);
		return sb.toString();
	}

	private CtField createSimplifierField(ClassPool pool, CtClass cc,
		Simplifier<?, ?> simplifier, int i) throws NotFoundException,
		CannotCompileException
	{
		// TODO: can we just say Simplifier.class?
		Class<?> simplifierClass = Simplifier.class;
		CtClass fType = pool.get(simplifierClass.getName());
		CtField f = new CtField(fType, "simplifier" + i, cc);
		f.setModifiers(Modifier.PRIVATE + Modifier.FINAL);
		return f;
	}
	
	private CtField createOpField(ClassPool pool, CtClass cc, Class<?> opType) throws NotFoundException,
		CannotCompileException
	{
		CtClass fType = pool.get(opType.getName());
		CtField f = new CtField(fType, "op", cc);
		f.setModifiers(Modifier.PRIVATE + Modifier.FINAL);
		return f;
	}

	private String createConstructor(CtClass cc, Class<?> opClass)	{
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
		for(int i = 0; i < simplifiers.size(); i++) {
			Type focused = simplifiers.get(i).focusedType();
			Type simple = simplifiers.get(i).simpleType();
			sb.append(focused.getTypeName() + " focused" + i + " = simplifier" + i + ".focus((" + simple.getTypeName() + ") in" + i + ");");
		}
		
		// call the op, return the output
		sb.append("return op." + methodName + "(");
		for(int i = 0; i < simplifiers.size(); i++) {
			sb.append(" focused" + i);
			if (i + 1 < simplifiers.size()) sb.append(",");
		}
		sb.append("); }");
		
//		sb.append(") { return " + m.getDeclaringClass().getName() + "." + m
//			.getName() + "(");
//		int numInputs = 0;
//		int numDependencies = 0;
//		List<Member<?>> members = struct().members().stream() //
//			.filter(member -> !(!member.isInput() && member.isOutput())) //
//			.collect(Collectors.toList());
//		Parameter[] mParams = m.getParameters();
//		for (int i = 0; i < mParams.length; i++) {
//			Class<?> paramRawType = Types.raw(mParams[i].getParameterizedType());
//			String castClassName = paramRawType.getName();
//			if (paramRawType.isArray()) castClassName = paramRawType.getSimpleName();
//			sb.append("(" + castClassName + ") ");
//			if (mParams[i].getAnnotation(OpDependency.class) != null) sb.append(
//				"dep" + numDependencies++);
//			else sb.append("in" + numInputs++);
//			if (numDependencies + numInputs < members.size()) sb.append(", ");
//		}
//		sb.append("); }");
		return sb.toString();
	}
	
}