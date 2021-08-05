
package org.scijava.ops.matcher;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.function.Computers;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.Op;
import org.scijava.ops.OpCollection;
import org.scijava.ops.OpDependency;
import org.scijava.ops.OpField;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

@Plugin(type = OpCollection.class)
public class DefaultMatchingErrorTest extends AbstractTestEnvironment {

	@OpField(names = "test.duplicateOp")
	public final Function<Double, Double> duplicateA = (in) -> in;

	@OpField(names = "test.duplicateOp")
	public final Function<Double, Double> duplicateB = (in) -> in;

	/**
	 * Assert a relevant (i.e. informational) message is thrown when multiple Ops
	 * conflict for a given OpRef.
	 */
	@Test
	public void duplicateErrorRegressionTest() {
		try {
			ops.op("test.duplicateOp").inType(Double.class).outType(Double.class)
				.function();
			Assert.fail();
		}
		catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith(
				"org.scijava.ops.matcher.OpMatchingException: " +
					"Multiple 'test.duplicateOp/java.util.function.Function<java.lang.Double, java.lang.Double>' " +
					"ops of priority 0.0:"));
		}
	}

	/**
	 * Assert that a relevant error is thrown when a dependency is missing
	 */
	@Test
	public void missingDependencyRegressionTest() {
		try {
			ops.op("test.missingDependencyOp").input(1.).outType(Double.class)
				.apply();
			Assert.fail("Expected DependencyMatchingException");
		}
		catch (IllegalArgumentException e) {
			Throwable cause = e.getCause();
			Assert.assertTrue(cause instanceof DependencyMatchingException);
			String message = cause.getMessage();
			Assert.assertTrue(message.contains("Name: \"test.nonexistingOp\""));
		}
	}

	/**
	 * Assert the DependencyMatchingException contains both dependencies in the
	 * chain
	 */
	@Test
	public void missingNestedDependencyRegressionTest() {
		try {
			ops.op("test.outsideOp").input(1.).outType(Double.class).apply();
			Assert.fail("Expected DependencyMatchingException");
		}
		catch (IllegalArgumentException e) {
			Throwable cause = e.getCause();
			Assert.assertTrue(cause instanceof DependencyMatchingException);
			String message = cause.getMessage();
			Assert.assertTrue(message.contains("Name: \"test.missingDependencyOp\""));
			Assert.assertTrue(message.contains("Name: \"test.nonexistingOp\""));
		}
	}

	/**
	 * Assert that DependencyMatchingExceptions do not omit relevant information
	 * when an adaptation is involved.
	 */
	@Test
	public void missingDependencyViaAdaptationTest() {
		Double[] d = new Double[0];
		try {
			ops.op("test.adaptMissingDep").input(d).outType(Double[].class).apply();
			Assert.fail("Expected DependencyMatchingException");
		}
		catch (IllegalArgumentException e) {
			Throwable cause = e.getCause();
			Assert.assertTrue(cause instanceof DependencyMatchingException);
			String message = cause.getMessage();
			Assert.assertTrue(message.contains("adapted"));
			Assert.assertTrue(message.contains("Name: \"test.nonexistingOp\""));

		}
	}

}

@Plugin(type = Op.class, name = "test.furtherOutsideOp")
class FurtherDependentOp implements Function<Double, Double> {

	@OpDependency(name = "test.outsideOp")
	private Function<Double, Double> op;

	/**
	 * @param t the input
	 * @return the output
	 */
	@Override
	public Double apply(Double t) {
		return op.apply(t);
	}

}

@Plugin(type = Op.class, name = "test.outsideOp")
class DependentOp implements Function<Double, Double> {

	@OpDependency(name = "test.missingDependencyOp")
	private Function<Double, Double> op;

	/**
	 * @param t the input
	 * @return the output
	 */
	@Override
	public Double apply(Double t) {
		return op.apply(t);
	}

}

@Plugin(type = Op.class, name = "test.missingDependencyOp")
class MissingDependencyOp implements Function<Double, Double> {

	@OpDependency(name = "test.nonexistingOp")
	private Function<Double, Double> op;

	/**
	 * @param t the input
	 * @return the output
	 */
	@Override
	public Double apply(Double t) {
		return op.apply(t);
	}

}

@Plugin(type = Op.class, name = "test.adaptMissingDep")
class MissingDependencyOpArr1 implements Computers.Arity1<Double[], Double[]> {

	@OpDependency(name = "test.nonexistingOp")
	private Function<Double, Double> op;

	/**
	 * @param t the input
	 * @param out the output
	 */
	@Override
	public void compute(Double[] t, Double[] out) {}

}

@Plugin(type = Op.class, name = "test.adaptMissingDep")
class MissingDependencyOpArr2 implements Function<Double, Double> {

	@OpDependency(name = "test.nonexistingOp")
	private Function<Double, Double> op;

	/**
	 * @param t the input
	 * @return the output
	 */
	@Override
	public Double apply(Double t) {
		return op.apply(t);
	}

}
