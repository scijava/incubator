package org.scijava.ops.matcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

import org.scijava.ops.OpInfo;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;


public class ReducedOpInfo implements OpInfo {

	private final OpInfo srcInfo;
	private final Type reducedOpType;
	private final double priority;

	private Struct struct;
	private ValidityException validityException;

	public ReducedOpInfo(OpInfo src, Type reducedOpType) {
		this.srcInfo = src;
		this.reducedOpType = reducedOpType;

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
		return srcInfo.implementationName() + " as " + reducedOpType.toString();
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		// TODO Auto-generated method stub
		return null;
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
	public boolean hasOptionalParameters() {
		return false;
	}

	/**
	 * NB since this {@link OpInfo} has already been reduced, we ignore any
	 * remaining {@link Optional} parameters
	 */
	@Override
	public Parameter[] optionalParameters() {
		return new Parameter[0];
	}

}
