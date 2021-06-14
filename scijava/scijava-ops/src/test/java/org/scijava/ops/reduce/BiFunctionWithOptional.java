
package org.scijava.ops.reduce;

import org.scijava.ops.function.Functions;
import org.scijava.param.Optional;

@FunctionalInterface
public interface BiFunctionWithOptional<I1, I2, I3, O> extends
	Functions.Arity3<I1, I2, I3, O>
{

	@Override
	public O apply(I1 in1, I2 in2, @Optional I3 in3);
}
