
package org.scijava.ops.hints.impl;

import java.util.HashMap;
import java.util.Map;

import org.scijava.ops.hints.Hints;
import org.scijava.ops.hints.BaseOpHints.Adaptation;

/**
 * A set of {@link Hints} governing Adaptation procedures.
 * @author G
 *
 */
public class AdaptationHints extends AbstractHints {

	private AdaptationHints(Map<String, String> map) {
		super(map);
		set(Adaptation.IN_PROGRESS);
	}

	public static AdaptationHints generateHints(Hints hints) {
		// collect all old hints that are not Adaptable
		Map<String, String> map = new HashMap<>();
		hints.all().entrySet().parallelStream().filter(e -> e
			.getKey() != Adaptation.prefix).forEach(e -> map.put(e.getKey(), e
				.getValue()));

		// add Adaptation.NO
		AdaptationHints newHints = new AdaptationHints(map);

		return newHints;
	}

	@Override
	public String set(String hint) {
		return super.set(hint);
	}

	@Override
	public Hints copy() {
		return AdaptationHints.generateHints(this);
	}

}
