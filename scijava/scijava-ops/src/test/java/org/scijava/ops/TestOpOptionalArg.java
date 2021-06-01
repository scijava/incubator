
package org.scijava.ops;

import java.util.function.BiFunction;

import org.scijava.ops.core.Op;
import org.scijava.param.Optional;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

@Plugin(type = Op.class, name = "test.optionalAdd")
@Parameter(key = "in1")
@Parameter(key = "in2")
@Parameter(key = "out", itemIO = ItemIO.OUTPUT)
public class TestOpOptionalArg implements BiFunction<Double, Double, Double> {

	@Override
	public Double apply(Double t, @Optional Double u) {
		if (u == null) return t;
		return t + u;
	}

}
