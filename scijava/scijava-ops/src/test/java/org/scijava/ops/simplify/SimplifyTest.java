package org.scijava.ops.simplify;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.util.Types;
import java.lang.reflect.Type;

/**
 * Basic simplify test
 * 
 * @author Gabriel Selzer
 * @author Curtis Rueden
 */
public class SimplifyTest extends AbstractTestEnvironment {

	@Test
	public void testSimplify() {
		Integer number = 2;
		Integer exponent = 2;
		Double result = ops.op("math.pow").input(number, exponent).outType(
			Double.class).apply();
		assertEquals(4.0, result, 0);
	}

}
