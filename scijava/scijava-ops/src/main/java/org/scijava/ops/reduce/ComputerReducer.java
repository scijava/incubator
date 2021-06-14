package org.scijava.ops.reduce;

import java.lang.reflect.Type;

import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.function.Computers;
import org.scijava.param.ParameterStructs;
import org.scijava.plugin.Plugin;
import org.scijava.util.Types;

@Plugin(type = InfoReducer.class)
public class ComputerReducer implements InfoReducer{

	@Override
	public boolean canReduce(OpInfo info) {
		return Computers.isComputer(ParameterStructs.findFunctionalInterface(Types.raw(info.opType())));
	}

	@Override
	public ReducedOpInfo reduce(OpInfo info, int numReductions) {
		Type opType = info.opType();
		Class<?> rawType = ParameterStructs.findFunctionalInterface(Types.raw(opType));
		Integer originalArity = Computers.ALL_COMPUTERS.get(rawType);
		Integer reducedArity = originalArity - numReductions;
		Class<?> reducedRawType = Computers.ALL_COMPUTERS.inverse().get(reducedArity);
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
