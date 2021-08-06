
package org.scijava.ops.engine.impl;

import org.scijava.ops.api.ProgressReporter;

/**
 * A {@link ProgressReporter} with 3 states:
 * <ol>
 * <li>Not yet started
 * <li>In progress
 * <li>Completed
 * </ol>
 * 
 * @author Gabe Selzer
 */
public class TernaryProgressReporter implements ProgressReporter {

	private final Object o;
	private boolean started;
	private boolean completed;

	public TernaryProgressReporter(final Object o) {
		this.o = o;
		this.started = false;
		this.completed = false;
	}

	@Override
	public Object progressOf() {
		return o;
	}

	@Override
	public void reportPixels(long numPixels) {}

	@Override
	public void reportStart() {
		this.started = true;
	}

	@Override
	public void reportComplete() {
		this.started = true;
		this.completed = true;
	}

	@Override
	public double getProgress() {
		if (!started) return 0.0; // not started
		if (!completed) return 0.5; // started, but incomplete
		return 1.0; // complete
	}

	@Override
	public boolean hasStarted() {
		return started;
	}

	@Override
	public boolean isComplete() {
		return completed;
	}

}
