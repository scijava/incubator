
package org.scijava.opsdemo;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.ops.function.Consumers;
import org.scijava.ops.function.Functions;
import org.scijava.ops.function.Inplaces;
import org.scijava.param.Mutable;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;

/**
 * How to define your own Op type
 * <p>
 * To be used by the matcher without error, Op types must satisfy the following:
 * <ul>
 * <li>They must be {@code FunctionalInterface}s
 * <li>Their singular abstract method must annotate mutable parameters with the
 * {@link Mutable} annotation.
 * <li>Only one {@Mutable} annotation is allowed.
 * </ul>
 * <p>
 * From there, it is responsibility of those writing Ops implementing your type
 * to use the Op correctly.
 * <p>
 * Note: you <b>cannot</b> use {@OpBuilder} to construct any Ops implementing
 * your Functional types; only supported functional types within scijava-ops are
 * supported by {@code OpBuilder}. Thus, in order to get Ops implementing your
 * {@FunctionalInterface} from an {@link OpEnvironment}, you must call
 * {@link OpEnvironment#op(String, org.scijava.types.Nil, org.scijava.types.Nil[], org.scijava.types.Nil)}
 * as shown below.
 *
 * @author Gabriel Selzer
 * @see Computers
 * @see Functions
 * @see Inplaces
 */
@Plugin(type = OpCollection.class)
public class WriteYourOwnFunctionalType {

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

	static void run(OpEnvironment opEnv) {
		// -- Construct the Nils -- //

		// Op type Nil
		Nil<StrangeComputer<double[], double[], double[]>> opType = //
			new Nil<StrangeComputer<double[], double[], double[]>>()
			{};

		// Avoid duplication of code - declare this Nil once
		Nil<double[]> nilDoubleArray = new Nil<double[]>() {};

		// Op will take 3 of these as input
		Nil<?>[] inputTypes = new Nil[] { nilDoubleArray, nilDoubleArray,
			nilDoubleArray };

		// Only one is mutated - this is the output
		Nil<double[]> outType = nilDoubleArray;

		// -- Obtain the Op -- //
		// See demoStrangeComputer below for impl.
		StrangeComputer<double[], double[], double[]> demoOp = opEnv //
			.op("demo.strangeComputer", opType, inputTypes, outType);

		// -- Create arguments to Op -- //
		double[] in1 = { 10, 20, 30 };
		double[] in2 = { 1, 2, 3 };
		double[] in3 = { 4, 5, 6 };

		// -- Run the Op -- //
		demoOp.compute(in1, in2, in3);

		// -- Print results -- //
		for (int i = 0; i < in1.length; i++) {
			System.out.println(in2[i] + " x " + in3[i] + " = " + in1[i]);
		}
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

	/**
	 * A barebones {@link Op} implementing {@link StrangeComputer}
	 */
	@OpField(names = "demo.strangeComputer")
	public final StrangeComputer<double[], double[], double[]> demoStrangeComputer =
		(in1, in2, in3) -> {
			for (int i = 0; i < in1.length; i++) {
				// in1 = in2 * in3
				in1[i] = in2[i];
				in1[i] *= in3[i];
			}
		};

}

/**
 * An Op that mutates a preallocated output in the first position. Think of all
 * of the things that we can do with this!
 * 
 * @author Gabriel Selzer
 * @param <O> - the preallocated output of the Op
 * @param <I2> - the second parameter of the Op
 * @param <I3> - the third mutable parameter of the Op
 */
@FunctionalInterface
interface StrangeComputer<O, I2, I3> extends Consumers.Arity3<O, I2, I3> {

	void compute(@Mutable O in1, I2 in2, I3 in3);

	@Override
	default void accept(final O in1, final I2 in2, final I3 in3) {
		compute(in1, in2, in3);
	}
}
