
package org.scijava.ops.simplify;

import com.google.common.collect.Streams;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.function.Computers;
import org.scijava.ops.matcher.OpRef;

public class SimplifiedOpRef extends OpRef {

	private final OpRef srcRef;
	private final List<List<OpInfo>> simplifierSets;
	private final List<OpInfo> outputFocusers;
	private final Optional<Computers.Arity1<?, ?>> copyOp;

	public SimplifiedOpRef(String name, Type type, Type outType,
		Type[] args)
	{
		super(name, type, outType, args);
		throw new UnsupportedOperationException("Simplified OpRef requires original OpRef!");
	}

	public SimplifiedOpRef(OpRef ref, OpEnvironment env) {
		// TODO: this is probably incorrect
		super(ref.getName(), ref.getType(), ref.getOutType(), ref.getArgs());
		this.srcRef = ref;
		this.simplifierSets = SimplificationUtils.simplifyArgs(env, ref.getArgs());
		this.outputFocusers = SimplificationUtils.getFocusers(env, ref.getOutType());
		this.copyOp = Optional.empty();
	}

	public SimplifiedOpRef(OpRef ref, OpEnvironment env, Computers.Arity1<?, ?> copyOp) {
		// TODO: this is probably incorrect
		super(ref.getName(), ref.getType(), ref.getOutType(), ref.getArgs());
		this.srcRef = ref;
		this.simplifierSets = SimplificationUtils.simplifyArgs(env, ref.getArgs());
		this.outputFocusers = SimplificationUtils.getFocusers(env, ref.getOutType());
		this.copyOp = Optional.of(copyOp);
	}

	public boolean matchExists(SimplifiedOpInfo info) {

		if (srcRef.getArgs().length != info.inputs().size())
			throw new IllegalArgumentException(
				"ref and info must have the same number of arguments!");
		int numInputs = srcRef.getArgs().length;

		Type[] originalArgs = srcRef.getArgs();
		Type[] originalParams = info.srcInfo().inputs().stream().map(m -> m.getType())
			.toArray(Type[]::new);
		TypePair[] pairings = Streams.zip(Arrays.stream(originalArgs), Arrays
			.stream(originalParams), (from, to) -> new TypePair(from, to)).toArray(
				TypePair[]::new);

		Map<TypePair, ChainCluster> pathways = new HashMap<>();

		List<List<OpInfo>> infoFocusers = info.getFocusers(); 
		for (int i = 0; i < numInputs; i++) {
			TypePair pairing = pairings[i];
			if (pathways.keySet().contains(pairing)) continue;
			List<OpInfo> simplifiers = simplifierSets.get(i);
			List<OpInfo> focusers = infoFocusers.get(i);
			ChainCluster cluster = ChainCluster.generateCluster(pairing, simplifiers, focusers);
			if (cluster.getChains().size() == 0) return false;
			pathways.put(pairing, cluster);
		}

		TypePair outPairing = new TypePair(info.srcInfo().output().getType(), srcRef.getOutType());
		if(!pathways.keySet().contains(outPairing)) {
			List<OpInfo> simplifiers = info.getOutputSimplifiers();
			List<OpInfo> focusers = outputFocusers;
			ChainCluster cluster = ChainCluster.generateCluster(outPairing, simplifiers, focusers);
			if (cluster.getChains().size() == 0) return false;
			pathways.put(outPairing, cluster);
		}

		return true;
	}

	public OpRef srcRef() {
		return srcRef;
	}

	public List<List<OpInfo>> simplifierSets() {
		return simplifierSets;
	}

	public List<OpInfo> outputFocusers() {
		return outputFocusers;
	}

	public Optional<Computers.Arity1<?, ?>> copyOp() {
		return copyOp;
	}

}

//class TypePair {
//
//	private final Type a;
//	private final Type b;
//
//	public TypePair(Type a, Type b) {
//		this.a = a;
//		this.b = b;
//	}
//
//	public Type getA() {
//		return a;
//	}
//
//	public Type getB() {
//		return b;
//	}
//
//	@Override
//	public boolean equals(Object that) {
//		if (!(that instanceof TypePair)) return false;
//		TypePair thatPair = (TypePair) that;
//		return a.equals(thatPair.getA()) && b.equals(thatPair.getB());
//	}
//
//	@Override
//	public int hashCode() {
//		return Arrays.hashCode(new Type[] { a, b });
//	}
//}
//
//class ChainCluster {
//
//	private final List<MutatorChain> chains;
//	private final TypePair typeConversion;
//
//	public ChainCluster(TypePair typeConversion) {
//		this.chains = new ArrayList<>();
//		this.typeConversion = typeConversion;
//	}
//	
//	public static ChainCluster generateCluster(TypePair pairing, List<OpInfo> simplifiers, List<OpInfo> focusers) {
//		ChainCluster cluster = new ChainCluster(pairing);
//		List<List<OpInfo>> chains = Lists.cartesianProduct(simplifiers, focusers);
//
//		for(List<OpInfo> chain : chains) {
//				OpInfo simplifier = chain.get(0);
//				Type simplifierInput = simplifier.inputs().stream().filter(m -> !m
//					.isOutput()).findFirst().get().getType();
//				Type simplifierOutput = simplifier.output().getType();
//				Type simpleType = SimplificationUtils.resolveMutatorTypeArgs(
//					pairing.getA(), simplifierInput, simplifierOutput);
//				OpInfo focuser = chain.get(1);
//				Type focuserOutput = focuser.output().getType();
//				Type focuserInput = focuser.inputs().stream().filter(m -> !m
//					.isOutput()).findFirst().get().getType();
//				Type unfocusedType = SimplificationUtils.resolveMutatorTypeArgs(
//					pairing.getB(), focuserOutput, focuserInput);
//
//				if (Types.isAssignable(simpleType, unfocusedType)) cluster.addChain(
//					new MutatorChain(simplifier, focuser));
//		}
//		return cluster;
//}
//
//	public boolean addChain(MutatorChain chain) {
//		return chains.add(chain);
//	}
//
//	public Type originalArg() {
//		return typeConversion.getA();
//	}
//
//	public Type originalParam() {
//		return typeConversion.getB();
//	}
//
//	public List<MutatorChain> getChains() {
//		return chains;
//	}
//}
//
//class MutatorChain {
//
//	private final OpInfo simplifier;
//	private final OpInfo focuser;
//
//	public MutatorChain(OpInfo simplifier,
//		OpInfo focuser)
//	{
//		this.simplifier = simplifier;
//		this.focuser = focuser;
//	}
//
//	public OpInfo simplifier() {
//		return simplifier;
//	}
//
//	public OpInfo focuser() {
//		return focuser;
//	}
//
//}
