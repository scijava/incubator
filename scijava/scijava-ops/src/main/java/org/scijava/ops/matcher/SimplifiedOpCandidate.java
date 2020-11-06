
package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import org.scijava.Priority;
import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.conversionLoss.LossReporter;
import org.scijava.ops.core.Op;
import org.scijava.struct.ItemIO;
import org.scijava.types.Nil;
import org.scijava.types.Types;

public class SimplifiedOpCandidate extends OpCandidate {

	private SimplifiedOpInfo info;
	private SimplifiedOpRef ref;

	final Type[] originalRefTypes;
	final Type[] originalInfoTypes;

	public SimplifiedOpCandidate(OpEnvironment env, Logger log,
		SimplifiedOpRef ref, SimplifiedOpInfo info,
		Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		super(env, log, ref, info, typeVarAssigns);
		this.info = info;
		this.ref = ref;

		originalRefTypes = ref.simplifiers.stream().map(s -> s.focusedType())
			.toArray(Type[]::new);
		originalInfoTypes = info.simplifiers.stream().map(s -> s.focusedType())
			.toArray(Type[]::new);

		if (originalRefTypes.length != originalInfoTypes.length) {
			throw new IllegalStateException(
				"The OpRef and OpInfo do not use the same amount of Simplifiers!");
		}
	}

	public SimplifiedOpCandidate(OpEnvironment env, Logger log, OpRef ref,
		SimplifiedOpInfo info, Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		this(env, log, SimplifiedOpRef.identitySimplification(ref), info,
			typeVarAssigns);
	}

	/**
	 * We define the priority of any {@link SimplifiedOpCandidate} as the sum of
	 * the following:
	 * <ul>
	 * <li>{@link Priority#VERY_LOW} to ensure that simplifications are not chosen
	 * over a direct match.
	 * <li>{@link OpInfo#priority} to ensure that a simplification of a
	 * higher-priority Op wins out over a simplification of a lower-priority Op,
	 * all else equal.
	 * <li>a penalty defined as a lossiness heuristic of this simplification. This
	 * penalty is the sum of:
	 * <ul>
	 * <li>the loss undertaken by converting each of the Op's inputs (as defined
	 * by an {@link ItemIO#INPUT} or {@link ItemIO#BOTH} annotation) from the ref
	 * type to the info type
	 * <li>the loss undertaken by converting each of the Op's outputs (as defined
	 * by an {@link ItemIO#OUTPUT} or {@link ItemIO#BOTH} annotation) from the
	 * info type to the ref type
	 * </ul>
	 * </ul>
	 */
	@Override
	public double priority() {
		// BASE PRIORITY
		double base = Priority.VERY_LOW;

		// ORIGINAL PRIORITY
		double originalPriority = info.priority();

		// PENALTY
		double penalty = 0;

		// TODO: will these ever be incorrectly ordered?
		Nil<?>[] refInTypes = ref.simplifiers.stream().map(s -> Nil.of(s
			.focusedType())).toArray(Nil[]::new);
		Nil<?>[] infoInTypes = info.simplifiers.stream().map(s -> Nil.of(s
			.focusedType())).toArray(Nil[]::new);

		for (int i = 0; i < refInTypes.length; i++) {
			penalty += determineLoss(refInTypes[i], infoInTypes[i]);
		}

		// TODO: is this right?
		Nil<?> infoOutType = Nil.of(info.output().getType());
		Nil<?> refOutType = Nil.of(ref.getOutType());

		penalty += determineLoss(refOutType, infoOutType);

		// PRIORITY = BASE + ORIGINAL - PENALTY
		return base + originalPriority - penalty;
	}

	/**
	 * Calls a {@code lossReporter} {@link Op} to determine the <b>worst-case</b>
	 * loss from a {@code T} to a {@code R}.
	 * 
	 * @param <T> -the generic type we are converting from.
	 * @param <R> - generic type we are converting to.
	 * @param from - a {@link Nil} describing the type we are converting from
	 * @param to - a {@link Nil} describing the type we are converting to
	 * @return - a {@code double} describing the magnitude of the <worst-case>
	 *         loss in a conversion from an instance of {@code T} to an instance
	 *         of {@code R}
	 */
	private <T, R> double determineLoss(Nil<T> from, Nil<R> to) {
		Type specialType = Types.parameterize(LossReporter.class, new Type[] { from
			.getType(), to.getType() });
		@SuppressWarnings("unchecked")
		Nil<LossReporter<T, R>> specialTypeNil = (Nil<LossReporter<T, R>>) Nil.of(
			specialType);
		LossReporter<T, R> op = env().op("lossReporter", specialTypeNil, new Nil[] {
			from, to }, Nil.of(Double.class));
		return op.apply(from, to);
	}

}
