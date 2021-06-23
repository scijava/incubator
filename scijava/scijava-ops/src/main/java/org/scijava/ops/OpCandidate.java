
package org.scijava.ops;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

import org.scijava.ops.matcher.DefaultOpCandidate;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;

public interface OpCandidate {

	public static enum StatusCode {
		MATCH, //
		INVALID_STRUCT, //
		OUTPUT_TYPES_DO_NOT_MATCH, //
		TOO_MANY_ARGS, //
		TOO_FEW_ARGS, //
		ARG_TYPES_DO_NOT_MATCH, //
		REQUIRED_ARG_IS_NULL, //
		CANNOT_CONVERT, //
		DOES_NOT_CONFORM, OTHER //
	}

	/** Gets the op execution environment of the desired match. */
	OpEnvironment env();

	/** Gets the op reference describing the desired match. */
	OpRef getRef();

	/** Gets the {@link OpInfo} metadata describing the op to match against. */
	OpInfo opInfo();

	/** Gets the priority of this result */
	double priority();

	/** Gets the mapping between {@link TypeVariable}s and {@link Type}s that makes the {@link DefaultOpCandidate} pair legal. */
	Map<TypeVariable<?>, Type> typeVarAssigns();

	Type[] paddedArgs();

	/**
	 * Gets the {@link Struct} metadata describing the op to match against.
	 *
	 * @see OpInfo#struct()
	 */
	Struct struct();

	/** Sets the status of the matching attempt. */
	void setStatus(StatusCode code);

	/** Sets the status of the matching attempt. */
	void setStatus(StatusCode code, String message);

	/** Sets the status of the matching. */
	void setStatus(StatusCode code, String message, Member<?> item);

	/** Gets the matching status code. */
	StatusCode getStatusCode();

	/**
	 * Gets the status item related to the matching status, if any. Typically,
	 * if set, this is the parameter for which matching failed.
	 */
	Member<?> getStatusItem();

	/** Gets a descriptive status message in human readable form. */
	String getStatus();

	StructInstance<?> createOpInstance(List<?> dependencies);

	Object createOp(List<?> dependencies);

}
