
package org.scijava.ops.engine.yaml.ops;

import java.lang.reflect.Method;

/**
 * A static {@link Method}, exposed to Ops via YAML
 * 
 * @author Gabriel Selzer
 */
public class YAMLMethodOp {

	/**
	 *
	 * @implNote op name=example.sub type=java.util.function.BiFunction
	 * @param aDouble the first double
	 * @param aDouble2 the second double
	 * @return the difference
	 */
	public static Double subtract(Double aDouble, Double aDouble2) {
		return aDouble - aDouble2;
	}

}
