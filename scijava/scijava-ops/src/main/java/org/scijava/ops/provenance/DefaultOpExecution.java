
package org.scijava.ops.provenance;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.scijava.ops.OpInfo;
import org.scijava.ops.impl.DefaultOpEnvironment;

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

	/**
	 * The {@link Object} produced by this execution of {@code wrappedInstance}
	 * (or {@code instance}, if {@code wrappedInstance} is {@code null}.
	 */
	private final CompletableFuture<Object> output;

	public DefaultOpExecution(UUID executionHash, OpInfo info, Object instance,
		Object wrappedOp)
	{
		this.executionHash = executionHash;
		this.info = info;
		this.instance = instance;
		this.wrappedInstance = wrappedOp;
		this.output = new CompletableFuture<>();
	}

	public DefaultOpExecution(UUID executionHash, OpInfo info, Object instance) {
		this.executionHash = executionHash;
		this.info = info;
		this.instance = instance;
		this.wrappedInstance = null;
		this.output = new CompletableFuture<>();
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
