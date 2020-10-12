package org.scijava.ops.matcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.simplify.Identity;
import org.scijava.ops.simplify.Simplifier;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.types.Types;


public class SimplifiedOpInfo implements OpInfo {
	
	final OpInfo srcInfo;
	final List<Simplifier<?, ?>> simplifiers;
	final ParameterizedType simplifiedType;
	
	private Struct struct;
	private ValidityException validityException;

	public SimplifiedOpInfo(OpInfo info, List<Simplifier<?, ?>> simplification) {
		this.srcInfo = info;
		this.simplifiers = simplification;
		this.simplifiedType = generateSimplifiedType(srcInfo);
		
		// NOTE: since the source Op has already been shown to be valid, there is
		// not much for us to do here.
		try {
			struct = ParameterStructs.structOf(srcInfo, simplifiedType);
			OpUtils.checkHasSingleOutput(struct);
		}
		catch (ValidityException e) {
			validityException = e;
		}
	}
	
	public boolean isIdentical() {
		return simplifiers.stream().allMatch(s -> s instanceof Identity);
	}

	// TODO: We assume that this is a Function of some type.
	// We also assume that simplifiers exist for all inputs ONLY.
	// FIXME: generalize
	private ParameterizedType generateSimplifiedType(OpInfo originalInfo)
	{
		List<Type> simplifiedTypes = simplifiers.stream().map(s -> s.simpleType()).collect(Collectors.toList());
		if (!(originalInfo.opType() instanceof ParameterizedType))
			throw new UnsupportedOperationException(
				"I am not smart enough to handle this yet.");
		simplifiedTypes.add(srcInfo.output().getType()); 
		Class<?> rawType = Types.raw(originalInfo.opType());
		return Types.parameterize(rawType, simplifiedTypes.toArray(Type[]::new));
	}

	@Override
	public Type opType() {
		return simplifiedType;
	}

	@Override
	public Struct struct() {
		return null;
	}

	@Override
	public double priority() {
		// The original info should hold priority over this one
		return srcInfo.priority() - 1;
	}

	@Override
	public String implementationName() {
		return srcInfo.implementationName();
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		// FIXME
		return true;
	}

	@Override
	public ValidityException getValidityException() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnnotatedElement getAnnotationBearer() {
		return srcInfo.getAnnotationBearer();
	}

}
