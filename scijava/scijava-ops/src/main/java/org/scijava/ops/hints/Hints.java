
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

	default String set(String hint) {
		int delimIndex = hint.indexOf(hintDelimiter());
		String hintType = hint.substring(0, delimIndex);
		String setting = hint.substring(delimIndex + 1);
		return set(hintType, setting);
	}

	public String set(String hintType, Object setting);

	public String get(String hintType);

	public boolean contains(String hint);

	public boolean containsType(String hintType);

	public Map<String, String> all();

	public Hints copy();

	char hintDelimiter();

}
