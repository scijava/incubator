package org.scijava.ops.engine.impl;


public interface ProgressReporter {

	default void reportElement() {
		reportElements(1);
	}

	void reportElements(long completedElements);

	double getProgress();

}
