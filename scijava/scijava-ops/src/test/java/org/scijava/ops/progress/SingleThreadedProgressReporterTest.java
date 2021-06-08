
package org.scijava.ops.progress;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.function.Inplaces;
import org.scijava.ops.progress.impl.SingleThreadedReporter;

public class SingleThreadedProgressReporterTest extends
	AbstractTestEnvironment
{

	@Test
	public void testSingleThreaded() throws InterruptedException {
		// setup object to be processed
		int[][][] cube = new int[100][100][100];

		// setup "Op" to run in another thread (so that we can track its progress
		// here)
		ThreadThing t = new ThreadThing();
		ProgressReporter p = new SingleThreadedReporter(t, 100 * 100 * 100);
		ProgressReporters.setReporter(t, p);
		Thread thread = new Thread(() -> t.accept(cube));
		thread.start();

		// track progress
		double lastProgress = 0, currentProgress;
		while (thread.isAlive()) {
			Thread.sleep(0, 5000);
			currentProgress = p.getProgress();
			Assert.assertTrue(currentProgress >= lastProgress);
			lastProgress = currentProgress;
		}

		// Assert completion once the thread running the "Op" has terminated
		Assert.assertTrue(p.isComplete());
	}

}

class ThreadThing implements Inplaces.Arity1<int[][][]> {

	public ThreadThing() {}

	@Override
	public void mutate(int[][][] io) {
		// get ProgressReporter
		ProgressReporter p = ProgressReporters.getReporter(this);
		if (p == null || p.hasStarted()) throw new IllegalArgumentException();
		p.reportStart();

		// Process io
		for (int i = 0; i < io.length; i++) {
			for (int j = 0; j < io[i].length; j++) {
				for (int k = 0; k < io[i][j].length; k++) {
					io[i][j][k] = 1;
				}
				p.reportPixels(io[i][j].length);
			}
		}
	}

}
