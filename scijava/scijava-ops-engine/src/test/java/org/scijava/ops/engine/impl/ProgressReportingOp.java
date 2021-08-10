package org.scijava.ops.engine.impl;

import org.scijava.function.Computers;
import org.scijava.ops.api.ProgressReporter;
import org.scijava.ops.api.ProgressTracker;
import org.scijava.ops.spi.Op;
import org.scijava.ops.spi.OpProgressReporter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "test.progressAdd")
public class ProgressReportingOp implements Computers.Arity2<int[], int[], int[]>{

	@OpProgressReporter
	ProgressTracker pt;

	@Override
	public void compute(int[] t, int[] u, int[] out) {
		ProgressReporter p = new DefaultProgressReporter(this, out.length);
		pt.setReporter(p, t, u, out);

		for(int i = 0; i < out.length; i++) {
			out[i] = t[i] + u[i];
			p.reportPixel();
		}
	}

}
