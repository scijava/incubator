
package org.scijava.opsdemo;

import java.util.function.Function;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Producer;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;

/**
 * How to pass lambdas to other Ops
 * <p>
 * Sometimes Ops take other Ops as parameters (for example, see imagej-ops2's
 * project Op), many of which are lambdas. The generic typing of lambdas,
 * however, is not retained in the bytecode; it follows that the Op matcher is
 * not able to determine whether a passed lambda satisfies the type required by
 * the Op.
 * <p>
 * This means that if we want to provide a lambda to an Op, we have to bake in
 * its type beforehand. This can be done using
 * {@link OpEnvironment#opify(Object, java.lang.reflect.Type)}.
 * <p>
 * This can be useful if you ever have a lambda that cannot be made into an Op.
 * <p>
 * Note that <strong>any</strong> Op returned by the Op matcher already knows
 * its type and thus does not need to be wrapped by {@code opify()} before being
 * passed back into the matcher.
 *
 * @author Gabriel Selzer
 */
@Plugin(type = OpCollection.class)
public class PassingLambdaToOps {

	/**
	 * Uses a {@link Context} to obtain an {@OpEnvironment}.
	 *
	 * @return an {@link OpEnvironment} that can be used to obtain {@link Op}s
	 */
	public static OpEnvironment getOpEnvironment() {
		// Note that all three of these Services are necessary
		Context context = new Context(OpService.class, PluginService.class,
			TypeService.class);
		OpService ops = context.getService(OpService.class);
		return ops.env();
	}

	/**
	 * Bakes the type of {@link PassingLambdaToOps#demoOpField} into the lambda
	 * 
	 * @param opEnv - the {@link OpEnvironment}
	 * @return a subclass of {@link Producer} that knows its generic type
	 */
	private static Producer<String> getOpifiedLambda(OpEnvironment opEnv) {
		return opEnv.opify(demoOpField, new Nil<Producer<String>>() {}.getType());
	}

	public static void run(OpEnvironment opEnv) {
		// -- Opify input lambda -- //
		Producer<String> inputLambda = getOpifiedLambda(opEnv);

		// -- Build Op, Run Immediately -- //
		String output = opEnv //
			.op("demo.acceptorOp") // Declare name of Op
			.input(getOpifiedLambda(opEnv)) // Declare Producer<String> input
			.outType(String.class) // Declare String output
			.apply(); // Compute the result (we don't need the Function)
 
		// -- Print results -- //
		System.out.println(output);
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

	/**
	 * A simple lambda Op
	 * 
	 * @author Gabriel Selzer
	 * @see Producer
	 */
	@OpField(names = "demo.passedOp")
	public static final Producer<String> demoOpField =
		() -> "This is how you pass Ops";

	/**
	 * A slightly more complicated lambda that takes as input a {@link Producer}
	 * 
	 * @author Gabriel Selzer
	 */
	@OpField(names = "demo.acceptorOp")
	public static final Function<Producer<String>, String> demoOpAcceptor = //
		(func) -> func.create() + " to other Ops!";

}
