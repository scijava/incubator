
package org.scijava.opsdemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpService;
import org.scijava.ops.adapt.functional.ComputersToFunctionsViaSource;
import org.scijava.ops.adapt.lift.FunctionToIterables;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;
import org.scijava.util.MersenneTwisterFast;

/**
 * How to adapt an {@link Op} automagically using an {@code adapt} Op.
 * <p>
 * Note that adapting Ops often comes at a performance cost. Consider the
 * adaptation of a {@link Computers.Arity1} to a {@code Function} (using
 * {@link ComputersToFunctionsViaSource.Computer1ToFunction1ViaSource}): in
 * order to call this {@code Computer} as a {@code Function}, you must create an
 * output {@link Object} every time the {@code Function} is called so that the
 * {@code Computer} has a preallcated output. This is (often) fine if you only
 * need to use the Op once, but for repeated calls consider calling the Op as
 * its declared Type.
 * <p>
 * Note that this performance cost is not standard; some adaptations can be
 * performed in a way that maintains or even boosts performance.
 *
 * @author Gabriel Selzer
 */
@Plugin(type = OpCollection.class)
public class AdaptOpAutomagically {

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
	 * Generates a {@link List} for testing purposes
	 * 
	 * @return a {@code List}
	 */
	public static List<Integer> generateDemoList() {
		// Fill a test list with ints in range [0, 10)
		List<Integer> list = new ArrayList<>();
		MersenneTwisterFast betterRNG = new MersenneTwisterFast(0xf1eece);
		for (int i = 0; i < 5; i++)
			list.add(betterRNG.nextInt(10));
		return list;
	}

	/**
	 * Note that this Op call makes use of {@link FunctionToIterables} to allow
	 * this Op to be called across every element of the List. This does force us
	 * to request an {@code Iterable<Integer>} as output, however.
	 * 
	 * @param opEnv - the OpEnvironment
	 * @see FunctionToIterables
	 */
	public static void run(OpEnvironment opEnv) {
		// -- Construct input -- //
		List<Integer> input = generateDemoList();

		// -- Construct the Nils -- //
		Nil<Iterable<Integer>> outputType = new Nil<>() {};

		// -- Build the Op, run Immediately -- //
		Iterable<Integer> output = opEnv //
			.op("demo.strangeIntegerOp") // Ask for Op with the given name
			.input(input) // Provide the input list
			.outType(outputType) // We want an Iterable of Integer back
			.apply(); // Compute directly (i.e. do not give us a Function)

		// -- Print results -- //
		Iterator<Integer> inputItr = input.iterator();
		Iterator<Integer> outputItr = output.iterator();
		while (inputItr.hasNext()) {
			System.out.println(inputItr.next() + " x 4 = " + outputItr.next());
		}

	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

	/**
	 * A strange Op
	 * <p>
	 * Note that the Op operates on {@link Integer}s, not on {@link Iterable}s.
	 * 
	 * @author Gabriel Selzer
	 */
	@OpField(names = "demo.strangeIntegerOp")
	public final Function<Integer, Integer> demoOpField = (in) -> in * 4;

}
