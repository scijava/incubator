
package org.scijava.ops.simplify;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.plugin.Plugin;
import org.scijava.util.ObjectArray;

/**
 * Basic simplify test
 * 
 * @author Gabriel Selzer
 * @author Curtis Rueden
 */
@Plugin(type = OpCollection.class)
public class SimplifyTest extends AbstractTestEnvironment {

	@OpField(names = "test.math.powDouble", params = "base, exponent, result")
	public final BiFunction<Double, Double, Double> powOp = (b, e) -> Math.pow(b,
		e);

	@OpField(names = "test.math.powDouble", params = "base, exponent, result")
	public final BiFunction<Long, Long, Double> powOpL = (b, e) -> Math.pow(b, e);

	@OpField(names = "test.math.powDouble", params = "base, exponent, result")
	public final BiFunction<Integer[], Double, Double> powOpArray = (b, e) -> Math.pow(b[0], e);

	@OpField(names = "test.math.powDouble", params = "base, exponent, result")
	public final Computers.Arity2<Long, Long, List<Long>> powOpLArray = (b, e, list) -> {
		list.clear();
		list.add((long) Math.pow(b, e));
	};

	@Test
	public void testSimplify() {
		Integer number = 2;
		Integer exponent = 2;
		Double result = ops.op("test.math.powDouble").input(number, exponent)
			.outType(Double.class).apply();
		assertEquals(4.0, result, 0);
	}

	@Test
	public void testSimplifySome() {
		Integer number = 2;
		Double exponent = 2.;
		Double result = ops.op("test.math.powDouble").input(number, exponent)
			.outType(Double.class).apply();
		assertEquals(4.0, result, 0);
	}
	
	@Test
	public void testSimplifyArray() {
		Byte[] number = {2};
		Double exponent = 3.;
		Double result = ops.op("test.math.powDouble").input(number, exponent)
			.outType(Double.class).apply();
		assertEquals(8.0, result, 0);
	}

	@Test
	public void testSimplifiedOp() {
		BiFunction<Number, Number, Double> numFunc = ops.op("test.math.powDouble")
			.inType(Number.class, Number.class).outType(Double.class).function();
		
		Double result = numFunc.apply(3., 4.);
		assertEquals(81., result, 0);
	}

//	@Test
//	public void testSimplifyComputer() {
//		Integer number = 2;
//		Integer exponent = 4;
//		List<Long> list = new ArrayList<>();
//		list.add(5l);
//		ops.op("test.math.powDouble").input(number, exponent)
//			.output(list).compute();
//		assertEquals(16l, list.get(0), 0);
//	}
}
