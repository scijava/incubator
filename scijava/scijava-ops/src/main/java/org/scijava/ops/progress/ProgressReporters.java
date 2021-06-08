
package org.scijava.ops.progress;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProgressReporters {

	/**
	 * A quasi-static repository for obtaining a {@link ProgressReporter} used to
	 * report the progress of its {@link Object} key
	 */
	public static final Map<Object, ProgressReporter> reporters =
		new ConcurrentHashMap<>();

	public static ProgressReporter getReporter(Object o) {
		return reporters.get(o);
	}

	public static ProgressReporter setReporter(Object o, ProgressReporter p) {
		return reporters.put(o, p);
	}

}
