/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2018 ImageJ developers.
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
package net.imagej.ops2.features.zernike;

import static org.junit.Assert.assertEquals;

import net.imagej.ops2.features.AbstractFeatureTest;
import net.imglib2.type.numeric.real.DoubleType;

import org.junit.Test;
import org.scijava.types.Nil;

/**
 * 
 * Test {@Link ZernikeFeature}
 * 
 * @author Andreas Graumann (University of Konstanz)
 *
 */
public class ZernikeFeatureTest extends AbstractFeatureTest {

	private static final double EPSILON = 1e-12;

	@Test
	public void testPhaseFeature() {

		assertEquals("features.zernike.phase", 179.92297037263532,
				 op("features.zernike.phase").input(ellipse, 4, 2).outType(new Nil<DoubleType>() {}).apply().get(), EPSILON);
		assertEquals("features.zernike.phase", 0.0802239034925816,
				 op("features.zernike.phase").input(rotatedEllipse, 4, 2).outType(new Nil<DoubleType>() {}).apply().get(), EPSILON);
	}

	@Test
	public void testMagnitudeFeature() {

		double v1 = op("features.zernike.magnitude").input(ellipse, 4, 2).outType(new Nil<DoubleType>() {})
				.apply().get();
		double v2 = op("features.zernike.magnitude").input(rotatedEllipse, 4, 2).outType(new Nil<DoubleType>() {})
				.apply().get();

		assertEquals("features.zernike.magnitude", 0.10985876611295191, v1, EPSILON);

		// magnitude is the same after rotating the image
		assertEquals("features.zernike.magnitude", v1, v2, 1e-3);
	}

}