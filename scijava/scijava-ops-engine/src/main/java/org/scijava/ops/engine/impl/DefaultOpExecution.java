
package org.scijava.ops.engine.impl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.scijava.ops.api.OpExecution;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.ProgressTracker;

/**
 * Describes the execution of an Op
 *
 * @author Gabe Selzer
 */
public class DefaultOpExecution implements OpExecution {

	/** The identifier identifying the matching call */
	private final UUID executionHash;

	/** The {@link OpInfo} responsible for creating {@code instance} */
	private final OpInfo info;

	/** The {@link Object} created by {@code info} */
	private final Object instance;

	/** The {@link Object} returned by the call to {@link DefaultOpEnvironment} */
	private final Object wrappedInstance;

	/** The {@link ProgressTracker} used by the Op / OpWrapper */
	private final ProgressTracker tracker;

	/**
	 * The {@link Object} produced by this execution of {@code wrappedInstance}
	 * (or {@code instance}, if {@code wrappedInstance} is {@code null}.
	 */
	private final CompletableFuture<Object> output;

	public DefaultOpExecution(UUID executionHash, OpInfo info, Object instance,
		Object wrappedOp, ProgressTracker tracker)
	{
		this.executionHash = executionHash;
		this.info = info;
		this.instance = instance;
		this.wrappedInstance = wrappedOp;
		this.output = new CompletableFuture<>();
		this.tracker = tracker;
	}

	public DefaultOpExecution(UUID executionHash, OpInfo info, Object instance, ProgressTracker tracker) {
		this.executionHash = executionHash;
		this.info = info;
		this.instance = instance;
		this.wrappedInstance = null;
		this.output = new CompletableFuture<>();
		this.tracker = tracker;
	}

	/**
	 * Returns the output {@link Object} of this execution
	 *
	 * @return the output of the execution
	 */
	@Override
	public Future<Object> output() {
		return output;
	}

	/**
	 * Returns the {@link Object} whose execution has been tracked by this
	 * summary.
	 *
	 * @return the executor
	 */
	@Override
	public Object executor() {
		return instance;
	}

	/**
	 * Returns the wrapping of {@link DefaultOpExecution#instance}, if it exists
	 *
	 * @return the wrapping of {@link DefaultOpExecution#instance}
	 */
	@Override
	public Object wrappedExecutor() {
		return wrappedInstance;
	}

	/**
	 * Returns the {@link ProgressTracker} describing the execution's progress
	 * 
	 * @return the {@link ProgressTracker}
	 */
	@Override
	public ProgressTracker progressTracker() {
		return tracker;
	}

	/**
	 * Describes whether {@code o} is the output of this
	 * {@link DefaultOpExecution}
	 *
	 * @param o the {@link Object} that might be {@link DefaultOpExecution#output}
	 * @return true iff {@code o == output}
	 */
	@Override
	public boolean isOutput(Object o) {
		if (!output.isDone()) {
			return false;
		}
		try {
			return output.getNow(this) == o;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns the {@link OpInfo} responsible for creating this
	 * {@link DefaultOpExecution#instance}
	 *
	 * @return the {@link OpInfo}
	 */
	@Override
	public OpInfo info() {
		return info;
	}

	/**
	 * Returns the {@link UUID} identifying the Op chain responsible for creating
	 * this {@link DefaultOpExecution#instance}
	 *
	 * @return the identifying {@link UUID}
	 */
	@Override
	public UUID executionTreeHash() {
		return executionHash;
	}

	@Override
	public boolean hasCompleted() {
		return output.isDone();
	}

	@Override
	public double getProgress() {
		return hasCompleted() ? 1. : 0.;
	}

	@Override
	public boolean complete(Object o) {
		return output.complete(o);
	}

}
