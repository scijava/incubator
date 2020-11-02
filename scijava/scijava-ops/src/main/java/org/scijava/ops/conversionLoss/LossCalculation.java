
package org.scijava.ops.conversionLoss;

import org.scijava.types.Nil;

/**
 * Container storing the result of converion loss between types {@code T} and
 * {@code R}. Conversion loss is stored for <b>both directions of
 * conversion</b>.
 * 
 * @author Gabriel Selzer
 * @param <T> - the first type
 * @param <R> - the second type
 */
public class LossCalculation<T, R> {

	// TODO: maybe these should be Nils?
	final Nil<? extends T> fromType;
	final Nil<? extends R> toType;
	final double fLoss;
	final double rLoss;

	public LossCalculation(Nil<? extends T> from, Nil<? extends R> to, double fLoss, double rLoss) {
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
