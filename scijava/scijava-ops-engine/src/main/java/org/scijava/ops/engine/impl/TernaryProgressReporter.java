
package org.scijava.ops.engine.impl;

import org.scijava.ops.api.ProgressReporter;

/**
 * A {@link ProgressReporter} with 3 states:
 * <ol>
 * <li>Not yet started
 * <li>In progress
 * <li>Completed
 * </ol>
 * <b>NOTE</b>: This Progress Reporter decides to under-report progress, as
 * opposed to over-report progress. This decision was made to prevent confusion
 * when an Op overwrites this {@link ProgressReporter} with another
 * implementation. That implementation will likely drop the progress to
 * {@code 0}. If we over-reported progress (say, by making the progress for the
 * in-progress state {@code 0.5}), switching the reporter would cause a
 * confusing <i>decrease</i> in progress. Thus progress is reported as {@code 0}
 * for <b>both</b> the not-yet-started <b>and</b> the in-progress states.
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
		return completed ? 1.0 : 0.0;
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
