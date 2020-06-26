
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
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;
import org.scijava.util.MersenneTwisterFast;

/**
 * How to adapt an {@link Op} manually using an {@code adapt} Op.
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
 * performed in a way that boosts performance.
 * <p>
 * <b>While this behavior is showcased for completeness, we do not suggest that
 * it should be used. In most cases, it is better to allow the matcher to
 * automagically adapt the Op. This avoids the wall of text shown below. </b>
 *
 * @author Gabriel Selzer
 * @see AdaptOpAutomagically
 */
@Plugin(type = OpCollection.class)
public class AdaptOpManually {

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

	public static void run(OpEnvironment opEnv) {
		// -- Create input -- //
		List<Integer> input = generateDemoList();

		// -- Build element-wise Op -- //
		Function<Integer, Integer> intComputer = opEnv //
			.op("demo.uselessIntegerOp") // Ask for an Op with this name
			.inType(Integer.class) // Provide the input image
			.outType(Integer.class) // Provide the output image
			.function(); // Give us back a Function

		// -- Construct the Nils -- //
		Nil<Function<Integer, Integer>> inType = new Nil<>() {};
		Nil<Function<Iterable<Integer>, Iterable<Integer>>> outType =
			new Nil<>()
			{};

		// -- Build adaptor -- //
		Function<Function<Integer, Integer>, Function<Iterable<Integer>, Iterable<Integer>>> adaptor =
			opEnv.op("adapt") // Ask for an Op with the given name
				.inType(inType) // Declare the input type of the Op
				.outType(outType) // Declare the output type of the Op
				.function(); // Compute directly (i.e. do not give us a Computer)

		// -- Adapt Op -- //
		Function<Iterable<Integer>, Iterable<Integer>> iterableComputer = adaptor
			.apply(intComputer);

		// -- Run Op -- //
		Iterable<Integer> output = iterableComputer.apply(input);

		// -- Print results -- //
		Iterator<Integer> inputItr = input.iterator();
		Iterator<Integer> outputItr = output.iterator();
		while (inputItr.hasNext()) {
			System.out.println(inputItr.next() + " x 5 = " + outputItr.next());
		}
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

	/**
	 * A useless Op
	 * <p>
	 * Note that the Op operates on {@link Integer}s, not on {@link Iterable}s.
	 * 
	 * @author Gabriel Selzer
	 */
	@OpField(names = "demo.uselessIntegerOp")
	public final Function<Integer, Integer> demoOpField = (in) -> in * 5;

}
