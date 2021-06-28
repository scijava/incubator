package org.scijava.ops.math;

import java.util.stream.IntStream;

import org.scijava.function.Computers;
import org.scijava.ops.OpCollection;
import org.scijava.ops.OpField;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = OpCollection.class)
public class Zero {

	public static final String NAMES = MathOps.ZERO;

	// --------- Computers ---------

	@OpField(names = NAMES)
	public static final Computers.Arity0<double[]> MathParallelPointwiseZeroDoubleArrayComputer = out -> {
		IntStream.range(0, out.length).parallel().forEach(i -> {
			out[i] = 0.0;
		});
	};
}
