package org.scijava.ops.matcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.List;

import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.simplify.SimplificationUtils;
import org.scijava.param.ValidityException;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;


public class GraphBasedSimplifiedOpInfo implements OpInfo {

	private final OpInfo srcInfo;
	private final List<List<OpInfo>> focuserSets;
	private final List<OpInfo> outputSimplifiers;

	public GraphBasedSimplifiedOpInfo(OpInfo info, OpEnvironment env) {
		this.srcInfo = info;
		Type[] args = OpUtils.inputs(srcInfo.struct()).stream() //
				.map(m -> m.getType()) //
				.toArray(Type[]::new);
		this.focuserSets = SimplificationUtils.focusArgs(env, args);
		Type outType = info.output().getType();
		this.outputSimplifiers = SimplificationUtils.getSimplifiers(env, outType);
	}

	public List<List<OpInfo>> getFocusers(){
		return focuserSets;
	}

	public List<OpInfo> getOutputSimplifiers(){
		return outputSimplifiers;
	}

	public OpInfo srcInfo() {
		return srcInfo;
	}

	@Override
	public Type opType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct struct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSimplifiable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double priority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String implementationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidityException getValidityException() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnnotatedElement getAnnotationBearer() {
		// TODO Auto-generated method stub
		return null;
	}

}
