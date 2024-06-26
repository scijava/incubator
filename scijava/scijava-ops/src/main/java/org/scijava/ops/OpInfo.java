
package org.scijava.ops;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

import org.scijava.log.Logger;
import org.scijava.ops.hints.Hints;
import org.scijava.ops.hints.OpHints;
import org.scijava.ops.hints.impl.ImmutableHints;
import org.scijava.ops.matcher.OpCandidate;
import org.scijava.ops.matcher.OpRef;
import org.scijava.param.ValidityException;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.util.MiscUtils;

/**
 * Metadata about an op implementation.
 * 
 * @author Curtis Rueden
 * @author David Kolb
 */
public interface OpInfo extends Comparable<OpInfo> {

	/** Generic type of the op. This will be the parameterized type of the concrete class */
	Type opType();

	/** Gets the associated {@link Struct} metadata. */
	Struct struct();

	/** Gets the hints declared in the {@link OpHints} annotation */
	Hints declaredHints();

	default Hints formHints(OpHints h) {
		if (h == null) return new ImmutableHints(new String[0]);
		return new ImmutableHints(h.hints());
	}

	/** Gets the op's input parameters. */
	default List<Member<?>> inputs() {
		return OpUtils.inputs(struct());
	}

	/** Gets the op's output parameters. */
	default Member<?> output() {
		return OpUtils.outputs(struct()).get(0);
	}

	/** Gets the op's dependencies on other ops. */
	default List<OpDependencyMember<?>> dependencies() {
		return OpUtils.dependencies(struct());
	}
	
	default OpCandidate createCandidate(OpEnvironment env, Logger log, OpRef ref, Map<TypeVariable<?>, Type> typeVarAssigns) {
		return new OpCandidate(env, log, ref, this, typeVarAssigns);
	}

	/** The op's priority. */
	double priority();

	/** A fully qualified, unambiguous name for this specific op implementation. */
	String implementationName();

	/** Create a StructInstance using the Struct metadata backed by an object of the op itself. */
	StructInstance<?> createOpInstance(List<?> dependencies);

	// TODO Consider if we really want to keep the following methods.
	boolean isValid();
	ValidityException getValidityException();
	
	AnnotatedElement getAnnotationBearer();

	@Override
	default int compareTo(final OpInfo that) {
		if (this.priority() < that.priority()) return 1;
		if (this.priority() > that.priority()) return -1;
		return MiscUtils.compare(this.implementationName(), that.implementationName());
	}
}
