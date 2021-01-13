package org.scijava.ops.simplify;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.param.ValidityException;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.util.MiscUtils;


public class SimplifiedOpInfo implements OpInfo {

	private final OpInfo srcInfo;
	private final List<List<OpInfo>> focuserSets;
	private final List<OpInfo> outputSimplifiers;

	public SimplifiedOpInfo(OpInfo info, OpEnvironment env) {
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
		return srcInfo.opType();
	}

	@Override
	public Struct struct() {
		// TODO Auto-generated method stub
		return srcInfo.struct();
	}

	@Override
	public double priority() {
		return srcInfo.priority() - 1;
	}

	@Override
	public String implementationName() {
		return srcInfo.implementationName();
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		throw new UnsupportedOperationException(
			"Cannot create an instance without knowing the Simplifier/Focuser Functions needed to mutate the arugments!");
	}

	@Override
	public boolean isValid() {
		return srcInfo.isValid();
	}

	@Override
	public ValidityException getValidityException() {
		return srcInfo.getValidityException();
	}

	@Override
	public AnnotatedElement getAnnotationBearer() {
		return srcInfo.getAnnotationBearer();
	}

	/**
	 * Creates a <b>simplified</b> version of the original Op, whose parameter
	 * types are dictated by the {@code focusedType}s of this info's
	 * {@link Simplifier}s. The resulting Op will use {@code simplifier}s to
	 * simplify the inputs, and then will use this info's {@code focuser}s to
	 * focus the simplified inputs into types suitable for the original Op.
	 * 
	 * @param dependencies - this Op's dependencies
	 * @param metadata - data required to correctly write the simplified Op
	 * @see #createOpInstance(List) - used when there are no associated
	 *      {@code refSimplifier}s.
	 */
	public StructInstance<?> createOpInstance(List<?> dependencies,
		SimplificationMetadata metadata)
	{
		final Object op = srcInfo.createOpInstance(dependencies).object();
		try {
			Object simpleOp = SimplificationUtils.javassistOp(op, metadata);
			return struct().createInstance(simpleOp);
		}
		catch (Throwable ex) {
			throw new IllegalArgumentException(
				"Failed to invoke simplification of Op: \n" + srcInfo +
					"\nProvided Op dependencies were: " + Objects.toString(dependencies),
				ex);
		}
	}

	@Override
	public boolean isSimplifiable() {
		return false;
	}

	@Override
	public String toString() {
		return OpUtils.opString(this);
	}
	
	@Override
	public int compareTo(final OpInfo that) {
		// compare priorities
		if (this.priority() < that.priority()) return 1;
		if (this.priority() > that.priority()) return -1;

		// compare implementation names 
		int implNameDiff = MiscUtils.compare(this.implementationName(), that.implementationName());
		if(implNameDiff != 0) return implNameDiff; 

		// compare structs if the OpInfos are "sibling" SimplifiedOpInfos
		if(that instanceof SimplifiedOpInfo) return compareToSimplifiedInfo((SimplifiedOpInfo) that);

		return 0;
	}

	private int compareToSimplifiedInfo(SimplifiedOpInfo that) {
		// Compare structs
		List<Member<?>> theseMembers = new ArrayList<>();
		this.struct().forEach(theseMembers::add);
		List<Member<?>> thoseMembers = new ArrayList<>();
		that.struct().forEach(thoseMembers::add);
		return theseMembers.hashCode() - thoseMembers.hashCode();
	}



}
