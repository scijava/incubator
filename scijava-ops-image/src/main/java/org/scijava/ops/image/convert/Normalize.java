/*-
 * #%L
 * ImageJ2 software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2023 ImageJ2 developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.ops.image.convert;

import net.imglib2.type.numeric.RealType;
import org.scijava.function.Computers;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Normalize casting normalizes an input value within its range and converts it
 * to the equivalent value in a destination range.
 *
 * @param <I> - Source type
 * @param <O> - Destination type
 */
public class Normalize<I extends RealType<I>, O extends RealType<O>> {

	/**
	 * @input in
	 * @container out
	 * @implNote op names='cast.normalize'
	 */
	public final Computers.Arity1<I, O> computeMinMax = (I in, O out) -> {
		double inNorm;
		if (Double.isInfinite(in.getMaxValue() - in.getMinValue())) {
			BigDecimal bigIn = BigDecimal.valueOf(in.getRealDouble());
			BigDecimal minVal = BigDecimal.valueOf(in.getMinValue());
			BigDecimal maxVal = BigDecimal.valueOf(in.getMaxValue());
			inNorm = bigIn.subtract(minVal).divide(maxVal.subtract(minVal),
				MathContext.DECIMAL128).doubleValue();
		}
		else {
			inNorm = (in.getRealDouble() - in.getMinValue()) / (in.getMaxValue() - in
				.getMinValue());
		}
		if (Double.isInfinite(out.getMaxValue() - out.getMinValue())) {
			BigDecimal minVal = BigDecimal.valueOf(out.getMinValue());
			BigDecimal maxVal = BigDecimal.valueOf(out.getMaxValue());
			BigDecimal bigOutRange = maxVal.subtract(minVal);
			double result = bigOutRange.multiply(BigDecimal.valueOf(inNorm)).add(
				minVal).doubleValue();
			out.setReal(result);
		}
		else {
			double result = inNorm * (out.getMaxValue() - out.getMinValue());
			result += out.getMinValue();
			out.setReal(result);
		}
	};
}
