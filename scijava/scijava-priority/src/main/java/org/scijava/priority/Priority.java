/*
 * #%L
 * SciJava Priority: A lightweight priority hierarchy
 * %%
 * Copyright (C) 2022 - 2023 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.priority;

/**
 * Constants for specifying an item's priority.
 * 
 * @author Johannes Schindelin
 * @author Curtis Rueden
 * @see org.scijava.priority.Prioritized#getPriority()
 */
public final class Priority {

	private Priority() {
		// prevent instantiation of utility class
	}

	/**
	 * Priority for items that must be sorted first.
	 * <p>
	 * Note that it <em>is</em> still possible to prioritize something earlier
	 * than this value (e.g., for testing purposes), although doing so strongly
	 * discouraged in production.
	 * </p>
	 */
	public static final double FIRST = +1e300;

	/** Priority for items that very strongly prefer to be sorted early. */
	public static final double EXTREMELY_HIGH = +1000000;

	/** Priority for items that strongly prefer to be sorted early. */
	public static final double VERY_HIGH = +10000;

	/** Priority for items that prefer to be sorted earlier. */
	public static final double HIGH = +100;

	/** Default priority for items. */
	public static final double NORMAL = 0;

	/** Priority for items that prefer to be sorted later. */
	public static final double LOW = -100;

	/** Priority for items that strongly prefer to be sorted late. */
	public static final double VERY_LOW = -10000;

	/** Priority for items that very strongly prefer to be sorted late. */
	public static final double EXTREMELY_LOW = -1000000;

	/** Priority for items that must be sorted last.
	 * <p>
	 * Note that it <em>is</em> still possible to prioritize something later
	 * than this value (e.g., for testing purposes), although doing so strongly
	 * discouraged in production.
	 * </p>
	 */
	public static final double LAST = -1e300;

	/**
	 * Compares two {@link Prioritized} objects.
	 * <p>
	 * Note: this method provides a natural ordering that may be inconsistent with
	 * equals. That is, two unequal objects may often have the same priority, and
	 * thus return 0 when compared in this fashion. Hence, if this method is used
	 * as a basis for implementing {@link Comparable#compareTo} or
	 * {@link java.util.Comparator#compare}, that implementation may want to
	 * impose logic beyond that of this method, for breaking ties, if a total
	 * ordering consistent with equals is always required.
	 * </p>
	 * 
	 * @return -1 if {@code p1}'s priority is higher than {@code p2}'s, 1 if
	 *         {@code p2}'s priority is higher than {@code p1}'s, or 0 if they
	 *         have the same priority.
	 * @see org.scijava.util.ClassUtils#compare(Class, Class)
	 */
	public static <T extends Prioritized<T>> int compare(final Prioritized<T> p1, final Prioritized<T> p2) {
		final double priority1 =
			p1 == null ? Double.NEGATIVE_INFINITY : p1.getPriority();
		final double priority2 =
			p2 == null ? Double.NEGATIVE_INFINITY : p2.getPriority();
		if (priority1 == priority2) return 0;
		// NB: We invert the ordering here, so that large values come first,
		// rather than the typical natural ordering of smaller values first.
		return priority1 > priority2 ? -1 : 1;
	}
}
