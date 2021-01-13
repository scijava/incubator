package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.param.ParameterStructs;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;

public class AdaptedOpCandidate extends OpCandidate{

	private final OpCandidate srcCandidate;
	private final Type type;
	private final Function<Object, Object> adaptor;

	private final Struct struct;

	public AdaptedOpCandidate(OpEnvironment env, Logger log, OpRef ref,
		OpInfo info, Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		super(env, log, ref, info, typeVarAssigns);
		throw new UnsupportedOperationException(
			"AdaptedOpCandidate cannot be instantiated without an adaptor Function! See OpCandidate if no adaptor is needed!");
	}

	public AdaptedOpCandidate(OpCandidate srcCandidate, Logger log, OpRef ref, Type type, Function<Object, Object> adaptor) {
		super(srcCandidate.env(), log, ref, srcCandidate.opInfo(), srcCandidate.typeVarAssigns());
		this.srcCandidate = srcCandidate;
		this.type = type;
		this.adaptor = adaptor;
		this.struct = ParameterStructs.structOf(srcCa, newType)
	}

	@Override
	public Struct struct() {
		return struct;
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) throws OpMatchingException {
		final Object op = srcCandidate.createOp(dependencies);
		final Object adaptedOp = adaptor.apply(op);
		return struct().createInstance(adaptedOp);
	}

}
