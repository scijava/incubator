/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.scijava.ops.benchmarks.matching;

import net.imglib2.img.array.ArrayImgs;
import org.openjdk.jmh.annotations.*;
import org.scijava.ops.api.Hints;

/**
 * JMH Benchmarks comparing the performance of basic Op invocations
 *
 * @author Gabriel Selzer
 */
public class BenchmarkMatching {

	private static final Hints HINTS = new Hints("cache.IGNORE");

	@Fork(value = 1, warmups = 2)
	@Warmup(iterations = 2)
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public void runStatic(MatchingState state) {
		var out = ArrayImgs.doubles(state.in.dimensionsAsLongArray());
		MatchingOpCollection.op(state.in, 1.0, out);
	}

	@Fork(value = 1, warmups = 2)
	@Warmup(iterations = 2)
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public void runOp(final MatchingState state) {
		var out = ArrayImgs.doubles(state.in.dimensionsAsLongArray());
		state.env.binary("benchmark.match", HINTS) //
			.input(state.in, 1.0) //
			.output(out) //
			.compute();
	}

	@Fork(value = 1, warmups = 2)
	@Warmup(iterations = 2)
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public void runOpAdapted(final MatchingState state) {
		var tmp = state.env.binary("benchmark.match", HINTS) //
			.input(state.in, 1.0) //
			.apply();
	}
}
