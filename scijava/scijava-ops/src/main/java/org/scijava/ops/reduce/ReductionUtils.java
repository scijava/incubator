package org.scijava.ops.reduce;

import com.google.common.collect.Streams;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.scijava.ops.OpUtils;
import org.scijava.ops.simplify.Simplifier;
import org.scijava.param.Optional;
import org.scijava.param.ParameterStructs;
import org.scijava.struct.Member;
import org.scijava.types.Types;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class ReductionUtils {

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
	 * @param reducedInfo - the {@link ReducedOpInfo} containing the information
	 *          required to reduce {@code originalOp}.
	 * @return a wrapper of {@code originalOp} taking arguments that are then
	 *         mutated to satisfy {@code originalOp}, producing outputs that are
	 *         then mutated to satisfy the desired output of the wrapper.
	 * @throws Throwable
	 */
	protected static Object javassistOp(Object originalOp, ReducedOpInfo reducedInfo) throws Throwable {
		ClassPool pool = ClassPool.getDefault();

		// Create wrapper class
		String className = formClassName(reducedInfo);
		Class<?> c;
		try {
			c = pool.getClassLoader().loadClass(className);
		}
		catch (ClassNotFoundException e) {
			CtClass cc = generateSimplifiedWrapper(pool, className, reducedInfo);
			c = cc.toClass(MethodHandles.lookup());
		}

		// Return Op instance
		return c.getDeclaredConstructor(Types.raw(reducedInfo.srcInfo().opType()))
			.newInstance(originalOp);
	}

	// TODO: consider correctness
	private static String formClassName(ReducedOpInfo reducedInfo) {
		// package name - required to be this package for the Lookup to work
		String packageName = ReductionUtils.class.getPackageName();
		StringBuilder sb = new StringBuilder(packageName + ".");

		// class name
		String implementationName = reducedInfo.implementationName();
		String originalName = implementationName.substring(implementationName
			.lastIndexOf('.') + 1); // we only want the class name
		Stream<String> memberNames = //
			Streams.concat(Arrays.stream(OpUtils.inputTypes(reducedInfo.struct())), //
				Stream.of(OpUtils.outputType(reducedInfo.struct()))) //
				.map(type -> getClassName(Types.raw(type)));
		Iterable<String> iterableNames = (Iterable<String>) memberNames::iterator;
		String simplifiedParameters = String.join("_", iterableNames);
		String className = originalName.concat(simplifiedParameters);
		if(className.chars().anyMatch(c -> !Character.isJavaIdentifierPart(c)))
			throw new IllegalArgumentException(className + " is not a valid class name!");

		sb.append(className);
		return sb.toString();
	}

	/**
	 * {@link Class}es of array types return "[]" when
	 * {@link Class#getSimpleName()} is called. Those characters are invalid in a
	 * class name, so we exchange them for the suffix "_Arr".
	 * 
	 * @param clazz - the {@link Class} for which we need a name
	 * @return - a name that is legal as part of a class name.
	 */
	private static String getClassName(Class<?> clazz) {
		String className = clazz.getSimpleName();
		if(className.chars().allMatch(c -> Character.isJavaIdentifierPart(c)))
			return className;
		if(clazz.isArray())
			return clazz.getComponentType().getSimpleName() + "_Arr";
		return className;
	}

	private static CtClass generateSimplifiedWrapper(ClassPool pool, String className, ReducedOpInfo reducedInfo) throws Throwable
	{
		CtClass cc = pool.makeClass(className);
		// Add implemented interface
		CtClass jasOpType = pool.get(Types.raw(reducedInfo.opType()).getName());
		cc.addInterface(jasOpType);

		// Add Op field
		CtField opField = createOpField(pool, cc, Types.raw(reducedInfo.srcInfo().opType()), "op");
		cc.addField(opField);

		// Add constructor to take the Simplifiers, as well as the original op.
		CtConstructor constructor = CtNewConstructor.make(createConstructor(cc,
			reducedInfo), cc);
		cc.addConstructor(constructor);

		// add functional interface method
		CtMethod functionalMethod = CtNewMethod.make(createFunctionalMethod(reducedInfo),
			cc);
		cc.addMethod(functionalMethod);
		return cc;
	}

	private static CtField createOpField(ClassPool pool, CtClass cc, Class<?> opType, String fieldName)
			throws NotFoundException, CannotCompileException
		{
			CtClass fType = pool.get(opType.getName());
			CtField f = new CtField(fType, fieldName, cc);
			f.setModifiers(Modifier.PRIVATE + Modifier.FINAL);
			return f;
		}

	private static String createConstructor(CtClass cc, ReducedOpInfo reducedInfo) {
		StringBuilder sb = new StringBuilder();
		// constructor signature
		sb.append("public " + cc.getSimpleName() + "(");
		// argument - original op
		Class<?> opClass = Types.raw(reducedInfo.srcInfo().opType());
		sb.append(" " + opClass.getName() + " op");
		sb.append(") {");

		sb.append("this.op = op;");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Creates the functional method of a reduced Op. This functional method must:
	 * <ol>
	 * <li>Call the {@code Op} using the required <b>pure</b> inputs, followed by
	 * {@code null} {@link Optional} <b>pure</b> arguments, followed by the i/o
	 * argument (iff it exists).
	 * </ol>
	 * <b>NB</b> The Javassist compiler
	 * <a href="https://www.javassist.org/tutorial/tutorial3.html#generics">does
	 * not fully support generics</a>, so we must ensure that the types are raw.
	 * At compile time, the raw types are equivalent to the generic types, so this
	 * should not pose any issues.
	 * 
	 * @param info - the {@link ReducedOpInfo} containing the
	 *          information needed to write the method.
	 * @return a {@link String} that can be used by
	 *         {@link CtMethod#make(String, CtClass)} to generate the functional
	 *         method of the simplified Op
	 */
	private static String createFunctionalMethod(ReducedOpInfo info) {
		StringBuilder sb = new StringBuilder();

		// determine the name of the functional method
		Method m = ParameterStructs.singularAbstractMethod(Types.raw(info.opType()));
		// determine the name of the output:
		String opOutput = "out";

		//-- signature -- //
		sb.append(generateSignature(m));

		//-- body --//

		// processing
		sb.append(" {");
		if (OpUtils.hasPureOutput(info)) {
			sb.append(OpUtils.outputType(info.struct()).getTypeName() + " " + opOutput + " = ");
		}
		sb.append("op." + m.getName() + "(");
		int i;
		List<Member<?>> totalArguments = OpUtils.inputs(info.srcInfo().struct());
		int totalArgs = OpUtils.inputs(info.srcInfo().struct()).size();
		long totalOptionals = OpUtils.inputs(info.srcInfo().struct())
			.parallelStream().filter(member -> info.srcInfo().isOptional(member))
			.count();
		long neededOptionals = totalOptionals - info.paramsReduced();
		int reducedArg = 0;
		int optionals = 0;
		for (i = 0; i < totalArgs; i++) {
			// NB due to our optionality paradigm (if there are n optional parameters,
			// they must be the last n), we just need to pass null for the last n
			// arguments
			if (!info.srcInfo().isOptional(totalArguments.get(i))) {
				sb.append(" in" + reducedArg++);
			} else if (optionals < neededOptionals) {
				sb.append(" in" + reducedArg++);
				optionals++;
			}
			else {
				sb.append(" null");
			}
			if (i + 1 < totalArguments.size()) sb.append(",");
		}

		sb.append(");");

		// if pure output, return it
		if (OpUtils.hasPureOutput(info)) {
			sb.append("return out;");
		}
		sb.append("}");
		return sb.toString();
	}

	private static String generateSignature(Method m) {
		StringBuilder sb = new StringBuilder();
		String methodName = m.getName();

		// method modifiers
		boolean isVoid = m.getReturnType() == void.class;
		sb.append("public " + (isVoid ? "void" : "Object") + " " + methodName +
			"(");

		int inputs = m.getParameterCount();
		for (int i = 0; i < inputs; i++) {
			sb.append(" Object in" + i);
			if (i < inputs - 1) sb.append(",");
		}

		sb.append(" )");

		return sb.toString();
	}

}
