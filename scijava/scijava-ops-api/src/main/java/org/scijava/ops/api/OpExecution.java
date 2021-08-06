
package org.scijava.ops.api;

import java.util.UUID;
import java.util.concurrent.Future;

public interface OpExecution {

	/**
	 * Returns the output {@link Object} of this execution
	 *
	 * @return the output of the execution
	 */
	Future<Object> output();

	/**
	 * Logs the output of this execution
	 * 
	 * @param o the output of this execution
	 * @return {@code true} iff the output {@code o} was successfully logged.
	 */
	boolean complete(Object o);

	/**
	 * Returns the {@link Object} whose execution has been tracked by this
	 * summary.
	 *
	 * @return the executor
	 */
	Object executor();

	/**
	 * Returns the wrapping of {@link OpExecution#executor}, if it exists
	 *
	 * @return the wrapping of {@link OpExecution#executor}
	 */
	Object wrappedExecutor();

	/**
	 * Returns the {@link ProgressTracker} describing the execution's progress
	 * 
	 * @return the {@link ProgressTracker}
	 */
	ProgressTracker progressTracker();

	/**
	 * Describes whether {@code o} is the output of this
	 * {@link DefaultOpExecution}
	 *
	 * @param o the {@link Object} that might be {@link OpExecution#output}
	 * @return true iff {@code o == output}
	 */
	boolean isOutput(Object o);

	/**
	 * Returns the {@link OpInfo} responsible for creating this
	 * {@link OpExecution#executor}
	 *
	 * @return the {@link OpInfo}
	 */
	OpInfo info();

	/**
	 * Returns the {@link UUID} identifying the Op chain responsible for creating
	 * this {@link OpExecution#executor}
	 *
	 * @return the identifying {@link UUID}
	 */
	UUID executionTreeHash();

	/**
	 * Returns the completion status of this {@link OpExecution}
	 *
	 * @return true iff this {@link OpExecution} has completed.
	 */
	boolean hasCompleted();

	/**
	 * Returns the progress of this {@link OpExecution}
	 *
	 * @return the progress of this execution, in the range [0.0, 1.0]. A progress
	 *         of {@code 1.0} indicates completion.
	 */
	double getProgress();

}
