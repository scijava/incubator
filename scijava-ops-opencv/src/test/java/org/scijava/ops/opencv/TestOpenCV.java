/*-
 * #%L
 * SciJava Ops OpenCV: OpenCV configuration for ops
 * %%
 * Copyright (C) 2023 - 2024 SciJava developers.
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

package org.scijava.ops.opencv;

import net.imagej.opencv.MatToImgConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.junit.jupiter.api.Test;
import org.scijava.ops.api.OpBuilder;
import org.scijava.ops.api.OpEnvironment;

import java.io.File;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Ops generated by wrapping
 * {@link org.bytedeco.opencv.global.opencv_imgproc}.
 */
public class TestOpenCV {

	private static final String TEST_OP = "cv.GaussianBlur";
	private static final String TEST_OP_ALIAS = "filter.gauss";
	private static final String EXPECTED_HELP = //
			"cv.GaussianBlur:\n" //
					+ "\t- (in1, @CONTAINER container1, in2, in3) -> None\n"//
					+ "\t- (in1, @CONTAINER container1, in2, in3, in4, in5) -> None";

	private static final String EXPECTED_HELP_VERBOSE = //
			"cv.GaussianBlur:\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.GpuMat,org.bytedeco.opencv.opencv_core.GpuMat,org.bytedeco.opencv.opencv_core.Size,double)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.GpuMat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.GpuMat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.GpuMat,org.bytedeco.opencv.opencv_core.GpuMat,org.bytedeco.opencv.opencv_core.Size,double,double,int)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.GpuMat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.GpuMat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t\t> in4 : java.lang.Double\n" //
					+ "\t\t> in5 : java.lang.Integer\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.Mat,org.bytedeco.opencv.opencv_core.Mat,org.bytedeco.opencv.opencv_core.Size,double)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.Mat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.Mat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.Mat,org.bytedeco.opencv.opencv_core.Mat,org.bytedeco.opencv.opencv_core.Size,double,double,int)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.Mat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.Mat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t\t> in4 : java.lang.Double\n" //
					+ "\t\t> in5 : java.lang.Integer\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.UMat,org.bytedeco.opencv.opencv_core.UMat,org.bytedeco.opencv.opencv_core.Size,double)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.UMat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.UMat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.UMat,org.bytedeco.opencv.opencv_core.UMat,org.bytedeco.opencv.opencv_core.Size,double,double,int)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.UMat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.UMat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t\t> in4 : java.lang.Double\n" //
					+ "\t\t> in5 : java.lang.Integer";

	private static final String EXPECTED_USAGE = //
			"cv.GaussianBlur:\n" //
					+ "\t- (in1, @CONTAINER container1, in2, in3) -> None";

	private static final String EXPECTED_USAGE_VERBOSE = //
			"cv.GaussianBlur:\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.GpuMat,org.bytedeco.opencv.opencv_core.GpuMat,org.bytedeco.opencv.opencv_core.Size,double)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.GpuMat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.GpuMat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.Mat,org.bytedeco.opencv.opencv_core.Mat,org.bytedeco.opencv.opencv_core.Size,double)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.Mat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.Mat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double\n" //
					+ "\t- org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur(org.bytedeco.opencv.opencv_core.UMat,org.bytedeco.opencv.opencv_core.UMat,org.bytedeco.opencv.opencv_core.Size,double)\n" //
					+ "\t\t> in1 : org.bytedeco.opencv.opencv_core.UMat\n" //
					+ "\t\t> container1 : @CONTAINER org.bytedeco.opencv.opencv_core.UMat\n" //
					+ "\t\t> in2 : org.bytedeco.opencv.opencv_core.Size\n" //
					+ "\t\t> in3 : java.lang.Double";

	/**
	 * Verify we get all the expected ops for {@link #TEST_OP}
	 */
	@Test
	public void testDiscovery() {
		final OpEnvironment ops = OpEnvironment.build();
		assertEquals(EXPECTED_HELP, ops.help(TEST_OP));
		assertEquals(EXPECTED_HELP_VERBOSE, ops.helpVerbose(TEST_OP));
	}

	/**
	 * Verify that we can actually use a {@link #TEST_OP} op.
	 */
	@Test
	public void testUsage() {
		final OpEnvironment ops = OpEnvironment.build();
		Mat src = openFish();
		Mat opsFish = new Mat(src.rows(), src.cols(), src.type());
		Mat opencvFish = new Mat(src.rows(), src.cols(), src.type());

		int stDev = 100;
		Size size = new Size(5, 5);

		OpBuilder.Arity3_IV_OV<Mat, Size, Integer, Mat> builder =
				ops.ternary(TEST_OP).input(src, size, stDev).output(opsFish);

		// Check help strings from the builder perspective
		assertEquals(EXPECTED_USAGE, builder.help());
		assertEquals(EXPECTED_USAGE_VERBOSE, builder.helpVerbose());

		// Blur with ops
		builder.compute();

		// Blur directly with JavaCV
		GaussianBlur(src, opencvFish, size, stDev);

		// Verify the images are the same
		verifyMats(opencvFish, opsFish);

		// Repeat with alias
		opsFish = new Mat(src.rows(), src.cols(), src.type());
		ops.ternary(TEST_OP_ALIAS).input(src, size, stDev).output(opsFish)
				.compute();

		// Verify the images are the same once more
		verifyMats(opencvFish, opsFish);
	}

	/**
	 * Helper method to verify two {@link Mat}s are the same
	 */
	private void verifyMats(Mat expectedMat, Mat actualMat) {
		// Verify dimensions
		assertEquals(expectedMat.rows(), actualMat.rows());
		assertEquals(expectedMat.cols(), actualMat.cols());

		// Verify data
		byte[] opencvBytes = MatToImgConverter.toByteArray(expectedMat);
		byte[] opsFishBytes = MatToImgConverter.toByteArray(actualMat);

		assertArrayEquals(opencvBytes, opsFishBytes);
	}

	/**
	 * Helper method to open sample data via OpenCV
	 */
	private Mat openFish() {
		return imread(new File(
				getClass().getResource("/HappyFish.jpg").getFile()).getAbsolutePath());
	}
}
