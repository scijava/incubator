package org.scijava.ops.simplify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.OpDependency;
import org.scijava.ops.OpField;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.param.Mutable;
import org.scijava.plugin.Plugin;

@Plugin(type = OpCollection.class)
public class SimplifyIOTest extends AbstractTestEnvironment{

	@OpField(names = "test.math.square")
	public final Function<Double, Double> squareOp = in -> in * in;

	@Test
	public void testFunctionOutputSimplification() {
		Integer in = 4;
		Integer square = ops.op("test.math.square").input(in).outType(Integer.class).apply();
		
		assertEquals(square, 16, 0.);
	}
	
	@OpField(names = "test.math.square")
	public final Computers.Arity1<Double[], Double[]> squareArray = (in, out) -> {
		for(int i = 0; i < in.length && i < out.length; i++) {
			out[i] = squareOp.apply(in[i]);
		}
	};
	
	@Test
	public void basicComputerTest() {
		Integer[] in = new Integer[] {1, 2, 3};
		Integer[] out = new Integer[] {4, 5, 6}; 
		
		ops.op("test.math.square").input(in).output(out).compute();
		assertArrayEquals(out, new Integer[] {1, 4, 9});
	}

}

