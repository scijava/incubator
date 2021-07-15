
package org.scijava.ops.hints.impl;

import java.util.HashMap;
import java.util.Map;

import org.scijava.ops.hints.Hints;
import org.scijava.ops.hints.BaseOpHints.Simplification;

public class SimplificationHints extends AbstractHints {

	private SimplificationHints(Map<String, String> map) {
		super(map);
		set(Simplification.IN_PROGRESS);
	}

	public static SimplificationHints generateHints(Hints hints) {
		// collect all old hints that are not Adaptable
		Map<String, String> map = new HashMap<>();
		hints.all().entrySet().parallelStream().filter(e -> e
			.getKey() != Simplification.prefix).forEach(e -> map.put(e.getKey(), e
				.getValue()));

		// add Simplifiable.NO
		SimplificationHints newHints = new SimplificationHints(map);

		return newHints;
	}

	@Override
	public String set(String hint) {
		if (hint.equals(Simplification.ALLOWED)) throw new IllegalArgumentException(
			"We cannot allow simplification during simplification; this would cause a recursive loop!");
		return super.set(hint);
	}

	@Override
	public Hints copy() {
		return SimplificationHints.generateHints(this);
	}

}
