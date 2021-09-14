/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2016 Board of Regents of the University of
 * Wisconsin-Madison and University of Konstanz.
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

package net.imagej.ops2.threshold.localPhansalkar;

import java.util.Arrays;
import java.util.function.Function;

import net.imagej.ops2.filter.ApplyCenterAwareNeighborhoodBasedFilter;
import net.imagej.ops2.threshold.ApplyLocalThresholdIntegral;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.neighborhood.RectangleNeighborhood;
import net.imglib2.algorithm.neighborhood.RectangleShape;
import net.imglib2.algorithm.neighborhood.Shape;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.view.composite.Composite;

import org.scijava.Priority;
import org.scijava.function.Computers;
import org.scijava.ops.spi.Op;
import org.scijava.ops.spi.OpDependency;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "threshold.localPhansalkar",
	priority = Priority.LOW)
public class LocalPhansalkarThreshold<T extends RealType<T>> extends
	ApplyLocalThresholdIntegral<T, DoubleType> implements
	Computers.Arity5<RandomAccessibleInterval<T>, Shape, Double, Double, OutOfBoundsFactory<T, RandomAccessibleInterval<T>>, //
			RandomAccessibleInterval<BitType>> {

	private static final int INTEGRAL_IMAGE_ORDER_1 = 1;
	private static final int INTEGRAL_IMAGE_ORDER_2 = 2;

	@OpDependency(name = "threshold.localPhansalkar")
	private Computers.Arity4<Iterable<T>, T, Double, Double, BitType> computeThresholdNonIntegralOp;

	@OpDependency(name = "threshold.localPhansalkar")
	private Computers.Arity4<RectangleNeighborhood<? extends Composite<DoubleType>>, T, Double, Double, BitType> computeThresholdIntegralOp;

	/**
	 * TODO
	 *
	 * @param input
	 * @param inputneighborhoodshape
	 * @param k (required = false)
	 * @Param r (required = false)
	 * @Param outOfBoundsFactory (required = false)
	 * @param output
	 */
	@Override
	public void compute(final RandomAccessibleInterval<T> input,
		final Shape inputNeighborhoodShape, final Double k, final Double r,
		final OutOfBoundsFactory<T, RandomAccessibleInterval<T>> outOfBoundsFactory,
		final RandomAccessibleInterval<BitType> output)
	{
		// Use integral images for sufficiently large windows.
		RectangleShape rShape = inputNeighborhoodShape instanceof RectangleShape
			? (RectangleShape) inputNeighborhoodShape : null;
		if (rShape != null && rShape.getSpan() > 2 && !rShape.isSkippingCenter()) {
			// NB: under these conditions, the RectangleShape will produce
			// RectangleNeighborhoods (which is needed to perform the computations via
			// an IntegralImg).
			computeIntegral(input, rShape, k, r,
				outOfBoundsFactory, getIntegralImageOp(INTEGRAL_IMAGE_ORDER_1),
				getIntegralImageOp(INTEGRAL_IMAGE_ORDER_2), computeThresholdIntegralOp,
				output);
		}
		else {
			computeNonIntegral(input, inputNeighborhoodShape, k, r,
				outOfBoundsFactory, computeThresholdNonIntegralOp, output);
		}
	}

	public void computeNonIntegral(
		final RandomAccessibleInterval<T> input, final Shape inputNeighborhoodShape,
		final Double k, final Double r,
		final OutOfBoundsFactory<T, RandomAccessibleInterval<T>> outOfBoundsFactory,
		final Computers.Arity4<Iterable<T>, T, Double, Double, BitType> computeThresholdOp,
		final RandomAccessibleInterval<BitType> output)
	{
		final Computers.Arity2<Iterable<T>, T, BitType> parametrizedComputeThresholdOp = //
			(i1, i2, o) -> computeThresholdOp.compute(i1, i2, k, r, o);
		ApplyCenterAwareNeighborhoodBasedFilter.compute(input,
			inputNeighborhoodShape, outOfBoundsFactory,
			parametrizedComputeThresholdOp, output);
	}

	public void computeIntegral(
		final RandomAccessibleInterval<T> input,
		final RectangleShape inputNeighborhoodShape, final Double k, final Double r,
		final OutOfBoundsFactory<T, RandomAccessibleInterval<T>> outOfBoundsFactory,
		final Function<RandomAccessibleInterval<T>, RandomAccessibleInterval<DoubleType>> integralImageOp,
		final Function<RandomAccessibleInterval<T>, RandomAccessibleInterval<DoubleType>> squareIntegralImageOp,
		final Computers.Arity4<RectangleNeighborhood<? extends Composite<DoubleType>>, T, Double, Double, BitType> computeThresholdOp,
		final RandomAccessibleInterval<BitType> output)
	{
		final Computers.Arity2<RectangleNeighborhood<? extends Composite<DoubleType>>, T, BitType> parametrizedComputeThresholdOp = //
			(i1, i2, o) -> computeThresholdOp.compute(i1, i2, k, r, o);
		compute(input, inputNeighborhoodShape,
			outOfBoundsFactory, Arrays.asList(integralImageOp, squareIntegralImageOp),
			parametrizedComputeThresholdOp, output);
	}

}
