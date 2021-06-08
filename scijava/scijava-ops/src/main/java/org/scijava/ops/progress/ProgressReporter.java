package org.scijava.ops.progress;

/**
 * A basic interface for Objects reporting the progress of certain tasks.
 * @author G
 *
 */
public interface ProgressReporter {

	Object progressOf();

	void reportPixel();
	
	void reportPixels(long numPixels);
	
	void reportChunk();

	void reportStart();

	void reportComplete();

	double getProgress();

	boolean hasStarted();

	boolean isComplete();

}
