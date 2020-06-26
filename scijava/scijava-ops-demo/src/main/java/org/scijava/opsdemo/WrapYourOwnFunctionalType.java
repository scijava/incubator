
package org.scijava.opsdemo;

import java.lang.reflect.Type;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Consumers;
import org.scijava.ops.function.OpWrappers;
import org.scijava.ops.function.Producer;
import org.scijava.ops.util.OpWrapper;
import org.scijava.param.Mutable;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.GenericTyped;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;

/**
 * How to wrap your own Functional Type with an {@link OpWrapper}
 * <p>
 * Creating your own {@link FunctionalInterface}s to be used by the matcher is
 * fun and all, but without an {@link OpWrapper} you will not be able to pass
 * your {@link Op} to other Ops without using {@link OpEnvironment#opify()}
 * first. This also has the side effect of error messages complaining about the
 * lack of an {@link OpWrapper}.
 * <p>
 *
 * @author Gabriel Selzer
 * @see OpWrappers
 * @see WriteYourOwnFunctionalType
 */
@Plugin(type = OpCollection.class)
public class WrapYourOwnFunctionalType {

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
		// See WriteYourOwnFunctionalType.demoStrangeComputer for impl.
		StrangeComputer<double[], double[], double[]> demoOp = opEnv //
			.op("demo.strangeComputer", opType, inputTypes, outType);

		// -- Print results -- //
		if (demoOp instanceof GenericTyped) {
			// demoOp can only implement GenericTyped if it was wrapped
			System.out.println("This is how you wrap your own Functional Type!");
		}
		else {
			System.out.println("ERROR: Op was not wrapped.");
		}
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

	/**
	 * {@link OpWrapper} to wrap {@link StrangeComputer}
	 * 
	 * @author Gabriel Selzer
	 * @param <O> - the output
	 * @param <I2> - the second parameter
	 * @param <I3> - the third parameter
	 * @see StrangeComputer
	 */
	@Plugin(type = OpWrapper.class)
	public static class StrangeComputerOpWrapper<O, I2, I3> implements
		OpWrapper<StrangeComputer<O, I2, I3>>
	{

		@Override
		public StrangeComputer<O, I2, I3> wrap(final StrangeComputer<O, I2, I3> op,
			final Type reifiedType)
		{
			class GenericTypedStrangeComputer implements StrangeComputer<O, I2, I3>,
				GenericTyped
			{

				@Override
				public void compute(O out, I2 in2, I3 in3) {
					op.compute(out, in2, in3);
				}

				@Override
				public Type getType() {
					return reifiedType;
				}
			}
			return new GenericTypedStrangeComputer();
		}
	}

}
