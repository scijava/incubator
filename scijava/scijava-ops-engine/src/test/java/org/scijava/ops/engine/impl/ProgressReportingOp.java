package org.scijava.ops.engine.impl;

import java.util.function.BiFunction;

import org.scijava.ops.api.ProgressReporter;
import org.scijava.ops.api.ProgressTracker;
import org.scijava.ops.spi.Op;
import org.scijava.ops.spi.OpProgressReporter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "test.progressAdd")
public class ProgressReportingOp implements BiFunction<Double[], Double[], Double[]>{

	@OpProgressReporter
	ProgressTracker pt;

	@Override
	public Double[] apply(Double[] t, Double[] u) {
		int numElements = Math.min(t.length, u.length);
		Double[] out = new Double[numElements];

		ProgressReporter p = new DefaultProgressReporter(this, numElements);
		pt.setReporter(p);

		for(int i = 0; i < numElements; i++) {
			out[i] = t[i] + u[i];
			p.reportPixel();
		}
		
		return out;
	}

}
