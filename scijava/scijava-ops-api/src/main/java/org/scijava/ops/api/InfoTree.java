
package org.scijava.ops.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A data structure wrangling a hierarchy of {@link OpInfo}s, created for every
 * Op match and called upon to instantiate any number of {@link OpInstance}s.
 * 
 * This {@link InfoTree} contains:
 * <ol>
 * <li>An {@link OpInfo} describing the Op</li>
 * <li>A {@link List} of {@link OpInfo}s that should be mapped to the Op
 * dependencies of this Op, <b>in the order that they are presented by the
 * Op</b>.</li>
 * </ol>
 * <p>
 * This {@link InfoTree} is also able to generate a {@link String} uniquely
 * identifying itself.
 * </p>
 * <b>NOTE</b>: This class is <b>not</b> responsible for generating
 * {@link RichOp}s.
 *
 * @author Gabriel Selzer
 * @see RichOp#infoChain()
 */
public class InfoTree {

	public static final Character DEP_START_DELIM = '{';
	public static final Character DEP_END_DELIM = '}';

	private final List<InfoTree> dependencies;
	private String id;

	private final OpInfo info;

	public InfoTree(OpInfo info) {
		this.info = info;
		this.dependencies = Collections.emptyList();
	}

	public InfoTree(OpInfo info, List<InfoTree> dependencies) {
		this.info = info;
		this.dependencies = new ArrayList<>(dependencies);
	}

	public List<InfoTree> dependencies() {
		return dependencies;
	}

	@Override
	public boolean equals(Object obj) {
		// Since the id is unique, we can check equality on that
		if (!(obj instanceof InfoTree)) return false;
		return signature().equals(((InfoTree) obj).signature());
	}

	@Override
	public int hashCode() {
		// Since the id is unique, we can hash on that
		return signature().hashCode();
	}

	/**
	 * Builds a String uniquely identifying this tree of Ops. As each
	 * {@link OpInfo#id()} should be unique to <b>that</b> {@link OpInfo}, we can
	 * use those to uniquely identify a hierarchy of {@link OpInfo}s, and thus by
	 * extension a unique {@link InfoTree}.
	 *
	 * @return a {@link String} uniquely identifying this {@link InfoTree}
	 */
	public String signature() {
		if (id == null) generateSignature();
		return id;
	}

	public OpInfo info() {
		return info;
	}

	public OpInstance<?> newInstance() {
		return OpInstance.of(generateOp(), this, info.opType());
	}

	public OpInstance<?> newInstance(Type opType) {
		return OpInstance.of(generateOp(), this, opType);
	}

	protected Object generateOp() {
		List<Object> dependencyInstances = dependencies().stream() //
			.map(d -> d.newInstance().op()) //
			.collect(Collectors.toList());
		Object op = info().createOpInstance(dependencyInstances).object();
		return op;
	}

	private synchronized void generateSignature() {
		if (id != null) return;
		String s = info().id();
		s = s.concat(String.valueOf(DEP_START_DELIM));
		for (InfoTree dependency : dependencies()) {
			s = s.concat(dependency.signature());
		}
		id = s.concat(String.valueOf(DEP_END_DELIM));
	}

}