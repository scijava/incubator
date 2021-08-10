
package org.scijava.ops.engine.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.scijava.ops.api.ProgressReporter;
import org.scijava.ops.api.ProgressTracker;

/**
 * A simple {@link ProgressTracker} implementation
 * 
 * @author Gabe Selzer
 */
public class DefaultProgressTracker implements ProgressTracker {

	private final Map<Integer, ProgressReporter> executionReporters =
		new ConcurrentHashMap<>();

	@Override
	public ProgressReporter setReporter(int hash, ProgressReporter p) {
		return executionReporters.put(hash, p);
	}

	@Override
	public double getProgress(int hash) throws InterruptedException,
		ExecutionException
	{
		ProgressReporter p = executionReporters.get(hash);
		if (p != null) {
			return p.getProgress();
		}
		// TODO: remove
//		if (p == null) throw new IllegalArgumentException(
//			"No ProgressReporter has been logged for such a hash!");
//		return p.getProgress();

		// If we don't have a ProgressReporter, we can assume that no progress has
		// been made on the operation.
		return 0.0; 
	}

	@Override
	public boolean hasStarted(int hash) throws InterruptedException,
		ExecutionException
	{
		ProgressReporter p = executionReporters.get(hash);
		// TODO: delete
//		if (p == null) throw new IllegalArgumentException(
//			"No ProgressReporter has been logged for such a hash!");
		if (p != null) {
			return p.hasStarted();
		}
		// If we don't have a ProgressReporter, we can assume that the operation has
		// not yet started.
		return false; 
	}

	@Override
	public boolean isCompleted(int hash) throws InterruptedException,
		ExecutionException
	{
		ProgressReporter p = executionReporters.get(hash);
		// TODO: delete
//		if (p == null) throw new IllegalArgumentException(
//			"No ProgressReporter has been logged for such a hash!");
//		return p.isComplete();

		if (p != null) {
			return p.isComplete();
		}
		// If we don't have a ProgressReporter, we can assume that the operation has
		// not yet started.
		return false; 
	}

}
