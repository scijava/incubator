
package org.scijava.ops;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import org.scijava.types.Types;

public interface OpRef {

	/** Gets the name of the op. */
	String getName();

	/** Gets the type which the op must match. */
	Type getType();

	/**
	 * Gets the op's output type constraint, or null for no constraint.
	 */
	Type getOutType();

	/** Gets the op's arguments. */
	Type[] getArgs();

	/**
	 * Gets a label identifying the op's scope (i.e., its name and/or types).
	 */
	String getLabel();

	boolean typesMatch(Type opType);

	/**
	 * Determines whether the specified type satisfies the op's required types
	 * using {@link Types#isApplicable(Type[], Type[])}.
	 */
	boolean typesMatch(Type opType, Map<TypeVariable<?>, Type> typeVarAssigns);

}
