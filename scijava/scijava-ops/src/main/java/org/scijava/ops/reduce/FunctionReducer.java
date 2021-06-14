package org.scijava.ops.reduce;

import java.lang.reflect.Type;

import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.function.Functions;
import org.scijava.param.ParameterStructs;
import org.scijava.plugin.Plugin;
import org.scijava.util.Types;

@Plugin(type = InfoReducer.class)
public class FunctionReducer implements InfoReducer{

	@Override
	public boolean canReduce(OpInfo info) {
		return Functions.isFunction(ParameterStructs.findFunctionalInterface(Types.raw(info.opType())));
	}

	@Override
	public ReducedOpInfo reduce(OpInfo info, int numReductions) {
		Type opType = info.opType();
		Class<?> rawType = ParameterStructs.findFunctionalInterface(Types.raw(opType));
		Integer originalArity = Functions.ALL_FUNCTIONS.inverse().get(rawType);
		Integer reducedArity = originalArity - numReductions;
		Class<?> reducedRawType = Functions.ALL_FUNCTIONS.get(reducedArity);
		Type[] inputTypes = OpUtils.inputTypes(info.struct());
		Type outputType = OpUtils.outputType(info.struct());
		Type[] newTypes = new Type[reducedArity + 1];
		for(int i = 0; i < reducedArity; i++) {
			newTypes[i] = inputTypes[i];
		}
		newTypes[newTypes.length - 1] = outputType;
		Type reducedOpType = Types.parameterize(reducedRawType, newTypes);
		return new ReducedOpInfo(info, reducedOpType, originalArity - reducedArity);
	}

}
