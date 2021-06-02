package org.scijava.ops.reduce;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import org.scijava.ops.OpInfo;
import org.scijava.param.Optional;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;


public class ReducedOpInfo implements OpInfo {

	private final OpInfo srcInfo;
	private final Type reducedOpType;
	private final int paramsReduced;

	private Struct struct;
	private ValidityException validityException;

	public ReducedOpInfo(OpInfo src, Type reducedOpType, int paramsReduced) {
		this.srcInfo = src;
		this.reducedOpType = reducedOpType;
		this.paramsReduced = paramsReduced;

		try {
			this.struct = ParameterStructs.structOf(srcInfo, reducedOpType);
		}
		catch (ValidityException e) {
			validityException = e;
		}
	}

	@Override
	public Type opType() {
		return reducedOpType;
	}

	@Override
	public Struct struct() {
		return struct;
	}

	@Override
	public boolean isSimplifiable() {
		return true;
	}

	@Override
	public double priority() {
		return srcInfo.priority();
	}

	@Override
	public String implementationName() {
		// TODO: improve this name
		return srcInfo.implementationName() + "Reduction" + paramsReduced; 
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		final Object op = srcInfo.createOpInstance(dependencies).object();
//		if (!Modifier.isPublic(srcInfo.opType().getClass().getModifiers())) {
//			throw new IllegalArgumentException("Op " + srcInfo.opType() +
//				" is not public; only Ops backed by public classes can be reduced!");
//		}
		try {
			Object reducedOp = ReductionUtils.javassistOp(op, this);
			return struct().createInstance(reducedOp);
		}
		catch (Throwable ex) {
			throw new IllegalArgumentException(
				"Failed to invoke reduction of Op: \n" + srcInfo +
					"\nProvided Op dependencies were: " + Objects.toString(dependencies),
				ex);
		}
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
	 * NB since this {@link OpInfo} has already been reduced, we ignore any
	 * remaining {@link Optional} parameters
	 */
	@Override
	public boolean isOptional(Member<?> m) {
		return false;
	}

	public OpInfo srcInfo() {
		return srcInfo;
	}

	public int paramsReduced() {
		return paramsReduced;
	}

}
