
package org.scijava.ops.progress;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.function.Inplaces;
import org.scijava.ops.progress.impl.DefaultProgressReporter;

public class MultiThreadedProgressReporterTest extends AbstractTestEnvironment {

	private final Function<ProgressReporter, Function<int[][], Thread>> createThreadOpPerPixel = p -> {
		return io -> new Thread(() -> {
			for (int j = 0; j < io.length; j++) {
				for (int k = 0; k < io[j].length; k++) {
					io[j][k] = 1;
					p.reportPixel();
				}
			}
		});
	};

	private final Function<ProgressReporter, Function<int[][], Thread>> createThreadOpPerRow = p -> {
		return io -> new Thread(() -> {
			long numPixels = 0;
			for (int j = 0; j < io.length; j++) {
				for (int k = 0; k < io[j].length; k++) {
					io[j][k] = 1;
				}
				numPixels += io[j].length;
			}
			p.reportPixels(numPixels);
		});
	};

	private final Consumer<Thread> joinThread = t -> {
		try {
			t.join();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	};

	public final Inplaces.Arity1<int[][][]> updatePerPixel = (io) -> {
		// get ProgressReporter -- TODO: Can we make this cleaner?
		ProgressReporter p = ProgressReporters.getAndStart(this.updatePerPixel);

		// Process io
		List<Thread> threads = Arrays.stream(io) //
			.map(createThreadOpPerPixel.apply(p)) //
			.collect(Collectors.toList());
		threads.forEach(thread -> thread.start());
		threads.forEach(joinThread);
	};

	public final Inplaces.Arity1<int[][][]> updatePerRow = (io) -> {
		// get ProgressReporter -- TODO: Can we make this cleaner?
		ProgressReporter p = ProgressReporters.getAndStart(this.updatePerRow);

		// Process io
		List<Thread> threads = Arrays.stream(io) //
			.map(createThreadOpPerRow.apply(p)) //
			.collect(Collectors.toList());
		threads.forEach(thread -> thread.start());
		threads.forEach(joinThread);
	};

	@Test
	public void testSingleThreadedPerPixelUpdate() throws InterruptedException {
		TestInlace(updatePerPixel);
	}

	@Test
	public void testSingleThreadedPerRowUpdate() throws InterruptedException {
		TestInlace(updatePerRow);
	}

	private void TestInlace(Inplaces.Arity1<int[][][]> i)
		throws InterruptedException
	{
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
