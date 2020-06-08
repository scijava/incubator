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

package net.imagej.ops2.filter.vesselness;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.scijava.script.ScriptService;

import net.imagej.ops2.AbstractOpTest;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.Views;

/**
 * Tests the Frangi Vesselness operation.
 * 
 * @author Gabe Selzer
 */
public class FrangiVesselnessTest extends AbstractOpTest {

	@Test
	public void regressionTest() throws Exception {

		// load in input image and expected output image.
		Img<DoubleType> inputImg = ArrayImgs.doubles(256, 256);
		op("image.equation")
				.input("Math.tan(0.3*p[0]) + Math.tan(0.1*p[1])", context.getService(ScriptService.class))
				.output(inputImg).compute();
		Img<FloatType> expectedOutput = ((Img<FloatType>) openFloatImg("Result.tif"));

		// create ouput image
		long[] dims = new long[inputImg.numDimensions()];
		inputImg.dimensions(dims);
		Img<FloatType> actualOutput = ArrayImgs.floats(dims);

		// scale over which the filter operates (sensitivity)
		int scale = 1;

		// physical spacing between data points (1,1 since I got it from the
		// computer)
		double[] spacing = { 1, 1 };

		// run the op
		op("filter.frangiVesselness").input(inputImg, spacing, scale).output(actualOutput).compute();

		// compare the output image data to that stored in the file.
		Cursor<FloatType> cursor = Views.iterable(actualOutput).localizingCursor();
		RandomAccess<FloatType> actualRA = actualOutput.randomAccess();
		RandomAccess<FloatType> expectedRA = expectedOutput.randomAccess();

		while (cursor.hasNext()) {
			cursor.fwd();
			actualRA.setPosition(cursor);
			expectedRA.setPosition(cursor);
			assertEquals(expectedRA.get().get(), actualRA.get().get(), 0);
		}
	}

}
