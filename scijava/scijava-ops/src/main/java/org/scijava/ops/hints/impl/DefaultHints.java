package org.scijava.ops.hints.impl;

import java.util.HashMap;
import java.util.Map;

import org.scijava.ops.hints.Hints;
import org.scijava.ops.hints.BaseOpHints.Adaptation;
import org.scijava.ops.hints.BaseOpHints.Simplification;

/**
 * Default Implementation of {@link Hints}
 *
 * @author Gabriel Selzer
 */
public class DefaultHints extends AbstractHints {

	public DefaultHints() {
		super(new String[] {Simplification.ALLOWED, Adaptation.ALLOWED});
	}

	private DefaultHints(Map<String, String> hints) {
		super(hints);
	}

	@Override
	public Hints copy() {
		return new DefaultHints(new HashMap<>(all()));
	}

}
