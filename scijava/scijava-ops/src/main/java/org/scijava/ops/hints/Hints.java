
package org.scijava.ops.hints;

import java.util.Map;

/**
 * A basic interface for storing and accessing Hints. Hints should always come
 * in the form of
 * <p>
 * {@code hintType<delimiter>setting}
 * <p>
 * For example, suppose you have a hintType {@code Precision}, with settings
 * {@code lossless} and {@code lossy}. Suppose also that the {@code delimiter}
 * is ".". A possible Hint could be :
 * <p>
 * {@code Precision.lossless}
 * <p>
 * 
 * @author Gabriel Selzer
 */
public interface Hints {

	default String setHint(String hint) {
		int delimIndex = hint.indexOf(getDelimiter());
		String hintType = hint.substring(0, delimIndex);
		String setting = hint.substring(delimIndex + 1);
		return setHint(hintType, setting);
	}

	public String setHint(String hintType, Object setting);

	public String getHint(String hintType);

	public boolean containsHint(String hint);

	public boolean containsHintType(String hintType);

	public Map<String, String> getHints();

	public Hints getCopy();

	char getDelimiter();

}
