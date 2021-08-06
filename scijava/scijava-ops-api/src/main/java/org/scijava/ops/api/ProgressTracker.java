
package org.scijava.ops.api;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Defines API for an Object that manages {@link ProgressReporter}s for a
 * particular Op chain
 * <p>
 * NB: We deliberately avoid any sort of {@code getReporter()} method. This
 * prevents any race conditions between a user wishing to inquire about the
 * progress of an Op and that Op overriding some default
 * {@link ProgressReporter} with a {@code ProgressReporter} of its choosing. We
 * instead expose the progress reporting API to the user through this interface.
 *
 * @author Gabriel Selzer
 */
public interface ProgressTracker {

	// -- USER API -- //

	default ProgressReporter setReporter(ProgressReporter p, Object... inputs) {
		return setReporter(Objects.hash(inputs), p);
	}

	default double getProgress(Object... inputs) throws InterruptedException,
		ExecutionException
	{
		return getProgress(Objects.hash(inputs));
	}

	default boolean hasStarted(Object... inputs) throws InterruptedException,
		ExecutionException
	{
		return hasStarted(Objects.hash(inputs));
	}

	default boolean isCompleted(Object... inputs) throws InterruptedException,
		ExecutionException
	{
		return isCompleted(Objects.hash(inputs));
	}

	// -- IMPLEMENTATION METHODS

	ProgressReporter setReporter(int hash, ProgressReporter p);

	double getProgress(int hash) throws InterruptedException, ExecutionException;

	boolean hasStarted(int hash) throws InterruptedException, ExecutionException;

	boolean isCompleted(int hash) throws InterruptedException, ExecutionException;

}
