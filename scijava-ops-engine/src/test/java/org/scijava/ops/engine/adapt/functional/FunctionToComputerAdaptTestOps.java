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

package org.scijava.ops.engine.adapt.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.scijava.function.Functions;
import org.scijava.ops.spi.OpField;
import org.scijava.ops.spi.OpField;
import org.scijava.ops.spi.OpCollection;
import org.scijava.ops.spi.OpCollection;

public class FunctionToComputerAdaptTestOps implements OpCollection {

	@OpField(names = "test.FtC")
	public static final Function<double[], double[]> toComp1 = (in) -> {
		double[] out = new double[in.length];
		for (int i = 0; i < in.length; i++) {
			out[i] += in[i];
		}
		return out;
	};

	@OpField(names = "test.FtC")
	public static final BiFunction<double[], double[], double[]> toComp2 = (in1,
		in2) -> {
		double[] out = new double[in1.length];
		for (int i = 0; i < in1.length; i++) {
			out[i] += in1[i];
			out[i] += in2[i];
		}
		return out;
	};

	@OpField(names = "test.FtC")
	public static final Functions.Arity3<double[], double[], double[], double[]> toComp3 =
		(in1, in2, in3) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity4<double[], double[], double[], double[], double[]> toComp4 =
		(in1, in2, in3, in4) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity5<double[], double[], double[], double[], double[], double[]> toComp5 =
		(in1, in2, in3, in4, in5) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity6<double[], double[], double[], double[], double[], double[], double[]> toComp6 =
		(in1, in2, in3, in4, in5, in6) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity7<double[], double[], double[], double[], double[], double[], double[], double[]> toComp7 =
		(in1, in2, in3, in4, in5, in6, in7) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity8<double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp8 =
		(in1, in2, in3, in4, in5, in6, in7, in8) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity9<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp9 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity10<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp10 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
				out[i] += in10[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity11<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp11 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
				out[i] += in10[i];
				out[i] += in11[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity12<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp12 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
				out[i] += in10[i];
				out[i] += in11[i];
				out[i] += in12[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity13<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp13 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
				out[i] += in10[i];
				out[i] += in11[i];
				out[i] += in12[i];
				out[i] += in13[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity14<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp14 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13,
			in14) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
				out[i] += in10[i];
				out[i] += in11[i];
				out[i] += in12[i];
				out[i] += in13[i];
				out[i] += in14[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity15<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp15 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14,
			in15) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
				out[i] += in10[i];
				out[i] += in11[i];
				out[i] += in12[i];
				out[i] += in13[i];
				out[i] += in14[i];
				out[i] += in15[i];
			}
			return out;
		};

	@OpField(names = "test.FtC")
	public static final Functions.Arity16<double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[], double[]> toComp16 =
		(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14,
			in15, in16) -> {
			double[] out = new double[in1.length];
			for (int i = 0; i < in1.length; i++) {
				out[i] += in1[i];
				out[i] += in2[i];
				out[i] += in3[i];
				out[i] += in4[i];
				out[i] += in5[i];
				out[i] += in6[i];
				out[i] += in7[i];
				out[i] += in8[i];
				out[i] += in9[i];
				out[i] += in10[i];
				out[i] += in11[i];
				out[i] += in12[i];
				out[i] += in13[i];
				out[i] += in14[i];
				out[i] += in15[i];
				out[i] += in16[i];
			}
			return out;
		};
}
