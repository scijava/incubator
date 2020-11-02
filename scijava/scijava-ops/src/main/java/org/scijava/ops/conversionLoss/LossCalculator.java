
package org.scijava.ops.conversionLoss;

import org.scijava.plugin.SciJavaPlugin;
import org.scijava.types.Nil;

/**
 * Calculates the conversion loss going from:
 * <ol>
 * <li>{@code T} to {@code R} (Forward conversion)
 * <li>{@code R} to {@code T} (Reverse conversion)
 * </ol>
 */
public abstract class LossCalculator<T, R> implements SciJavaPlugin {

	// TODO: can we delete this?
//	public LossCalculation<T, R> calculateLoss(Nil<? extends T> t, Nil<? extends R> r) {
//		double fLoss = calculateForward(t);
//		double rLoss = calculateReverse(r);
//		return new LossCalculation<>(t, r, fLoss, rLoss);
//	}

	public abstract double calculateForward();

	public abstract double calculateReverse();
	
	public abstract Nil<T> fromType();
	
	public abstract Nil<R> toType();
	
	@Override
	public String toString() {
		return fromType().getType() + " -> " + toType().getType() + " LossCalculator";
	}

}
