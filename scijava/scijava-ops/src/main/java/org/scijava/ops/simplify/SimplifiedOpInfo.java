package org.scijava.ops.simplify;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.scijava.Priority;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.conversionLoss.LossReporter;
import org.scijava.ops.core.Op;
import org.scijava.ops.matcher.OpMatchingException;
import org.scijava.param.Optional;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.types.Nil;
import org.scijava.types.Types;
import org.scijava.util.MiscUtils;


public class SimplifiedOpInfo implements OpInfo {

	private final OpInfo srcInfo;
	private final SimplificationMetadata metadata;
	private final Type opType;
	private final double priority;

	private Struct struct;
	private ValidityException validityException;

	public SimplifiedOpInfo(OpInfo info, OpEnvironment env, SimplificationMetadata metadata) {
		this.srcInfo = info;
		this.metadata = metadata;
		Type[] inputs = metadata.originalInputs();
		Type output = metadata.focusedOutput();
		this.opType = SimplificationUtils.retypeOpType(info.opType(), inputs, output);
		try {
			this.struct = ParameterStructs.structOf(info, opType);
		}
		catch (ValidityException exc) {
			validityException = exc;
		}

		this.priority = calculatePriority(info, metadata, env);
	}

	public OpInfo srcInfo() {
		return srcInfo;
	}

	@Override
	public Type opType() {
		return opType;
	}

	@Override
	public Struct struct() {
		return struct;
	}

	@Override
	public double priority() {
		return priority;
	}

	/**
	 * We define the priority of any {@link SimplifiedOpInfo} as the sum of the
	 * following:
	 * <ul>
	 * <li>{@link Priority#VERY_LOW} to ensure that simplifications are not chosen
	 * over a direct match.
	 * <li>The {@link OpInfo#priority} of the source info to ensure that a
	 * simplification of a higher-priority Op wins out over a simplification of a
	 * lower-priority Op, all else equal.
	 * <li>a penalty defined as a lossiness heuristic of this simplification. This
	 * penalty is the sum of:
	 * <ul>
	 * <li>the loss undertaken by converting each of the Op's inputs from the ref
	 * type to the info type
	 * <li>the loss undertaken by converting each of the Op's outputs from the info
	 * type to the ref type
	 * </ul>
	 * </ul>
	 */
	private static double calculatePriority(OpInfo srcInfo, SimplificationMetadata metadata, OpEnvironment env) {
		// BASE PRIORITY
		double base = Priority.VERY_LOW;

		// ORIGINAL PRIORITY
		double originalPriority = srcInfo.priority();

		// PENALTY
		double penalty = 0;

		Type[] originalInputs = metadata.originalInputs();
		Type[] opInputs = metadata.focusedInputs();
		for (int i = 0; i < metadata.numInputs(); i++) {
			penalty += determineLoss(env, Nil.of(originalInputs[i]), Nil.of(opInputs[i]));
		}

		// TODO: only calculate the loss once
		Type opOutput = metadata.focusedOutput();
		Type originalOutput = metadata.originalOutput();
		penalty += determineLoss(env, Nil.of(opOutput), Nil.of(originalOutput));

		// PRIORITY = BASE + ORIGINAL - PENALTY
		return base + originalPriority - penalty;
	}

	/**
	 * Calls a {@code lossReporter} {@link Op} to determine the <b>worst-case</b>
	 * loss from a {@code T} to a {@code R}. If no {@code lossReporter} exists for
	 * such a conversion, we assume infinite loss.
	 * 
	 * @param <T> -the generic type we are converting from.
	 * @param <R> - generic type we are converting to.
	 * @param from - a {@link Nil} describing the type we are converting from
	 * @param to - a {@link Nil} describing the type we are converting to
	 * @return - a {@code double} describing the magnitude of the <worst-case>
	 *         loss in a conversion from an instance of {@code T} to an instance
	 *         of {@code R}
	 */
	private static <T, R> double determineLoss(OpEnvironment env, Nil<T> from, Nil<R> to) {
		Type specialType = Types.parameterize(LossReporter.class, new Type[] { from
			.getType(), to.getType() });
		@SuppressWarnings("unchecked")
		Nil<LossReporter<T, R>> specialTypeNil = (Nil<LossReporter<T, R>>) Nil.of(
			specialType);
		try {
			Type nilFromType = Types.parameterize(Nil.class, new Type[] {from.getType()});
			Type nilToType = Types.parameterize(Nil.class, new Type[] {to.getType()});
			LossReporter<T, R> op = env.op("lossReporter", specialTypeNil, new Nil[] {
				Nil.of(nilFromType), Nil.of(nilToType) }, Nil.of(Double.class));
			return op.apply(from, to);
		} catch(IllegalArgumentException e) {
			if (e.getCause() instanceof OpMatchingException)
				return Double.POSITIVE_INFINITY;
			throw e;
		}
	}

	@Override
	public String implementationName() {
		return srcInfo.implementationName();
	}

	@Override
	public boolean isValid() {
		return srcInfo.isValid();
	}

	@Override
	public ValidityException getValidityException() {
		return validityException;
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
	 */
	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies)
	{
		final Object op = srcInfo.createOpInstance(dependencies).object();
		try {
			Object simpleOp = SimplificationUtils.javassistOp(op, metadata);
			return struct().createInstance(simpleOp);
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

	@Override
	public boolean hasOptionalParameters() {
		return false;
	}

	/**
	 * NB Since the simplified Op is autogenerated, we can rest assured that there
	 * are no {@link Optional} parameters
	 */
	@Override
	public Parameter[] optionalParameters() {
		return new Parameter[] {};
	}

}
