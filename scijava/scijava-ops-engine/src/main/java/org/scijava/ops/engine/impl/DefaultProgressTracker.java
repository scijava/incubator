
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
		if (p == null) throw new IllegalArgumentException(
			"No ProgressReporter has been logged for such a hash!");
		return p.getProgress();
	}

	@Override
	public boolean hasStarted(int hash) throws InterruptedException,
		ExecutionException
	{
		ProgressReporter p = executionReporters.get(hash);
		if (p == null) throw new IllegalArgumentException(
			"No ProgressReporter has been logged for such a hash!");
		return p.hasStarted();
	}

	@Override
	public boolean isCompleted(int hash) throws InterruptedException,
		ExecutionException
	{
		ProgressReporter p = executionReporters.get(hash);
		if (p == null) throw new IllegalArgumentException(
			"No ProgressReporter has been logged for such a hash!");
		return p.isComplete();
	}

}
