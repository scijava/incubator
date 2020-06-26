
package org.scijava.opsdemo;

import java.util.function.Function;

import org.scijava.Context;
import org.scijava.Priority;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.ops.function.Functions;
import org.scijava.ops.function.Inplaces;
import org.scijava.ops.function.Producer;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

/**
 * How to write an {@link Op} that takes {@link Priority} over other Ops
 * <p>
 * Suppose you write an Op that mimics the functionality of another Op, but only
 * operates on some subclass of the typing of that Op. Furthermore, suppose your
 * Op can run faster. It would thus make sense to let the matcher know that it
 * should give users your Op when applicable, and the existing Op when not
 * applicable. This is where {@code Priority} comes in. When an Op request can
 * be fulfilled by both your new Op and the existing Op, {@code Priority} can be
 * used to declare that one of these Ops should be used over the other.
 *
 * @author Gabriel Selzer
 * @see WriteOpWithPriority#demoOpField
 * @see Computers
 * @see Functions
 * @see Inplaces
 */
@Plugin(type = OpCollection.class)
public class WriteOpWithPriority {

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
	 * Note that we first ask for an Op that can take an Integer as an input, and
	 * we get back {@code demoOpField}. Then we ask for an Op that can take a
	 * {@code Double} as an input. This will satisfy both, but since we set
	 * {@code demoOpFieldHighPriority} as having higher priority, we will get that
	 * one back.
	 * 
	 * @param opEnv
	 */
	static void run(OpEnvironment opEnv) {
		// -- Create the first input -- //
		Integer input1 = 6;

		// -- Build the first Op, Run Immediately -- //
		Double output1 = opEnv.op("demo.opField") //
			.input(input1) // The input is an Integer
			.outType(Double.class) // Our Op should return a Double
			.apply(); // Compute directly (we do not want a Function back)

		// -- Print results of first Op-- //
		System.out.println(input1 + " x 2 = " + output1);

		// -- Create the second input -- //
		Double input2 = 7.;

		// -- Build the second Op, Run Immediately -- //
		Double output2 = opEnv.op("demo.opField") //
			.input(input2) // The input is a Double
			.outType(Double.class) // Our Op should return a Double
			.apply(); // Compute directly (we do not want a Function back)

		// -- Print results of second Op-- //
		System.out.println(input2 + " x 3 = " + output2);
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

	/**
	 * A barebones {@link Op} defined as an {@link OpField}. Returns a double
	 * twice as large as the input
	 */
	@OpField(names = "demo.opField")
	public final Function<Number, Double> demoOpField = (in) -> in.doubleValue() *
		2;

	/**
	 * A barebones {@link Op} defined as an {@link OpField}. Returns a double
	 * three times as large as the input. Note that this Op has a higher
	 * {@link Priority} than {@code demoOpField}
	 */
	@OpField(names = "demo.opField", priority = Priority.HIGH)
	public final Function<Double, Double> demoOpFieldHighPriority = (in) -> in *
		3.;

}
