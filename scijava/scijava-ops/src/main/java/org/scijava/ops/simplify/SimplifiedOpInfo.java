package org.scijava.ops.simplify;

import com.google.common.collect.Streams;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.scijava.ops.OpDependency;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.function.Computers;
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
	private final List<List<OpInfo>> focuserSets;
	private final List<OpInfo> outputSimplifiers;

	public SimplifiedOpInfo(OpInfo info, OpEnvironment env) {
		this.srcInfo = info;
		Type[] args = OpUtils.inputs(srcInfo.struct()).stream() //
				.map(m -> m.getType()) //
				.toArray(Type[]::new);
		this.focuserSets = SimplificationUtils.focusArgs(env, args);
		Type outType = info.output().getType();
		this.outputSimplifiers = SimplificationUtils.getSimplifiers(env, outType);
	}

	public List<List<OpInfo>> getFocusers(){
		return focuserSets;
	}

	public List<OpInfo> getOutputSimplifiers(){
		return outputSimplifiers;
	}

	public OpInfo srcInfo() {
		return srcInfo;
	}

	@Override
	public Type opType() {
		return srcInfo.opType();
	}

	@Override
	public Struct struct() {
		// TODO Auto-generated method stub
		return srcInfo.struct();
	}

	@Override
	public double priority() {
		return srcInfo.priority() - 1;
	}

	@Override
	public String implementationName() {
		return srcInfo.implementationName();
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		throw new UnsupportedOperationException(
			"Cannot create an instance without knowing the Simplifier/Focuser Functions needed to mutate the arugments!");
	}

	@Override
	public boolean isValid() {
		return srcInfo.isValid();
	}

	@Override
	public ValidityException getValidityException() {
		return srcInfo.getValidityException();
	}

	@Override
	public AnnotatedElement getAnnotationBearer() {
		return srcInfo.getAnnotationBearer();
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
			throw new IllegalArgumentException(
				"Failed to invoke simplification of Op: \n" + srcInfo +
					"\nProvided Op dependencies were: " + Objects.toString(dependencies),
				ex);
		}
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
		String className = formClassName(metadata);
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

	// TODO: consider correctness
	private String formClassName(SimplificationMetadata metadata) {
		// package name - required to be this package for the Lookup to work
		String packageName = this.getClass().getPackageName();
		StringBuilder sb = new StringBuilder(packageName + ".");

		// class name
		String implementationName = metadata.info().implementationName();
		String originalName = implementationName.substring(implementationName
			.lastIndexOf('.') + 1); // we only want the class name
		Stream<String> memberNames = //
			Streams.concat(Arrays.stream(metadata.originalInputs()), //
				Stream.of(metadata.originalOutput())) //
				.map(type -> getClassName(Types.raw(type)));
		Iterable<String> iterableNames = (Iterable<String>) memberNames::iterator;
		String simplifiedParameters = String.join("_", iterableNames);
		String className = originalName.concat("_simplified_" + simplifiedParameters);
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
	 * @param c - the {@link Class} for which we need a name
	 * @return - a name that is legal as part of a class name.
	 */
	private String getClassName(Class<?> clazz) {
		String className = clazz.getSimpleName();
		if(className.chars().allMatch(c -> Character.isJavaIdentifierPart(c)))
			return className;
		if(clazz.isArray())
			return clazz.getComponentType().getSimpleName() + "_Arr";
		return className;
	}

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
		CtField opField = createOpField(pool, cc, metadata.opType(), "op");
		cc.addField(opField);

		// Add copy Op field iff not pure output
		if(metadata.hasCopyOp()) {
			CtField copyOpField = createOpField(pool, cc, Computers.Arity1.class,
				"copyOp");
			cc.addField(copyOpField);
		}

		// Add constructor to take the Simplifiers, as well as the original op.
		CtConstructor constructor = CtNewConstructor.make(createConstructor(cc,
			metadata), cc);
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
			CtField f = createMutatorField(pool, cc, Function.class, base + i);
			cc.addField(f);
		}
	}

	private CtField createMutatorField(ClassPool pool, CtClass cc, Class<?> fieldType, String name) throws NotFoundException,
	CannotCompileException
{
	CtClass fType = pool.get(fieldType.getName());
	CtField f = new CtField(fType, name, cc);
	f.setModifiers(Modifier.PRIVATE + Modifier.FINAL);
	return f;
}

	private CtField createOpField(ClassPool pool, CtClass cc, Class<?> opType, String fieldName)
			throws NotFoundException, CannotCompileException
		{
			CtClass fType = pool.get(opType.getName());
			CtField f = new CtField(fType, fieldName, cc);
			f.setModifiers(Modifier.PRIVATE + Modifier.FINAL);
			return f;
		}

	private String createConstructor(CtClass cc, SimplificationMetadata metadata) {
		StringBuilder sb = new StringBuilder();
		// constructor signature
		sb.append("public " + cc.getSimpleName() + "(");
		Class<?> depClass = Function.class;
		// input simplifiers
		for (int i = 0; i < metadata.numInputs(); i++) {
			sb.append(depClass.getName() + " inputSimplifier" + i);
			sb.append(",");
		}
		// input focusers
		for (int i = 0; i < metadata.numInputs(); i++) {
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
		Class<?> opClass = metadata.opType();
		sb.append(" " + opClass.getName() + " op");
		// copy op
		if(metadata.hasCopyOp()) {
			Class<?> copyOpClass = Computers.Arity1.class;
			sb.append(", " + copyOpClass.getName() + " copyOp");
		}
		sb.append(") {");

		// assign dependencies to field
		for (int i = 0; i < metadata.numInputs(); i++) {
			sb.append("this.inputSimplifier" + i + " = inputSimplifier" + i + ";");
		}
		for (int i = 0; i < metadata.numInputs(); i++) {
			sb.append("this.inputFocuser" + i + " = inputFocuser" + i + ";");
		}
		sb.append("this.outputSimplifier0" + " = outputSimplifier0" + ";");
		sb.append("this.outputFocuser0" + " = outputFocuser0" + ";");
		sb.append("this.op = op;");
		if(metadata.hasCopyOp()) {
			sb.append("this.copyOp = copyOp;");
		}
		sb.append("}");
		return sb.toString();
	}

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
	 * @param metadata - the {@link SimplificationMetadata} containing the
	 *          information needed to write the method.
	 * @return a {@link String} that can be used by
	 *         {@link CtMethod#make(String, CtClass)} to generate the functional
	 *         method of the simplified Op
	 */
	private String createFunctionalMethod(SimplificationMetadata metadata) {
		StringBuilder sb = new StringBuilder();

		// determine the name of the functional method
		Method m = ParameterStructs.singularAbstractMethod(metadata.opType());
		// determine the name of the output:
		String opOutput = "";
		int ioIndex = metadata.ioArgIndex();
		if(ioIndex == -1) {
			opOutput = "originalOut";
		}
		else {
			opOutput = "focused" + ioIndex;
		}

		//-- signature -- //
		sb.append(generateSignature(m));

		//-- body --//

		// preprocessing
		sb.append(" {");
		sb.append(fMethodPreprocessing(metadata));

		// processing
		if (metadata.pureOutput()) {
			sb.append(metadata.originalOutput().getTypeName() + " " + opOutput + " = ");
		}
		sb.append("op." + m.getName() + "(");
		for (int i = 0; i < metadata.numInputs(); i++) {
			sb.append(" focused" + i);
			if (i + 1 < metadata.numInputs()) sb.append(",");
		}
		sb.append(");");

		// postprocessing
		sb.append(fMethodPostprocessing(metadata, opOutput));

		// if pure output, return it
		if (metadata.pureOutput()) {
			sb.append("return out;");
		}
		sb.append("}");
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

	private String fMethodPostprocessing(SimplificationMetadata metadata, String opOutput) {
		StringBuilder sb = new StringBuilder();

		// simplify output
		Type original = Types.raw(metadata.originalOutput());
		Type simple = Types.raw(metadata.simpleOutput());
		sb.append(simple.getTypeName() + " simpleOut = (" + simple
			.getTypeName() + ") outputSimplifier0.apply((" + original
				.getTypeName() + ") " + opOutput + ");");	
	
		Type focused = Types.raw(metadata.focusedOutput());
		Type unfocused = Types.raw(metadata.unfocusedOutput());
		sb.append(focused.getTypeName() + " out = (" + focused
			.getTypeName() + ") outputFocuser0.apply((" + unfocused
				.getTypeName() + ") simpleOut);");

		// call copy op iff it exists
		if(metadata.hasCopyOp()) {
			int ioIndex = metadata.ioArgIndex();
			Type ioType = metadata.originalInputs()[ioIndex];
			String originalIOArg = "in" + ioIndex;
			sb.append("copyOp.compute((" + focused.getTypeName() + ") out, (" + ioType.getTypeName() + ") " + originalIOArg + ");");
			
		}

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


}
