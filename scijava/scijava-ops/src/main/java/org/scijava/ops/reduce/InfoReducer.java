
package org.scijava.ops.reduce;

import org.scijava.ops.OpInfo;
import org.scijava.plugin.SciJavaPlugin;

public interface InfoReducer extends SciJavaPlugin {

	boolean canReduce(OpInfo info);

	ReducedOpInfo reduce(OpInfo info, int numReductions);
}
