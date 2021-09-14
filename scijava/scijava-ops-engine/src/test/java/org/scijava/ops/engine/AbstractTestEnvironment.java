package org.scijava.ops.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.scijava.Context;
import org.scijava.cache.CacheService;
import org.scijava.ops.engine.OpService;
import org.scijava.ops.engine.impl.DefaultOpService;
import org.scijava.plugin.PluginService;
import org.scijava.thread.ThreadService;
import org.scijava.types.TypeService;

public abstract class AbstractTestEnvironment {

	protected static Context context;
	protected static OpService ops;

	@BeforeClass
	public static void setUp() {
		context = new Context(OpService.class, OpHistoryService.class, CacheService.class, ThreadService.class, PluginService.class, TypeService.class);
		ops = context.service(OpService.class);
	}

	@AfterClass
	public static void tearDown() {
		context.dispose();
		context = null;
		ops = null;
	}
	
	protected static boolean arrayEquals(double[] arr1, Double... arr2) {
		return Arrays.deepEquals(Arrays.stream(arr1).boxed().toArray(Double[]::new), arr2);
	}

	protected static <T> void assertIterationsEqual(final Iterable<T> expected,
		final Iterable<T> actual)
	{
		final Iterator<T> e = expected.iterator();
		final Iterator<T> a = actual.iterator();
		while (e.hasNext()) {
			assertTrue("Fewer elements than expected", a.hasNext());
			assertEquals(e.next(), a.next());
		}
		assertFalse("More elements than expected", a.hasNext());
	}
}
