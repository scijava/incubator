package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scijava.Priority;
import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.conversionLoss.IdentityLossCalculator;
import org.scijava.ops.conversionLoss.LossCalculator;
import org.scijava.ops.conversionLoss.PrimitiveLossCalculators.IntegerDoubleLoss;
import org.scijava.ops.conversionLoss.PrimitiveLossCalculators.LongDoubleLoss;
import org.scijava.ops.conversionLoss.PrimitiveLossCalculators.LongIntegerLoss;
import org.scijava.ops.conversionLoss.ReverseLossCalculator;
import org.scijava.struct.ItemIO;
import org.scijava.types.Nil;
import org.scijava.types.Types;

public class SimplifiedOpCandidate extends OpCandidate {
	
	private SimplifiedOpInfo info;
	private SimplifiedOpRef ref;

	final Type[] originalRefTypes;
	final Type[] originalInfoTypes;

	public SimplifiedOpCandidate(OpEnvironment env, Logger log, SimplifiedOpRef ref,
		SimplifiedOpInfo info, Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		super(env, log, ref, info, typeVarAssigns);
		this.info = info;
		this.ref = ref;

		originalRefTypes = ref.simplifiers.stream().map(s -> s.focusedType()).toArray(Type[]::new);
		originalInfoTypes = info.simplifiers.stream().map(s -> s.focusedType()).toArray(Type[]::new);

		if(originalRefTypes.length != originalInfoTypes.length) {
			throw new IllegalStateException("The OpRef and OpInfo do not use the same amount of Simplifiers!");
		}
	}
	
	public SimplifiedOpCandidate(OpEnvironment env, Logger log, OpRef ref,
		SimplifiedOpInfo info, Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		this(env, log, SimplifiedOpRef.identitySimplification(ref), info, typeVarAssigns);
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
		Nil<?>[] refInTypes = ref.simplifiers.stream().map(s -> Nil.of(s.focusedType())).toArray(Nil[]::new);
		Nil<?>[] infoInTypes = info.simplifiers.stream().map(s -> Nil.of(s.focusedType())).toArray(Nil[]::new);
		
		for(int i = 0; i < refInTypes.length; i++) {
			penalty += getLossCalculator(refInTypes[i], infoInTypes[i]).calculateForward();
		}
		
		Nil<?> infoOutType = Nil.of(info.output().getType());
		Nil<?> refOutType = Nil.of(ref.getOutType());
		
		penalty += getLossCalculator(refOutType, infoOutType).calculateReverse();

		// PRIORITY = BASE + ORIGINAL - PENALTY
		return base + originalPriority - penalty;
	}	

	
	private <T, R> LossCalculator<T, R> getLossCalculator(Nil<T> from, Nil<R> to) {
		List<LossCalculator<T, R>> list = foo(from, to); // should ask the pluginIndex for this list
		if (list.size() > 1) {
			// TODO: Consider the correctness of this exception
			throw new IllegalStateException(
				"Multiple LossCalculators are available for types " + from + " and " +
					to + ": \n" + list);
		}
		if (list.size() == 0) {
			throw new IllegalArgumentException(
				"No LossCalculators are available for types " + from + " and " + to +
					".");
		}
		return list.get(0);
	}
	
	// TODO: consider correctness
	@SuppressWarnings("unchecked")
	private <T, R> List<LossCalculator<T, R>> foo(Nil<T> from, Nil<R> to) {
		List<LossCalculator<?, ?>> known = Arrays.asList(new LongDoubleLoss(), new LongIntegerLoss(), new IntegerDoubleLoss());
		List<LossCalculator<?, ?>> list = new ArrayList<>();
		for(LossCalculator<?, ?> lc : known) {
			list.add(lc);
			list.add(getReverseCalculator(lc));
		}
		list.add(new IdentityLossCalculator<>(from));
		return list.parallelStream()//
		.filter(lc -> Types.isAssignable(from.getType(), lc.fromType().getType())) //
		.filter(lc -> Types.isAssignable(lc.toType().getType(), to.getType())) //
		.map(lc -> (LossCalculator<T, R>) lc) //
		.collect(Collectors.toList());
		
	}
	
	private <T, R> ReverseLossCalculator<R, T> getReverseCalculator(LossCalculator<T, R> lc) {
		return new ReverseLossCalculator<>(lc);
	}

}
