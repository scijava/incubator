/*
 * #%L
 * SciJava Operations: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2016 - 2019 SciJava Ops developers.
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

package org.scijava.ops.adapt.lift;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.scijava.function.Functions;
import org.scijava.ops.api.OpField;
import org.scijava.ops.api.OpField;
import org.scijava.ops.api.OpCollection;
import org.scijava.ops.api.OpCollection;
import org.scijava.plugin.Plugin;

/**
 * Converts {@link Functions} operating on single types to {@link Functions}
 * that operate on arrays of types. N.B. it is the user's responsibility to pass
 * arrays of the same length (otherwise the Op will stop when one of the arrays
 * runs out of {@link Object}s).
 * 
 * @author Gabriel Selzer
 */
@Plugin(type = OpCollection.class)
public class FunctionToArrays<I, I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O> {

	// TODO: extract logic to a utility class
	private int minLength(Object[]... arrays) {
		int minLength = Integer.MAX_VALUE;
		for (Object[] array : arrays)
			if (array.length < minLength) minLength = array.length;
		return minLength;
	}
	
	// NOTE: we cannot convert Producers since there is no way to determine the
	// length of the output array

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Function<I, O>, Function<I[], O[]>> liftFunction1 =
		(function) -> {
			return (in) -> {
				int len = minLength(in);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<BiFunction<I1, I2, O>, BiFunction<I1[], I2[], O[]>> liftFunction2 =
		(function) -> {
			return (in1, in2) -> {
				int len = minLength(in1, in2);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity3<I1, I2, I3, O>, Functions.Arity3<I1[], I2[], I3[], O[]>> liftFunction3 =
		(function) -> {
			return (in1, in2, in3) -> {
				int len = minLength(in1, in2, in3);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity4<I1, I2, I3, I4, O>, Functions.Arity4<I1[], I2[], I3[], I4[], O[]>> liftFunction4 =
		(function) -> {
			return (in1, in2, in3, in4) -> {
				int len = minLength(in1, in2, in3, in4);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity5<I1, I2, I3, I4, I5, O>, Functions.Arity5<I1[], I2[], I3[], I4[], I5[], O[]>> liftFunction5 =
		(function) -> {
			return (in1, in2, in3, in4, in5) -> {
				int len = minLength(in1, in2, in3, in4, in5);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity6<I1, I2, I3, I4, I5, I6, O>, Functions.Arity6<I1[], I2[], I3[], I4[], I5[], I6[], O[]>> liftFunction6 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity7<I1, I2, I3, I4, I5, I6, I7, O>, Functions.Arity7<I1[], I2[], I3[], I4[], I5[], I6[], I7[], O[]>> liftFunction7 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O>, Functions.Arity8<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], O[]>> liftFunction8 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>, Functions.Arity9<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], O[]>> liftFunction9 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>, Functions.Arity10<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], I10[], O[]>> liftFunction10 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9, in10) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0], in10[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i], in10[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O>, Functions.Arity11<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], I10[], I11[], O[]>> liftFunction11 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0], in10[0], in11[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i], in10[i], in11[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O>, Functions.Arity12<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], I10[], I11[], I12[], O[]>> liftFunction12 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0], in10[0], in11[0], in12[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i], in10[i], in11[i], in12[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O>, Functions.Arity13<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], I10[], I11[], I12[], I13[], O[]>> liftFunction13 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0], in10[0], in11[0], in12[0], in13[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i], in10[i], in11[i], in12[i], in13[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O>, Functions.Arity14<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], I10[], I11[], I12[], I13[], I14[], O[]>> liftFunction14 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0], in10[0], in11[0], in12[0], in13[0], in14[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i], in10[i], in11[i], in12[i], in13[i], in14[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O>, Functions.Arity15<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], I10[], I11[], I12[], I13[], I14[], I15[], O[]>> liftFunction15 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0], in10[0], in11[0], in12[0], in13[0], in14[0], in15[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i], in10[i], in11[i], in12[i], in13[i], in14[i], in15[i]);
				}
				return out;
			};
		};

	@OpField(names = "adapt", params = "fromOp, toOp")
	public final Function<Functions.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O>, Functions.Arity16<I1[], I2[], I3[], I4[], I5[], I6[], I7[], I8[], I9[], I10[], I11[], I12[], I13[], I14[], I15[], I16[], O[]>> liftFunction16 =
		(function) -> {
			return (in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16) -> {
				int len = minLength(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16);
				if (len == 0) throw new UnsupportedOperationException("Unable to create an empty output array.");
				O component = function.apply(in1[0], in2[0], in3[0], in4[0], in5[0], in6[0], in7[0], in8[0], in9[0], in10[0], in11[0], in12[0], in13[0], in14[0], in15[0], in16[0]);
				@SuppressWarnings("unchecked")
				O[] out = (O[]) Array.newInstance(component.getClass(), len);
				
				for (int i = 0; i < len; i++) {
					out[i] = function.apply(in1[i], in2[i], in3[i], in4[i], in5[i], in6[i], in7[i], in8[i], in9[i], in10[i], in11[i], in12[i], in13[i], in14[i], in15[i], in16[i]);
				}
				return out;
			};
		};

}
