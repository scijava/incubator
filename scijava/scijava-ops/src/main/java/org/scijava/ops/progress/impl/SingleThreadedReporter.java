package org.scijava.ops.progress.impl;

import java.util.concurrent.atomic.AtomicLong;

import org.scijava.ops.progress.ProgressReporter;


public class SingleThreadedReporter implements ProgressReporter {

	/** The Object in control of reporting the progress */
	private final Object o;

	private final long numPieces;
	// TODO: Consider whether we could get away without using an AtomicLong here
	private final AtomicLong piecesCompleted;
	private final long piecesPerChunk;

	private boolean started;
	private boolean completed;

	public SingleThreadedReporter(final Object o, final long numPieces) {
		this.o = o;
		this.numPieces = numPieces;
		this.piecesPerChunk = numPieces;
		this.piecesCompleted = new AtomicLong(0);
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
	public void reportPixels(final long numPixels) {
		piecesCompleted.getAndAdd(numPixels);
		checkForCompletion(piecesCompleted.longValue());
	}

	private void checkForCompletion(final long newValue) {
		if (newValue == numPieces) {
			completed = true;
		}
		if (newValue > numPieces) {
			throw new IllegalStateException("More pixels have been completed than exist!");
		}
	}

	@Override
	public void reportChunk() {
		reportPixels(piecesPerChunk);
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
		return piecesCompleted.doubleValue() / (numPieces);
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
