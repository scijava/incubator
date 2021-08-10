
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
	public void testProgressReportingSingleExecution() throws InterruptedException,
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

	@Test
	public void testProgressReportingMultipleExecution() throws InterruptedException,
		ExecutionException
	{
		int[] in0 = IntStream.range(0, 1000000).toArray();
		int[] in1 = IntStream.range(0, 1000000).map(i -> i * 2).toArray();
		int[] in2 = IntStream.range(0, 1000000).map(i -> i * 4).toArray();
		int[] in3 = IntStream.range(0, 1000000).map(i -> i * 5).toArray();
		int[] actual = IntStream.range(0, 1000000).map(i -> 0).toArray();
		int[] actual1 = IntStream.range(0, 1000000).map(i -> 0).toArray();

		Computers.Arity2<int[], int[], int[]> op = ops.op("test.progressAdd").input(
			in0, in1).outType(int[].class).computer();

		Thread thread1 = new Thread(() -> op.compute(in0, in1, actual));
		Thread thread2 = new Thread(() -> op.compute(in2, in3, actual1));

		// execute the Op in another thread
		thread1.start();
		thread2.start();

		// track progress
		ProgressTracker p = context.getService(OpHistoryService.class).getHistory()
			.progressTrackerFor(op);
		double lastProgress1 = 0, currentProgress1;
		double lastProgress2 = 0, currentProgress2;
		while (thread1.isAlive() || thread2.isAlive()) {
			Thread.sleep(0, 100);
			currentProgress1 = p.getProgress(in0, in1, actual);
			currentProgress2 = p.getProgress(in2, in3, actual1);
			System.out.println("Run 1: " + currentProgress1);
			System.out.println("Run 2: " + currentProgress2);
			Assert.assertTrue(currentProgress1 >= lastProgress1);
			Assert.assertTrue(currentProgress2 >= lastProgress2);
			lastProgress1 = currentProgress1;
			lastProgress2 = currentProgress2;
		}

		// Assert completion once the thread running the Inplace has terminated
		Assert.assertTrue(p.isCompleted(in0, in1, actual));
		Assert.assertEquals(p.getProgress(in0, in1, actual), 1.0, 1e-9);
		Assert.assertTrue(p.isCompleted(in2, in3, actual1));
		Assert.assertEquals(p.getProgress(in2, in3, actual1), 1.0, 1e-9);

		int[] expected1 = IntStream.range(0, 1000000).map(i -> i * 3).toArray();
		int[] expected2 = IntStream.range(0, 1000000).map(i -> i * 9).toArray();

		Assert.assertArrayEquals(expected1, actual);
		Assert.assertArrayEquals(expected2, actual1);
	}

}
