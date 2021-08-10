
package org.scijava.ops.engine.impl;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.function.Computers;
import org.scijava.ops.api.ProgressTracker;
import org.scijava.ops.engine.AbstractTestEnvironment;
import org.scijava.ops.engine.OpHistoryService;

public class ProgressReportingTest extends AbstractTestEnvironment {

	@Test
	public void testProgressReporting() throws InterruptedException,
		ExecutionException
	{
		int[] in0 = IntStream.range(0, 100000).toArray();
		int[] in1 = IntStream.range(0, 100000).map(i -> i * 2).toArray();
		int[] actual = IntStream.range(0, 100000).map(i -> 0).toArray();

		Computers.Arity2<int[], int[], int[]> op = ops.op("test.progressAdd").input(
			in0, in1).outType(int[].class).computer();

		Thread thread = new Thread(() -> op.compute(in0, in1, actual));

		// execute the Op in another thread
		thread.start();

		// track progress
		ProgressTracker p = context.getService(OpHistoryService.class).getHistory()
			.progressTrackerFor(op);
		double lastProgress = 0, currentProgress;
		while (thread.isAlive()) {
			Thread.sleep(0, 5000);
			currentProgress = p.getProgress(in0, in1, actual);
			Assert.assertTrue(currentProgress >= lastProgress);
			lastProgress = currentProgress;
		}

		// Assert completion once the thread running the Inplace has terminated
		Assert.assertTrue(p.isCompleted(in0, in1, actual));
		Assert.assertEquals(p.getProgress(in0, in1, actual), 1.0, 1e-9);

		int[] expected = IntStream.range(0, 100000).map(i -> i * 3).toArray();

		Assert.assertArrayEquals(expected, actual);
	}

}
