package org.scijava.ops.reduce;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpMethod;
import org.scijava.ops.core.OpCollection;
import org.scijava.plugin.Plugin;

@Plugin(type = OpCollection.class)
public class OptionalArgumentsFromIFaceTest extends AbstractTestEnvironment{

	@OpMethod(names = "test.optionalSubtract", type = BiFunctionWithOptional.class)
	public static Double foo(Double i1, Double i2, Double i3) {
		if (i3 == null) i3 = 0.;
		return i1 - i2 - i3;
	}

	@Test
	public void testMethodWithOneOptional() {
		Double o = ops.env().op("test.optionalSubtract").input(2., 5., 7.).outType(Double.class).apply();
		Double expected = -10.0;
		Assert.assertEquals(expected, o);
	}

	@Test
	public void testMethodWithoutOptionals() {
		Double o = ops.env().op("test.optionalSubtract").input(2., 5.).outType(Double.class).apply();
		Double expected = -3.0;
		Assert.assertEquals(expected, o);
	}

	@OpField(names = "test.optionalAnd", params = "in1, in2, in3")
	public final BiFunctionWithOptional<Boolean, Boolean, Boolean, Boolean> bar =
		(in1, in2, in3) -> {
			if (in3 == null) in3 = true;
			return in1 & in2 & in3;
		};

	@Test
	public void testFieldWithOptionals() {
		Boolean in1 = true;
		Boolean in2 = true;
		Boolean in3 = false;
		Boolean o = ops.env().op("test.optionalAnd").input(in1, in2, in3).outType(Boolean.class).apply();
		Boolean expected = false;
		Assert.assertEquals(expected, o);
	}

	@Test
	public void testFieldWithoutOptionals() {
		Boolean in1 = true;
		Boolean in2 = true;
		Boolean o = ops.env().op("test.optionalAnd").input(in1, in2).outType(Boolean.class).apply();
		Boolean expected = true;
		Assert.assertEquals(expected, o);
	}

	@Test
	public void testClassWithOptionals() {
		Boolean in1 = true;
		Boolean in2 = false;
		Boolean in3 = false;
		Boolean o = ops.env().op("test.optionalOr").input(in1, in2, in3).outType(Boolean.class).apply();
		Boolean expected = true;
		Assert.assertEquals(expected, o);
	}

	@Test
	public void testClassWithoutOptionals() {
		Boolean in1 = true;
		Boolean in2 = false;
		Boolean o = ops.env().op("test.optionalOr").input(in1, in2).outType(Boolean.class).apply();
		Boolean expected = true;
		Assert.assertEquals(expected, o);
	}

}
