/*
 * #%L
 * Java implementation of the SciJava Ops matching engine.
 * %%
 * Copyright (C) 2016 - 2024 SciJava developers.
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

/*
* This is autogenerated source code -- DO NOT EDIT. Instead, edit the
* corresponding template in templates/ and rerun bin/generate.groovy.
*/

package org.scijava.ops.engine.adapt.lift;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.scijava.function.Computers;
import org.scijava.ops.engine.AbstractTestEnvironment;
import org.scijava.ops.engine.OpBuilderTestOps;

/**
 * Tests the adaptation of {@link Computers} running on a type into
 * {@link Computers} running on an {@link Iterable} of that type.
 *
 * @author Gabriel Selzer
 */
public class ComputerToIterablesTest extends AbstractTestEnvironment {

	@BeforeAll
	public static void AddNeededOps() {
		ops.register(new ComputerToIterables());
		ops.register(new OpBuilderTestOps());
	}

	@Test
	public void testComputer1ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 1., 2., 3. });
		ops.op("test.addArrays") //
			//
			.input(in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer2ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 2., 4., 6. });
		ops.op("test.addArrays") //
			//
			.input(in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer3ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 3., 6., 9. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer4ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 4., 8., 12. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer5ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 5., 10., 15. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer6ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 6., 12., 18. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer7ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 7., 14., 21. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer8ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 8., 16., 24. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer9ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 9., 18., 27. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer10ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 10., 20., 30. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer11ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 11., 22., 33. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer12ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 12., 24., 36. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer13ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 13., 26., 39. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer14ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 14., 28., 42. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer15ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 15., 30., 45. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

	@Test
	public void testComputer16ToIterables() {
		final List<double[]> in = Arrays.asList(new double[] { 1, 2, 3 });
		final List<double[]> out = Arrays.asList(new double[] { 0, 0, 0 });
		final List<double[]> expected = //
			Arrays.asList(new double[] { 16., 32., 48. });
		ops.op("test.addArrays") //
			//
			.input(in, in, in, in, in, in, in, in, in, in, in, in, in, in, in, in) //
			.output(out).compute();
		assertArrayEquals(out.toArray(), expected.toArray());
	}

}
