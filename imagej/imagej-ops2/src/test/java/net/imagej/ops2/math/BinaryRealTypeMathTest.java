/*
 * #%L
 * ImageJ2 software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2022 ImageJ2 developers.
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

package net.imagej.ops2.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.imagej.ops2.AbstractOpTest;
import net.imglib2.type.numeric.integer.IntType;
import org.junit.jupiter.api.Test;
import org.scijava.ops.engine.math.MathOps;

/**
 * Tests {@link BinaryRealTypeMath}.
 *
 * @author Mark Hiner
 */
public class BinaryRealTypeMathTest extends AbstractOpTest {

	private static final IntType A = new IntType(25);
	private static final IntType B = new IntType(25);
	@Test
	public void testAddF() {
		for (String opName : MathOps.ADD.split(", ")) {
			IntType c = ops.binary(opName).input(A, B).outType(IntType.class).apply();
			assertEquals(A.get() + B.get(), c.get());
		}
	}

	@Test
	public void testAddC() {
		for (String opName : MathOps.ADD.split(", ")) {
			IntType c = new IntType();
			ops.binary(opName).input(A, B).output(c).compute();
			assertEquals(A.get() + B.get(), c.get());
		}
	}

	@Test
	public void testSubF() {
		for (String opName : MathOps.SUB.split(", ")) {
			IntType c = ops.binary(opName).input(A, B).outType(IntType.class).apply();
			assertEquals(A.get() - B.get(), c.get());
		}
	}

	@Test
	public void testSubC() {
		for (String opName : MathOps.SUB.split(", ")) {
			IntType c = new IntType();
			ops.binary(opName).input(A, B).output(c).compute();
			assertEquals(A.get() - B.get(), c.get());
		}
	}

	@Test
	public void testDivF() {
		for (String opName : MathOps.DIV.split(", ")) {
			IntType c = ops.binary(opName).input(A, B).outType(IntType.class).apply();
			assertEquals(A.get() / B.get(), c.get());
		}
	}

	@Test
	public void testDivC() {
		for (String opName : MathOps.DIV.split(", ")) {
			IntType c = new IntType();
			ops.binary(opName).input(A, B).output(c).compute();
			assertEquals(A.get() / B.get(), c.get());
		}
	}

	@Test
	public void testMulF() {
		for (String opName : MathOps.MUL.split(", ")) {
			IntType c = ops.binary(opName).input(A, B).outType(IntType.class).apply();
			assertEquals(A.get() * B.get(), c.get());
		}
	}

	@Test
	public void testMulC() {
		for (String opName : MathOps.MUL.split(", ")) {
			IntType c = new IntType();
			ops.binary(opName).input(A, B).output(c).compute();
			assertEquals(A.get() * B.get(), c.get());
		}
	}

// FIXME: inplaces would be nice
//	@Test
//	public void testAddI() {
//		IntType a = new IntType(25);
//		IntType b = new IntType(45);
//		int expected = a.get() + b.get();
//		ops.binary("math.add").input(a, b).mutate1();
//		assertEquals(a.get(), expected);
//	}
}
