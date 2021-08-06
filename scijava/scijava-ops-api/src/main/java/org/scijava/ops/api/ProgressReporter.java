package org.scijava.ops.api;

/**
 * A basic interface for Objects reporting the progress of certain tasks.
 *
 * @author Gabriel Selzer
 */
public interface ProgressReporter {

	Object progressOf();

	default void reportPixel() {
		reportPixels(1);
	}
	
	void reportPixels(long numPixels);

	void reportStart();

	void reportComplete();

	double getProgress();

	boolean hasStarted();

	boolean isComplete();

}