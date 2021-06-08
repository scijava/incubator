
package org.scijava.ops.progress;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.function.Inplaces;
import org.scijava.ops.progress.impl.SingleThreadedReporter;

public class SingleThreadedProgressReporterTest extends
	AbstractTestEnvironment
{
	public final Inplaces.Arity1<int[][][]> updatePerRow = (io) -> {
		// get ProgressReporter -- TODO: Can we make this cleaner?
		ProgressReporter p = ProgressReporters.getReporter(this.updatePerRow);
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
	};

	@Test
	public void testSingleThreadedPerRowUpdate() throws InterruptedException {
		TestInlace(updatePerRow);
	}

	private void TestInlace(Inplaces.Arity1<int[][][]> i) throws InterruptedException {
		// setup object to be processed
		int[][][] cube = new int[100][100][100];

		// setup "Op" to run in another thread (so that we can track its progress
		// here)
		ProgressReporter p = new SingleThreadedReporter(i, 100 * 100 * 100);
		ProgressReporters.setReporter(i, p);
		Thread thread = new Thread(() -> i.accept(cube));
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

