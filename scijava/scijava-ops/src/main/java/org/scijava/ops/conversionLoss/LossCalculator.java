
package org.scijava.ops.conversionLoss;

import org.scijava.plugin.SciJavaPlugin;

/**
 * Calculates the conversion loss going from:
 * <ol>
 * <li>{@code T} to {@code R} (Forward conversion)
 * <li>{@code R} to {@code T} (Reverse conversion)
 * </ol>
 */
public abstract class LossCalculator<T, R> implements SciJavaPlugin {

	public LossCalculation<T, R> calculateLoss(T t, R r) {
		double fLoss = calculateForward(t);
		double rLoss = calculateReverse(r);
		return new LossCalculation<>(t, r, fLoss, rLoss);
	}

	abstract double calculateForward(T t);

	abstract double calculateReverse(R r);

}
