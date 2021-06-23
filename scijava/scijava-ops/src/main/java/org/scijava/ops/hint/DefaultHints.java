package org.scijava.ops.hint;

import java.util.HashMap;
import java.util.Map;

import org.scijava.ops.Hints;
import org.scijava.ops.hint.BaseOpHints.Adaptation;
import org.scijava.ops.hint.BaseOpHints.Simplification;

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
	public Hints getCopy() {
		return new DefaultHints(new HashMap<>(getHints()));
	}

}
