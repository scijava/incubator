package org.scijava.ops.matcher;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.scijava.ops.OpInfo;
import org.scijava.ops.simplify.SimplificationUtils;
import org.scijava.util.Types;

public class GraphBasedSimplifiedOpRef extends OpRef{
	
	OpRef srcRef;

	public GraphBasedSimplifiedOpRef(String name, Type type, Type outType,
		Type[] args)
	{
		super(name, type, outType, args);
		// TODO Auto-generated constructor stub
	}
	List<OpInfo>[] simplifierSets;
	List<OpInfo> focusers;
	
	// TODO: only works on inputs
	public boolean matchExists(GraphBasedSimplifiedOpInfo info) {

		if(srcRef.getArgs().length != info.inputs().size())
			throw new IllegalArgumentException("ref and info must have the same number of arguments!");
		int numInputs = srcRef.getArgs().length;

		Type[] originalArgs = srcRef.getArgs();
		Type[] originalParams = info.srcInfo.inputs().stream().map(m -> m.getType()).toArray(Type[]::new);

		@SuppressWarnings("unchecked")
		List<MutatorChain>[] pathways = new List[numInputs];

		for(int i = 0; i < numInputs; i++) {
			pathways[i] = new ArrayList<>();

			for(OpInfo simplifier: simplifierSets[i]) {
				for(OpInfo focuser: info.getFocusers(i)) {
					Type simplifierInput = simplifier.inputs().stream().filter(m -> !m.isOutput()).findFirst().get().getType();
					Type simplifierOutput = simplifier.output().getType();
					Type simpleType = SimplificationUtils.resolveMutatorTypeArgs(originalArgs[i], simplifierInput, simplifierOutput);
					Type focuserOutput = focuser.output().getType();
					Type focuserInput = focuser.inputs().stream().filter(m -> !m.isOutput()).findFirst().get().getType();
					Type unfocusedType = SimplificationUtils.resolveMutatorTypeArgs(originalParams[i], focuserOutput, focuserInput);

					if(Types.isAssignable(simpleType, unfocusedType))
						pathways[i].add(new MutatorChain(simplifier, focuser));
				}
			}
			
			if(pathways[i].size() == 0) return false;
		}

		return true;
	}

}

class MutatorChain {
	private final OpInfo simplifier;
	private final OpInfo focuser;
	
	public MutatorChain(OpInfo simplifier, OpInfo focuser) {
		this.simplifier = simplifier;
		this.focuser = focuser;
	}

	public OpInfo simplifier() {
		return simplifier;
	}

	public OpInfo focuser() {
		return focuser;
	}
	
}
