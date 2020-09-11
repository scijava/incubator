package org.scijava.ops;

import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Producer;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

@Plugin(type = OpCollection.class)
public class OpMethodTestOps {
	
	// -- Functions -- //
	@OpMethod(names = "test.multiplyNumericStrings", type = Producer.class)
	@Parameter(key = "multipliedNumericStrings", itemIO = ItemIO.OUTPUT)
	public static Integer multiplyNumericStringsProducer() {
		return Integer.valueOf(1);
	}

}
