
package org.scijava.ops.reduce;

import org.scijava.ops.core.Op;
import org.scijava.param.Optional;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;
import org.scijava.ops.function.Functions;

@Plugin(type = Op.class, name = "test.optionalAdd")
@Parameter(key = "in1")
@Parameter(key = "in2")
@Parameter(key = "in3")
@Parameter(key = "out", itemIO = ItemIO.OUTPUT)
public class TestOpOptionalArg implements
	Functions.Arity3<Double, Double, Double, Double>
{

	@Override
	public Double apply(Double t, @Optional Double u, @Optional Double v) {
		if (u == null) u = 0.;
		if (v == null) v = 0.;
		return t + u + v;
	}

}
