
package org.scijava.opsdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.scijava.Context;
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
 * How to declare an {@link Op} as an {@link OpField}
 * <p>
 * Requirements of any Op written as a {@code OpField} include:
 * <ul>
 * <li>The {@code @OpField} annotation. This annotation allows the matcher to
 * discover the {@code Op}.
 * <li>The {@code name} attribute of the annotation. This is necessary for
 * matching.
 * <li>The {@link Type} of the {@code Field} must be a
 * {@code FunctionalInterface}. This gives the matcher the generic type of the
 * {@code Op}, which is necessary for matching.
 * </ul>
 * <p>
 * Note that the {@link Class} owning the {@link Field} must declare itself as
 * an {@link OpCollection}.
 *
 * @author Gabriel Selzer
 * @see WriteOpAsField#demoOpField
 * @see Computers
 * @see Functions
 * @see Inplaces
 */
@Plugin(type = OpCollection.class)
public class WriteOpAsField {

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
		// -- Build the Op -- //
		Producer<String> demoOp = opEnv.op("demo.opField") //
			.input() // Producers have no inputs
			.outType(String.class) // Our op needs to return a String
			.producer(); // We want a Producer back

		// -- Run the Op -- //
		String output = demoOp.create();
		
		// -- Print results -- //
		System.out.println(output);
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

	/**
	 * A barebones {@link Op} defined as an {@link OpField}. Note the following:
	 * <ul>
	 * <li>The {@code @OpField} annotation, along with the {@Plugin} annotation on
	 * the owning {@link Class}, makes the Op discoverable and available to the
	 * {@link OpEnvironment}
	 * <li>The Op implements {@link Producer}, one of the many vanilla Op types.
	 * </ul>
	 */
	@OpField(names = "demo.opField")
	public final Producer<String> demoOpField =
		() -> "This is how you create an Op as a Field!";

}
