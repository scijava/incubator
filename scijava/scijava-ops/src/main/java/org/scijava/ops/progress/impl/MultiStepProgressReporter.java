package org.scijava.ops.progress.impl;

import java.util.List;

import org.scijava.ops.progress.ProgressReporter;


public class MultiStepProgressReporter implements ProgressReporter {

	/** The Object in control of reporting the progress */
	private final Object o;
	private final List<ProgressReporter> stepReporters;

	private boolean started;
	private boolean completed;

	public MultiStepProgressReporter(final Object o, final List<ProgressReporter> stepReporters) {
		this.o = o;
		this.stepReporters = stepReporters;
		this.started = false;
		this.completed = false;
	}

	@Override
	public Object progressOf() {
		return o;
	}

	@Override
	public void reportPixel() {
		reportPixels(1);
	}

	@Override
	public void reportPixels(long numPixels) {
		throw new UnsupportedOperationException("This ProgressReporter cannot report pixels on its own!");
	}

	@Override
	public void reportStart() {
		started = true;
	}

	@Override
	public void reportComplete() {
		completed = true;
	}

	@Override
	public double getProgress() {
		double sumProgress = 0;
		for (ProgressReporter p : stepReporters) {
			sumProgress += p.getProgress();
		}
		return sumProgress / stepReporters.size();
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
