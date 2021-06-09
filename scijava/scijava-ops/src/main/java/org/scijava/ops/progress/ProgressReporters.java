
package org.scijava.ops.progress;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A powerful class designed to:
 * <ul>
 * <li>Serve as a <b>static</b> repository for all in-use
 * {@link ProgressReporter}s. This design decision was make it as easy as
 * possible for:
 * <ul>
 * <li>The {@link Object}s doing the reporting to get their
 * {@link ProgressReporter}s</li>
 * <li>Tasks to easily inquire about the progress of relevant {@link Object}s.
 * </li>
 * </ul>
 * </li>
 * <li>Make it as easy as possible for users to setup a {@link ProgressReporter}
 * for a particular {@link Object}. Since it is nearly impossible to reason
 * about the behavior of a task (e.g. whether it utilizes parallelization), we
 * delegate the setup to the user/task and attempt to make the setup as painless
 * as possible
 * </ul>
 * 
 * @author Gabriel Selzer
 */
public class ProgressReporters {

	/**
	 * A quasi-static repository for obtaining a {@link ProgressReporter} used to
	 * report the progress of its {@link Object} key
	 */
	private static final Map<Object, ProgressReporter> reporters =
		new ConcurrentHashMap<>();

	public static ProgressReporter get(Object o) {
		return reporters.get(o);
	}

	public static double getProgress(Object o) {
		return reporters.get(o).getProgress();
	}

	public static ProgressReporter getAndStart(Object o) {
		ProgressReporter p = get(o);
		if (p == null) throw new IllegalArgumentException("Object " + o + " has not been given a ProgressReporter!");
		if (p.hasStarted()) throw new IllegalArgumentException("The ProgressReporter for Object " + o + " has already been started!");
		p.reportStart();
		return p;
	}

	public static ProgressReporter setReporter(Object o, ProgressReporter p) {
		return reporters.put(o, p);
	}

	public static void pixelCompleted(Object o) {
		reporters.get(o).reportPixel();
	}

	public static void pixelsCompleted(Object o, int numPixels) {
		reporters.get(o).reportPixels(numPixels);
	}

}
