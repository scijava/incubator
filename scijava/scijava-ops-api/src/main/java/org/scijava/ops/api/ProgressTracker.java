
package org.scijava.ops.api;

/**
 * Interface used as a "slot" for a {@link ProgressReporter}
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

	public boolean setReporter(ProgressReporter p);

	public Object progressOf();

	public double getProgress();

	public boolean hasStarted();

	public boolean hasCompleted();

}
