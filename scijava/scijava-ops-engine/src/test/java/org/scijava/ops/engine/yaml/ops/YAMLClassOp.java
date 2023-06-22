
package org.scijava.ops.engine.yaml.ops;

import java.util.function.BiFunction;

import org.scijava.ops.api.OpEnvironment;

/**
 * An example Op, registered by YAML into the {@link OpEnvironment}
 * @implNote op names=example.add
 * @input aDouble the first input
 * @input bDouble the second input
 * @output the result
 */
public class YAMLClassOp implements BiFunction<Double, Double, Double> {

	@Override
	public Double apply(Double aDouble, Double aDouble2) {
		return aDouble + aDouble2;
	}

	/**
	 * An example inner class Op, registered by YAML into the {@link OpEnvironment}
	 * @implNote op names=example.div
	 * @input aDouble the first input
	 * @input bDouble the second input
	 * @output the result
	 */
	public static class YAMLInnerClassOp implements
		BiFunction<Double, Double, Double>
	{

		@Override
		public Double apply(Double aDouble, Double aDouble2) {
			return aDouble / aDouble2;
		}
	}
}
