package org.scijava.ops.reduce;

import org.scijava.struct.ItemIO;
import org.scijava.ops.core.Op;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "test.optionalOr")
@Parameter(key = "in1")
@Parameter(key = "in2")
@Parameter(key = "in3")
@Parameter(key = "out", itemIO = ItemIO.OUTPUT)
public class TestOpOptionalFromIFace implements BiFunctionWithOptional<Boolean, Boolean, Boolean, Boolean>{

	@Override
	public Boolean apply(Boolean in1, Boolean in2, Boolean in3) {
		if (in3 == null) in3 = false;
		return in1 | in2 | in3;
	}

}
