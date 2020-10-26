package org.scijava.ops.simplify;

import static org.junit.Assert.assertEquals;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.core.OpCollection;
import org.scijava.plugin.Plugin;

/**
 * Basic simplify test
 * 
 * @author Gabriel Selzer
 * @author Curtis Rueden
 */
@Plugin(type = OpCollection.class)
public class SimplifyTest extends AbstractTestEnvironment {
	
	@OpField(names = "test.math.powDouble", params = "base, exponent, result")
	public final BiFunction<Double, Double, Double> powOp = (b, e) -> Math.pow(b, e);

	@OpField(names = "test.math.powDouble", params = "base, exponent, result")
	public final BiFunction<Long, Long, Double> powOpL = (b, e) -> Math.pow(b, e);

	@Test
	public void testSimplify() {
		Integer number = 2;
		Integer exponent = 2;
		Double result = ops.op("test.math.powDouble").input(number, exponent).outType(
			Double.class).apply();
		assertEquals(4.0, result, 0);
	}

	@Test
	public void testSimplifySome() {
		Integer number = 2;
		Double exponent = 2.;
		Double result = ops.op("test.math.powDouble").input(number, exponent).outType(
			Double.class).apply();
		assertEquals(4.0, result, 0);
	}
	
	@Test
	public void testFoo() {
//		Type[] bar = Types.raw();
	}
	
	
	static class DoubleFunction<T extends Double, U extends Double> implements Function<T, U> {

		@Override
		public U apply(T t) {
			return null;
		}
		
	}

}
