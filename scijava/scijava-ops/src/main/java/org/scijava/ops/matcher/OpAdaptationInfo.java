package org.scijava.ops.matcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

import org.scijava.ops.OpDependencyMember;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;

/**
 * {@link OpInfo} for ops that have been adapted to some other Op type.
 * 
 * @author Gabriel Selzer
 * @see OpInfo
 */
public class OpAdaptationInfo implements OpInfo {

	private OpCandidate srcCandidate;
	private Type type;
	private Function<Object, Object> adaptor;

	private Struct struct;
	private ValidityException validityException;

	public OpAdaptationInfo(OpCandidate srcCandidate, Type type, Function<Object, Object> adaptor) {
		this.srcCandidate = srcCandidate;
		this.type = type;
		this.adaptor = adaptor;

		// NOTE: since the source Op has already been shown to be valid, there is not
		// much for us to do here.
		try {
			struct = ParameterStructs.structOf(srcInfo, type);
			OpUtils.checkHasSingleOutput(struct);
		} catch (ValidityException e) {
			validityException = e;
		}
	}
	
	@Override
	public List<OpDependencyMember<?>> dependencies() {
		return srcCandidate.opInfo().dependencies();
	}

	@Override
	public Type opType() {
		return type;
	}

	@Override
	public Struct struct() {
		return struct;
	}

	// we want the original op to have priority over this one.
	@Override
	public double priority() {
		return srcCandidate.priority() - 1;
	}

	@Override
	public String implementationName() {
		return srcCandidate.opInfo().implementationName() + " adapted to " + type
			.toString();
	}

	/**
	 * @param dependencies - the list of depencies <b>for the source Op</b>
	 */
	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		final Object op = srcCandidate.createOpInstance(dependencies).object();
		final Object adaptedOp = adaptor.apply(op);
		return struct().createInstance(adaptedOp);
	}

	@Override
	public boolean isValid() {
		return validityException == null;
	}

	@Override
	public ValidityException getValidityException() {
		return validityException;
	}

	@Override
	public AnnotatedElement getAnnotationBearer() {
		return srcInfo.getAnnotationBearer();
	}

	/**
	 * TODO: consider whether we could simplify {@link OpAdaptationInfo}s.
	 * Currently, the system doesn't even store them outside of caching, so they
	 * cannot be simplified. But maybe it would be useful.
	 */
	@Override
	public boolean isSimplifiable() {
		return false;
	}

}
