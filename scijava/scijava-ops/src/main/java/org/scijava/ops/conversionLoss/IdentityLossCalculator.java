
package org.scijava.ops.conversionLoss;

import org.scijava.types.Nil;

/**
 * A {@link LossCalculator} used when a type is not simplified.
 * 
 * @author Gabriel Selzer
 * @param <T> - the type that is not being simplified.
 */
public class IdentityLossCalculator<T> extends LossCalculator<T, T> {

	final Nil<T> type;

	public IdentityLossCalculator(Nil<T> type) {
		this.type = type;
	}

	@Override
	public double calculateForward() {
		return 0;
	}

	@Override
	public double calculateReverse() {
		return 0;
	}

	@Override
	public Nil<T> fromType() {
		return type;
	}

	@Override
	public Nil<T> toType() {
		return type;
	}

}
