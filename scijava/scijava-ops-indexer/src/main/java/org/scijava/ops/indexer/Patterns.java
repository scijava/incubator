package org.scijava.ops.indexer;

import java.util.regex.Pattern;

/**
 * A set of static {@link Pattern}s useful for Op parsing.
 *
 * @author Gabriel Selzer
 */
public class Patterns {

    /**
     * Given a Javadoc comment, separates the comment by the Javadoc tags (e.g. @author, @param, etc.).
     * Therefore, each string returned by blockSeparator.split(String) will be:
     * <ul>
     *   <li>A string beginning with and including a javadoc tag (e.g. @author, @param, etc.)</li>
     *   <li>The description of the code block - this happens when there's a description before the tags start</li>
     * </ul>
     */
    public static final Pattern
        blockSeparator = Pattern.compile("^\\s*(?=@\\S)", Pattern.MULTILINE);
    /**
     * Given a string, splits the String by whitespace UNLESS the whitespace is inside a set of single quotes.
     * Useful for parsing tags, especially implNote tags.
     */
    public static final Pattern tagElementSeparator = Pattern.compile("\\s*,*\\s+(?=(?:[^']*'[^']*')*[^']*$)");

    private Patterns() {
        throw new AssertionError("not instantiable");
    }

}
