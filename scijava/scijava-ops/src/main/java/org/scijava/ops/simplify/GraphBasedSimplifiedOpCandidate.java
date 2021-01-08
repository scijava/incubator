
package org.scijava.ops.simplify;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.scijava.Priority;
import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.conversionLoss.LossReporter;
import org.scijava.ops.core.Op;
import org.scijava.ops.matcher.OpCandidate;
import org.scijava.ops.matcher.OpMatchingException;
import org.scijava.ops.matcher.OpRef;
import org.scijava.struct.ItemIO;
import org.scijava.struct.StructInstance;
import org.scijava.types.Nil;
import org.scijava.types.Types;

public class GraphBasedSimplifiedOpCandidate extends OpCandidate {

	private final GraphBasedSimplifiedOpInfo info;
	private final GraphBasedSimplifiedOpRef ref;
	private final TypePair[] argPairings;
	private final TypePair outPairing;
	private final Map<TypePair, ChainCluster> pathways;

	public GraphBasedSimplifiedOpCandidate(OpEnvironment env, Logger log,
		GraphBasedSimplifiedOpRef ref, OpInfo info)
	{
		this(env, log, ref, new GraphBasedSimplifiedOpInfo(info, env));
	}

	public GraphBasedSimplifiedOpCandidate(OpEnvironment env, Logger log,
		GraphBasedSimplifiedOpRef ref, GraphBasedSimplifiedOpInfo info)
	{
		super(env, log, ref, info, new HashMap<>());
		setStatus(StatusCode.MATCH);
		this.info = info;
		this.ref = ref;
		this.argPairings = generatePairings(ref, info);
		this.outPairing = generateOutPairing(ref, info);
		if(!Types.isAssignable(Types.raw(info.opType()), Types.raw(ref.getType())))
				setStatus(StatusCode.CANNOT_CONVERT, "OpInfo and OpRef do not share an Op type");
		this.pathways = findPathways(ref, info);
		validateClusters();
	}

	/**
	 * If any clusters have no chains from the input type to the output type, then
	 * there does not exist a simplification pathway to mutate the ref type into
	 * the info type. Thus the arg types do not "match"
	 */
	private void validateClusters() {
		boolean impossiblePath = pathways.values().parallelStream() //
			.anyMatch(cluster -> cluster.getChains().size() == 0);
		if (impossiblePath) setStatus(StatusCode.ARG_TYPES_DO_NOT_MATCH);

	}


	public GraphBasedSimplifiedOpCandidate(OpEnvironment env, Logger log, OpRef ref,
		GraphBasedSimplifiedOpInfo info)
	{
		super(env, log, ref, info, new HashMap<>());
		throw new UnsupportedOperationException("TODO: support info-only simplification");
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) throws OpMatchingException
	{
		if (!getStatusCode().equals(StatusCode.MATCH)) {
			throw new OpMatchingException(
					"Status of candidate to create op from indicates a problem: " + getStatus());
		}
		
		Map<TypePair, MutatorChain> chains = new HashMap<>();
		pathways.forEach((pair, cluster) -> chains.put(pair, resolveCluster(cluster)));

		SimplificationMetadata metadata = new SimplificationMetadata(ref, info, argPairings, outPairing, chains, env());
		StructInstance<?> inst = opInfo().createOpInstance(dependencies, metadata);
		return inst;
	}



	@Override
	public GraphBasedSimplifiedOpInfo opInfo() {
		return info;
	}

	private static Map<TypePair, ChainCluster> findPathways(GraphBasedSimplifiedOpRef ref, GraphBasedSimplifiedOpInfo info) {
		if (ref.srcRef().getArgs().length != info.inputs().size())
			throw new IllegalArgumentException(
				"ref and info must have the same number of arguments!");
		int numInputs = ref.srcRef().getArgs().length;

		TypePair[] pairings = generatePairings(ref, info);

		Map<TypePair, ChainCluster> pathways = new HashMap<>();

		List<List<OpInfo>> infoFocusers = info.getFocusers(); 
		for (int i = 0; i < numInputs; i++) {
			TypePair pairing = pairings[i];
			if (pathways.keySet().contains(pairing)) continue;
			List<OpInfo> simplifiers = ref.simplifierSets().get(i);
			List<OpInfo> focusers = infoFocusers.get(i);
			ChainCluster cluster = ChainCluster.generateCluster(pairing, simplifiers, focusers);
			pathways.put(pairing, cluster);
		}

		TypePair outPairing = generateOutPairing(ref, info);
		if(!pathways.keySet().contains(outPairing)) {
			List<OpInfo> simplifiers = info.getOutputSimplifiers();
			List<OpInfo> focusers = ref.outputFocusers();
			ChainCluster cluster = ChainCluster.generateCluster(outPairing, simplifiers, focusers);
			pathways.put(outPairing, cluster);
		}

		return pathways;
	}

	private static TypePair[] generatePairings(GraphBasedSimplifiedOpRef ref,
		GraphBasedSimplifiedOpInfo info)
	{
		Type[] originalArgs = ref.srcRef().getArgs();
		Type[] originalParams = info.srcInfo().inputs().stream().map(m -> m.getType())
			.toArray(Type[]::new);
		TypePair[] pairings = Streams.zip(Arrays.stream(originalArgs), Arrays
			.stream(originalParams), (from, to) -> new TypePair(from, to)).toArray(
				TypePair[]::new);
		return pairings;
	}

	private static TypePair generateOutPairing(GraphBasedSimplifiedOpRef ref, GraphBasedSimplifiedOpInfo info) {
		return new TypePair(info.srcInfo().output().getType(), ref.srcRef().getOutType());
	}

	private static MutatorChain resolveCluster(ChainCluster cluster) {
		List<MutatorChain> chains = cluster.getChains();
		switch (chains.size()) {
			case 0:
				throw new IllegalArgumentException(
					"ChainCluster should have 1+ chains but has 0 chains!");
			case 1:
				return chains.get(0);
			default:
				// NB max returns Optional iff chains is empty, which it is not.
				return chains.stream().max(MutatorChain::compareTo).get();
		}

	}

	/**
	 * We define the priority of any {@link GraphBasedSimplifiedOpCandidate} as the sum of
	 * the following:
	 * <ul>
	 * <li>{@link Priority#VERY_LOW} to ensure that simplifications are not chosen
	 * over a direct match.
	 * <li>{@link OpInfo#priority} to ensure that a simplification of a
	 * higher-priority Op wins out over a simplification of a lower-priority Op,
	 * all else equal.
	 * <li>a penalty defined as a lossiness heuristic of this simplification. This
	 * penalty is the sum of:
	 * <ul>
	 * <li>the loss undertaken by converting each of the Op's inputs (as defined
	 * by an {@link ItemIO#INPUT} or {@link ItemIO#BOTH} annotation) from the ref
	 * type to the info type
	 * <li>the loss undertaken by converting each of the Op's outputs (as defined
	 * by an {@link ItemIO#OUTPUT} or {@link ItemIO#BOTH} annotation) from the
	 * info type to the ref type
	 * </ul>
	 * </ul>
	 */
	@Override
	public double priority() {
		// BASE PRIORITY
		double base = Priority.VERY_LOW;

		// ORIGINAL PRIORITY
		double originalPriority = info.srcInfo().priority();

		// PENALTY
		double penalty = 0;

		for (int i = 0; i < argPairings.length; i++) {
			penalty += determineLoss(Nil.of(argPairings[i].getA()), Nil.of(argPairings[i].getB()));
		}

		// TODO: only calculate the loss once
		penalty += determineLoss(Nil.of(outPairing.getA()), Nil.of(outPairing.getB()));

		// PRIORITY = BASE + ORIGINAL - PENALTY
		return base + originalPriority - penalty;
	}

	/**
	 * Calls a {@code lossReporter} {@link Op} to determine the <b>worst-case</b>
	 * loss from a {@code T} to a {@code R}. If no {@code lossReporter} exists for
	 * such a conversion, we assume infinite loss.
	 * 
	 * @param <T> -the generic type we are converting from.
	 * @param <R> - generic type we are converting to.
	 * @param from - a {@link Nil} describing the type we are converting from
	 * @param to - a {@link Nil} describing the type we are converting to
	 * @return - a {@code double} describing the magnitude of the <worst-case>
	 *         loss in a conversion from an instance of {@code T} to an instance
	 *         of {@code R}
	 */
	private <T, R> double determineLoss(Nil<T> from, Nil<R> to) {
		Type specialType = Types.parameterize(LossReporter.class, new Type[] { from
			.getType(), to.getType() });
		@SuppressWarnings("unchecked")
		Nil<LossReporter<T, R>> specialTypeNil = (Nil<LossReporter<T, R>>) Nil.of(
			specialType);
		try {
			Type nilFromType = Types.parameterize(Nil.class, new Type[] {from.getType()});
			Type nilToType = Types.parameterize(Nil.class, new Type[] {to.getType()});
			LossReporter<T, R> op = env().op("lossReporter", specialTypeNil, new Nil[] {
				Nil.of(nilFromType), Nil.of(nilToType) }, Nil.of(Double.class));
			return op.apply(from, to);
		} catch(IllegalArgumentException e) {
			if (e.getCause() instanceof OpMatchingException)
				return Double.POSITIVE_INFINITY;
			throw e;
		}
	}

}

class ChainCluster {

	private final List<MutatorChain> chains;
	private final TypePair typeConversion;

	public ChainCluster(TypePair typeConversion) {
		this.chains = new ArrayList<>();
		this.typeConversion = typeConversion;
	}
	
	public static ChainCluster generateCluster(TypePair pairing, List<OpInfo> simplifiers, List<OpInfo> focusers) {
		ChainCluster cluster = new ChainCluster(pairing);
		List<List<OpInfo>> chains = Lists.cartesianProduct(simplifiers, focusers);

		for(List<OpInfo> chainList : chains) {
				OpInfo simplifier = chainList.get(0);
				OpInfo focuser = chainList.get(1);
				MutatorChain chain = new MutatorChain(simplifier, focuser, pairing); 
				if (chain.isValid()) cluster.addChain(chain);
		}
		return cluster;
}

	public boolean addChain(MutatorChain chain) {
		return chains.add(chain);
	}

	public List<MutatorChain> getChains() {
		return chains;
	}
}

