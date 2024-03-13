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

import net.imglib2.FinalDimensions;
import net.imglib2.IterableInterval;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.*;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;
import org.junit.jupiter.api.Test;
import org.scijava.ops.image.AbstractOpTest;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * tests for {@link Normalize}
 */
public class TestNormalize extends AbstractOpTest {

	private static final String OP_NAME = "cast.normalize";
	private static final double EPSILON = 0.000000001;

	@Test
	public void testBitToUint4() {
		testUpscaleSuite(new BitType(), new Unsigned4BitType());
	}

	@Test
	public void testUint2ToUint4() {
		testUpscaleSuite(new Unsigned2BitType(), new Unsigned4BitType());
	}

	@Test
	public void testUint4ToUint4() {
		testUint4Suite(new Unsigned4BitType());
	}

	@Test
	public void testInt8ToUint4() {
		testUint4Suite(new ByteType());
	}

	@Test
	public void testUint8ToUint4() {
		testUint4Suite(new UnsignedByteType());
	}

	@Test
	public void testUint12ToUint4() {
		testUint4Suite(new Unsigned12BitType());
	}

	@Test
	public void testInt16ToUint4() {
		testUint4Suite(new ShortType());
	}

	@Test
	public void testUint16ToUint4() {
		testUint4Suite(new UnsignedShortType());
	}

	@Test
	public void testInt32ToUint4() {
		testUint4Suite(new IntType());
	}

	@Test
	public void testUint32ToUint4() {
		testUint4Suite(new UnsignedIntType());
	}

	@Test
	public void testInt64ToUint4() {
		testUint4Suite(new LongType());
	}

	@Test
	public void testUint64ToUint4() {
		testUint4Suite(new UnsignedLongType());
	}

	@Test
	public void testUint128ToUint4() {
		testUint4Suite(new Unsigned128BitType());
	}

	@Test
	public void testFloat32ToUint4() {
		testUint4Suite(new FloatType());
	}

	@Test
	public void testFloat64ToUint4() {
		testUint4Suite(new DoubleType());
	}

	@Test
	public void testFloat32ToInt32() {
		testMin(new FloatType(), new IntType());
		testMax(new FloatType(), new IntType());
		testNormalize(new FloatType(), new IntType(), 0.5, -1);
	}

	@Test
	public void testUInt4ToFloat32() {
		testUpscaleSuite(new Unsigned4BitType(), new FloatType());
	}

	private <I extends RealType<I>> void testUint4Suite(I inType) {
		testDownscaleSuite(inType, new Unsigned4BitType());
	}

	@Test
	public void testUint16ToUint8Image() {
		final UnsignedShortType b = new UnsignedShortType(480);
		// Create the input image
		final var img = ops.binary("create.img") //
			.input(new FinalDimensions(2, 2), b) //
			.apply();
		ops.unary("image.fill").input(b).output(img).compute();
		final var out = ops.binary("create.img") //
			.input(new FinalDimensions(2, 2), new UnsignedByteType()) //
			.apply();
		// Create the converted image
		ops.unary(OP_NAME).input(img).output(out).compute();
		var cursor = ((IterableInterval<UnsignedByteType>) out).cursor();
		while (cursor.hasNext()) {
			assertEquals(2, cursor.next().get());
		}

		b.set(0);
		ops.unary("image.fill").input(b).output(img).compute();
		ops.unary(OP_NAME).input(img).output(out).compute();
		cursor = ((IterableInterval<UnsignedByteType>) out).cursor();
		while (cursor.hasNext()) {
			assertEquals(Types.uint8(0), cursor.next().get());
		}

	}

	private <I extends RealType<I>, O extends RealType<O>> void testUpscaleSuite(
		I inType, O outType)
	{
		for (int i = (int) inType.getMinValue(); i <= (int) inType
			.getMaxValue(); i++)
		{
			inType.setReal(i);
			double expected;
			if (outType.getMinValue() < 0 && outType.getMaxValue() >= 0) {
				BigDecimal outMin = BigDecimal.valueOf(outType.getMinValue());
				BigDecimal outMax = BigDecimal.valueOf(outType.getMaxValue());
				BigDecimal inMax = BigDecimal.valueOf(inType.getMaxValue());
				BigDecimal iDec = BigDecimal.valueOf(i);
				expected = iDec.divide(inMax, MathContext.DECIMAL128).multiply(outMax
					.subtract(outMin)).add(outMin).doubleValue();
			}
			else {
				expected = (i / inType.getMaxValue()) * outType.getMaxValue();
			}
			testNormalize(inType, outType, i, expected);
		}
	}

	private <I extends RealType<I>, O extends RealType<O>> void
		testDownscaleSuite(I inType, O outType)
	{
		testMax(inType, outType);
		testMin(inType, outType);
		testMid(inType, outType);
	}

	private <I extends RealType<I>, O extends RealType<O>> void testMax(I inType,
		O outType)
	{
		testNormalize(inType, outType, inType.getMaxValue(), outType.getMaxValue());
	}

	private <I extends RealType<I>, O extends RealType<O>> void testMin(I inType,
		O outType)
	{
		testNormalize(inType, outType, inType.getMinValue(), outType.getMinValue());
	}

	private <I extends RealType<I>, O extends RealType<O>> void testMid(I inType,
		O outType)
	{
		testNormalize(inType, outType, getMidVal(inType), getMidVal(outType));
	}

	private <T extends RealType<T>> double getMidVal(T type) {
		if (type.getMinValue() < 0) {
			BigDecimal min = new BigDecimal(type.getMinValue());
			BigDecimal max = new BigDecimal(type.getMaxValue());
			BigDecimal offset = BigDecimal.ONE;
			if (max.equals(min.multiply(BigDecimal.valueOf(-1)))) {
				offset = BigDecimal.ZERO;
			}
			return min.add(max).add(offset).divide(BigDecimal.valueOf(2),
				MathContext.DECIMAL128).doubleValue();
		}
		return (type.getMaxValue() - type.getMinValue()) / 2;
	}

	private <I extends RealType<I>, O extends RealType<O>> void testNormalize(
		I inType, O outType, double inValue, double expectedOut)
	{
		inType.setReal(inValue);

		// Expected value is a double and we want to make sure it rounds
		// appropriately for this type, which may be an
		// Int type
		outType.setReal(expectedOut);
		expectedOut = outType.getRealDouble();
		outType.setReal(0.0);

		ops.unary(OP_NAME).input(inType).output(outType).compute();
		assertTrue(Double.compare(expectedOut, outType.getRealDouble()) < EPSILON,
			"Normalized value [" + outType.getRealDouble() +
				"] does not match expected value [" + expectedOut + "]");
	}
}
