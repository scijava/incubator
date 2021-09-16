package org.scijava.ops.engine.impl;

import org.scijava.ops.api.RichOp;

public class OpExecution {

	private final RichOp<?> op;
	private ProgressReporter reporter;

	public OpExecution(RichOp<?> op) {
		this.op = op;
	}

	public void setReporter(ProgressReporter p) {
		this.reporter = p;
	}

	public RichOp<?> op() {
		return op;
	}

	public ProgressReporter reporter() {
		return reporter;
	}


}
