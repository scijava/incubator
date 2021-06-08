
package org.scijava.ops.progress;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.function.Inplaces;
import org.scijava.ops.progress.impl.DefaultProgressReporter;

public class SingleThreadedProgressReporterTest extends
	AbstractTestEnvironment
{
	public final Inplaces.Arity1<int[][][]> updatePerPixel = (io) -> {
		// get ProgressReporter -- TODO: Can we make this cleaner?
		ProgressReporter p = ProgressReporters.getAndStart(this.updatePerPixel);

		// Process io
		for (int i = 0; i < io.length; i++) {
			for (int j = 0; j < io[i].length; j++) {
				for (int k = 0; k < io[i][j].length; k++) {
					io[i][j][k] = 1;
					p.reportPixel();
				}
			}
		}
	};

	public final Inplaces.Arity1<int[][][]> updatePerRow = (io) -> {
		// get ProgressReporter -- TODO: Can we make this cleaner?
		ProgressReporter p = ProgressReporters.getAndStart(this.updatePerRow);

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

	public final Inplaces.Arity1<int[][][]> updatePerChunk = (io) -> {
		// get ProgressReporter -- TODO: Can we make this cleaner?
		ProgressReporter p = ProgressReporters.getAndStart(this.updatePerChunk);

		// Process io
		long numPixels = 0;
		for (int i = 0; i < io.length; i++) {
			for (int j = 0; j < io[i].length; j++) {
				for (int k = 0; k < io[i][j].length; k++) {
					io[i][j][k] = 1;
				}
				numPixels += io[i][j].length;
			}
		}
		p.reportPixels(numPixels);
	};

	@Test
	public void testSingleThreadedPerPixelUpdate() throws InterruptedException {
		TestInlace(updatePerPixel);
	}

	@Test
	public void testSingleThreadedPerRowUpdate() throws InterruptedException {
		TestInlace(updatePerRow);
	}

	@Test
	public void testSingleThreadedPerChunkUpdate() throws InterruptedException {
		TestInlace(updatePerChunk);
	}

	private void TestInlace(Inplaces.Arity1<int[][][]> i) throws InterruptedException {
		// setup object to be processed
		int[][][] cube = new int[100][100][100];

		// setup Inplace to run in another thread (so that we can track its progress
		// here)
		ProgressReporter p = new DefaultProgressReporter(i, 100 * 100 * 100);
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

		// Assert completion once the thread running the Inplace has terminated
		Assert.assertTrue(p.isComplete());

		for (int[][] slice : cube) {
			for (int[] row : slice) {
				for (int pixel : row) {
					Assert.assertEquals(1, pixel);
				}
			}
		}
	}

}

