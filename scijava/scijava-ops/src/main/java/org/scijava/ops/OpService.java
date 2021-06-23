
package org.scijava.ops;

import org.scijava.service.SciJavaService;

public interface OpService extends SciJavaService {

	/**
	 * Begins declaration of an op matching request for locating an op with a
	 * particular name. Additional criteria are specified as chained method calls
	 * on the returned {@link OpBuilder} object. See {@link OpBuilder} for
	 * examples.
	 * 
	 * @param opName The name of the op to be matched.
	 * @return An {@link OpBuilder} for refining the search criteria for an op.
	 * @see OpBuilder
	 */
	OpBuilder op(String opName);

	/** Retrieves the motherlode of available ops. */
	OpEnvironment env();

}
