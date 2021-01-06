
package org.scijava.ops.simplify;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.function.Computers;
import org.scijava.ops.matcher.SimplifiedOpCandidate;
import org.scijava.ops.matcher.SimplifiedOpInfo;
import org.scijava.ops.matcher.SimplifiedOpRef;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.util.Types;

/**
 * Compiles a list of all needed Mutators (either Simplifiers or Focuser
 * {@link Function}s) for a given {@link SimplifiedOpCandidate}.
 * 
 * @author G
 */
public class SimplificationMetadata {

	private final SimplifiedOpRef ref;
	private final SimplifiedOpInfo info;

	private final List<OpInfo> refSimplifiers;
	private final List<Function<?, ?>> inputSimplifiers;
	private final List<OpInfo> infoFocusers;
	private final List<Function<?, ?>> inputFocusers;
	private final OpInfo infoSimplifier;
	private final Function<?, ?> outputSimplifier;
	private final OpInfo refFocuser;
	private final Function<?, ?> outputFocuser;

	private final Optional<Computers.Arity1<?, ?>> copyOp;

	private final int numInputs;

	public SimplificationMetadata(SimplifiedOpRef ref, SimplifiedOpInfo info,
		OpEnvironment env)
	{
		this.ref = ref;
		this.info = info;

		this.refSimplifiers = ref.simplifierInfos();
		this.inputSimplifiers = inputSimplifiers(ref, env, this.refSimplifiers);

		this.infoFocusers = info.focuserInfos();
		this.inputFocusers = inputFocusers(info, env, this.infoFocusers);

		this.infoSimplifier = info.simplifierInfo();
		this.outputSimplifier = outputSimplifier(info, env, this.infoSimplifier);

		this.refFocuser = ref.focuserInfo();
		this.outputFocuser = outputFocuser(ref, env, this.refFocuser);

		this.copyOp = ref.copyOp();

		if (refSimplifiers.size() != infoFocusers.size())
			throw new IllegalArgumentException(
				"Invalid SimplificationMetadata for Op" + info +
					"\n - incompatible number of input simplifiers and focusers");
		numInputs = refSimplifiers.size();
	}

	private static List<Function<?, ?>> inputSimplifiers(SimplifiedOpRef ref,
		OpEnvironment env, List<OpInfo> refSimplifiers)
	{
		Type[] originalInputs = ref.srcRef().getArgs();
		Type[] simpleInputs = ref.getArgs();
		return SimplificationUtils.findArgMutators(env, refSimplifiers,
			originalInputs, simpleInputs);
	}

	private static List<Function<?, ?>> inputFocusers(SimplifiedOpInfo info,
		OpEnvironment env, List<OpInfo> infoFocusers)
	{
		Type[] unfocusedInputs = OpUtils.inputTypes(info.struct());
		Type[] focusedInputs = OpUtils.inputTypes(info.srcInfo().struct());
		return SimplificationUtils.findArgMutators(env, infoFocusers,
			unfocusedInputs, focusedInputs);
	}

	private static Function<?, ?> outputSimplifier(SimplifiedOpInfo info,
		OpEnvironment env, OpInfo infoSimplifier)
	{
		Type originalOutput = OpUtils.outputs(info.srcInfo().struct()).get(0)
			.getType();
		Type simpleOutput = OpUtils.outputs(info.struct()).get(0).getType();
		return SimplificationUtils.findArgMutator(env, infoSimplifier,
			originalOutput, simpleOutput);
	}

	private static Function<?, ?> outputFocuser(SimplifiedOpRef ref,
		OpEnvironment env, OpInfo refFocuser)
	{
		Type unfocusedOutput = ref.getOutType();
		Type focusedOutput = ref.srcRef().getOutType();
		return SimplificationUtils.findArgMutator(env, refFocuser, unfocusedOutput,
			focusedOutput);
	}

	public List<Function<?, ?>> inputSimpilfiers() {
		return inputSimplifiers;
	}

	public List<Function<?, ?>> inputFocusers() {
		return inputFocusers;
	}

	public Function<?, ?> outputSimplifier() {
		return outputSimplifier;
	}

	public Function<?, ?> outputFocuser() {
		return outputFocuser;
	}

	public Type[] originalInputs() {
		return ref.srcRef().getArgs();
	}

	public Type[] simpleInputs() {
		return ref.getArgs();
	}

	public Type[] unfocusedInputs() {
		return OpUtils.inputTypes(info.struct());
	}

	public Type[] focusedInputs() {
		return OpUtils.inputTypes(info.srcInfo().struct());
	}

	public Type originalOutput() {
		Struct s = info.srcInfo().struct();
		return OpUtils.outputs(s).get(0).getType();
	}

	public Type simpleOutput() {
		Struct s = info.struct();
		return OpUtils.outputs(s).get(0).getType();
	}

	public Type unfocusedOutput() {
		return ref.getOutType();
	}

	public Type focusedOutput() {
		return ref.srcRef().getOutType();
	}

	public int numInputs() {
		return numInputs;
	}

	public Class<?> opType() {
		return Types.raw(info.opType());
	}

	public boolean hasCopyOp() {
		return copyOp.isPresent();
	}

	public Computers.Arity1<?, ?> copyOp() {
		return copyOp.get();
	}

	/**
	 * The constructor to the simplified Op takes:
	 * <ol>
	 * <li>All input simplifiers
	 * <li>All input focusers
	 * <li>The output simplifier
	 * <li>The output focuser
	 * <li>The original Op
	 * </ol>
	 * This Op returns the {@link Class}es of those arguments <b>in that
	 * order</b>. Note that the {@link Class} of all arugments other than the
	 * {@code Op} is {@link Function}.
	 * 
	 * @return an array of classes describing the constructor arguments of the
	 *         simplified {@code Op}
	 */
	public Class<?>[] constructorClasses() {
		// there are 2*numInputs input mutators, 2 output mutators
		int numMutators = numInputs() * 2 + 2;
		// orignal Op plus a output copier if applicable
		int numOps = hasCopyOp() ? 2 : 1;
		Class<?>[] args = new Class<?>[numMutators + numOps];
		for (int i = 0; i < numMutators; i++)
			args[i] = Function.class;
		args[args.length - numOps] = opType();
		if(hasCopyOp())
			args[args.length - 1] = Computers.Arity1.class;
		return args;
	}

	/**
	 * The constructor to the simplified Op takes:
	 * <ol>
	 * <li>All input simplifiers
	 * <li>All input focusers
	 * <li>The output simplifier
	 * <li>The output focuser
	 * <li>The original Op
	 * </ol>
	 * This Op returns those arguments <b>in that order</b>.
	 * 
	 * @return an array of the constructor arguments of the simplified {@code Op}
	 */
	public Object[] constructorArgs(Object op) {
		List<Object> args = new ArrayList<>(inputSimplifiers);
		args.addAll(inputFocusers);
		args.add(outputSimplifier);
		args.add(outputFocuser);
		args.add(op);
		if(hasCopyOp())
			args.add(copyOp.get());

		return args.toArray();
	}

	/**
	 * Returns the index of the argument that is both the input and the output. <b>If there is no such argument (i.e. the Op produces a pure output), -1 is returned</b>
	 * 
	 * @return the index of the mutable argument.
	 */
	public int ioArgIndex() {
		List<Member<?>> inputs = OpUtils.inputs(info.struct());
		Optional<Member<?>> ioArg = inputs.stream().filter(m -> m.isInput() && m.isOutput()).findFirst();
		if(ioArg.isEmpty()) return -1;
		Member<?> ioMember = ioArg.get();
		return inputs.indexOf(ioMember);
	}

	public boolean pureOutput() {
		return ioArgIndex() == -1;
	}

}
