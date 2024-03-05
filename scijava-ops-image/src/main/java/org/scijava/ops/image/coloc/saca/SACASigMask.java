/*-
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2024 ImageJ developers.
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

package org.scijava.ops.image.coloc.saca;

import net.imglib2.img.Img;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.logic.BitType;
import net.imglib2.util.Intervals;
import net.imglib2.util.Util;

import org.scijava.function.Computers;
import org.scijava.ops.spi.Nullable;
import org.scijava.ops.spi.OpDependency;

/**
 * Spatially Adaptive Colocalization Analysis (SACA) Adapted from Shulei's
 * original Java code for AdaptiveSmoothedKendallTau from his RKColocal R
 * package.
 * (https://github.com/lakerwsl/RKColocal/blob/master/RKColocal_0.0.1.0000.tar.gz)
 *
 * @author Shulei Wang
 * @author Curtis Rueden
 * @author Ellen TA Dobson
 * @author Edward Evans
 * @implNote op names='coloc.saca.sigMask', priority='100.'
 */

public class SACASigMask implements
	Computers.Arity6<RandomAccessibleInterval<DoubleType>, Double, Double, Double, Boolean, Boolean, RandomAccessibleInterval<BitType>>
{

	@OpDependency(name = "threshold.apply")
	private Computers.Arity2<RandomAccessibleInterval<DoubleType>, DoubleType, RandomAccessibleInterval<BitType>> thresOp;

	/**
	 * Spatially Adaptive Colocalization Analysis (SACA) Significant pixel mask.
	 *
	 * @param heatmap input heatmap returned from 'coloc.saca.heatmapZScore'
	 * @param alpha significance cuttoff, type 1 error (default=0.05)
	 * @param mean mean value (default=0)
	 * @param sd standard div (default=1)
	 * @param lowerTail lower tail (default=false)
	 * @param logP log P (default=false)
	 * @param result BitType significant pixel mask
	 */

	@Override
	public void compute(final RandomAccessibleInterval<DoubleType> heatmap,
		@Nullable Double alpha, @Nullable Double mean, @Nullable Double sd,
		@Nullable Boolean lowerTail, @Nullable Boolean logP,
		RandomAccessibleInterval<BitType> result)
	{
		// set alpha, mean, sd, lowerTail and logP if null
		if (alpha == null) alpha = 0.05;
		if (mean == null) mean = 0.0;
		if (sd == null) sd = 1.0;
		if (lowerTail == null) lowerTail = false;
		if (logP == null) logP = false;

		// compute QNorm
		double thres = QNorm.compute(alpha / Intervals.numElements(heatmap), mean,
			sd, lowerTail, logP);

		// apply QNorm thres and create significant pixel mask
		thresOp.compute(heatmap, new DoubleType(thres), result);

	}
}
