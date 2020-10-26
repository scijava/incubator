package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.scijava.Priority;
import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;

public class SimplifiedOpCandidate extends OpCandidate {
	
	private SimplifiedOpInfo simplifiedInfo;
	private SimplifiedOpRef simplifiedRef;

	public SimplifiedOpCandidate(OpEnvironment env, Logger log, SimplifiedOpRef ref,
		SimplifiedOpInfo info, Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		super(env, log, ref, info, typeVarAssigns);
		this.simplifiedInfo = info;
		this.simplifiedRef = ref;
		List<LossCalculator<?, ?>> list = getLossCalculators(1l, 1.0);
		LossCalculator<Long, Double> calc = (LossCalculator<Long, Double>) list.get(0);
		LossCalculation<Long, Double> c = calc.calculateLoss(1l, 1.0);
	}
	
	@Override
	public double priority() {
		// TODO
		double base = Priority.VERY_LOW + simplifiedInfo.priority();
		
		// Penalty
		double penalty = 0; // TODO
		
		return base + penalty;
	}
	
	public abstract class LossCalculator<T, R> {
		
		/**
		 * Calculates the conversion loss going from:
		 * <ol>
		 * <li> {@code T} to {@code R} (Forward conversion)
		 * <li> {@code R} to {@code T} (Reverse conversion)
		 * </ol>
		 * 
		 * @param t - the first type
		 * @param r - the second type
		 * @return a {@link LossCalculation}
		 */
		public LossCalculation<T, R> calculateLoss(T t, R r) { 
			double fLoss = calculateForward(t);
			double rLoss = calculateReverse(r);
			return new LossCalculation<>(t, r, fLoss, rLoss);
		}
		
		abstract double calculateForward(T t);
		
		abstract double calculateReverse(R r);
		
	}
	
	/**
	 * Container storing the result of converion loss between types {@code T} and
	 * {@code R}. Conversion loss is stored for <b>both directions of
	 * conversion</b>.
	 * 
	 * @author Gabriel Selzer
	 * @param <T> - the first type
	 * @param <R> - the second type
	 */
	public static class LossCalculation<T, R>{
		// TODO: maybe these should be Nils?
		final T fromType;
		final R toType;
		final double fLoss;
		final double rLoss;

		public LossCalculation(T from, R to, double fLoss, double rLoss) {
			this.fromType = from;
			this.toType = to;
			this.fLoss = fLoss;
			this.rLoss = rLoss;
		}
		
		public double forwardLoss() {
			return fLoss;
		}

		public double reverseLoss() {
			return rLoss;
		}

		@Override
		public String toString() {
			return "Forward Loss: " + fLoss + ", Reverse Loss: " + rLoss;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private List<LossCalculator<?, ?>> getLossCalculators(Object t, Object u) {
		if (t instanceof Long && u instanceof Double) {
			List<LossCalculator<?, ?>> list = new ArrayList<>();
			list.add(new LossCalculator<Long, Double>() {

				@Override
				public double calculateForward(Long t) {
					long maxValue = Long.MAX_VALUE - 1;
					double converted = maxValue;
					return Math.abs(maxValue - (long)converted);
				}

				@Override
				public double calculateReverse(Double r) {
					double maxValue = Double.MAX_VALUE;
					long converted = (long) maxValue;
					return maxValue - converted;
				}
				
			});
			return list;
		}
		return Collections.EMPTY_LIST;
	}

}
