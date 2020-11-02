
package org.scijava.ops.conversionLoss;

import org.scijava.types.Nil;

/**
 * We should avoid writing multiple {@link LossCalculator}s for the same set of
 * types (for example, we do not want to write both a
 * {@code LossCalculator<Double, Long>} and a
 * {@code LossCalculator<Long, Double>}). To write both would be nothing more
 * than code duplication. However, it is necessary to be able to fulfill both
 * requests. Given a {@code LossCalculator} that handles one direction,
 * {@link ReverseLossCalculator} can handle the other.
 * 
 * @author Gabriel Selzer
 * @param <T> - the <b>reverse</b> type of the original {@code LossCalculator}
 * @param <R> - the <b>forward</b> type of the original {@code LossCalculator}
 */
public class ReverseLossCalculator<T, R> extends LossCalculator<T, R> {

	LossCalculator<R, T> original;

	public ReverseLossCalculator(LossCalculator<R, T> original) {
		this.original = original;
	}

	@Override
	public double calculateForward() {
		return original.calculateReverse();
	}

	@Override
	public double calculateReverse() {
		return original.calculateForward();
	}

	@Override
	public Nil<T> fromType() {
		return original.toType();
	}

	@Override
	public Nil<R> toType() {
		return original.fromType();
	}

}
