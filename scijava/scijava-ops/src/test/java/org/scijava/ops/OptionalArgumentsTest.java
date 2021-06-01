package org.scijava.ops;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.ops.core.OpCollection;
import org.scijava.plugin.Plugin;

@Plugin(type = OpCollection.class)
public class OptionalArgumentsTest extends AbstractTestEnvironment{

	@Test
	public void testClassWithOptional() {
		Double sum = ops.env().op("test.optionalAdd").input(2.0, 5.0).outType(Double.class).apply();
		Double expected = 7.0;
		Assert.assertEquals(expected, sum);
	}

	@Test
	public void testClassWithoutOptional() {
		Double sum = ops.env().op("test.optionalAdd").input(2.0).outType(Double.class).apply();
		Double expected = 2.0;
		Assert.assertEquals(expected, sum);

	}

}
