
package org.scijava.opsdemo;

import java.lang.reflect.Field;

import org.scijava.Context;
import org.scijava.ops.OpDependency;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Producer;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

/**
 * How to declare an {@link Op} with an {@code OpDependency}
 * <p>
 * This mechanism allows Ops to make use of other Ops: this makes the codebase
 * DRYer and allows Ops to make use of the extensibility and flexibility of the
 * project. <strong>Every {@code OpDependency} will be automagically injected
 * before the {@code Op} is returned by the matcher; the caller of the Op is not
 * given any extra work.</strong> Necessary components of any
 * {@code OpDependency} include:
 * <ul>
 * <li>The {@code @OpDependency} annotation. This annotation allows the matcher
 * to discover the dependency.
 * <li>The {@code name} attribute of the annotation. This is necessary for
 * matching.
 * <li>The {@code type} of the {@link Field}. This tells the matcher the type an
 * Op must be able to satisfy for it to be injected.
 * </ul>
 * <p>
 * Note that {@code Op}s <b>must</b> be {@link Class}es in order to declare an
 * {@link OpDependency}. That is, {@link OpField}s <b>cannot</b> declare an
 * {@link OpDependency} (since a {@link Field} cannot declare a {@code Field}).
 *
 * @author Gabriel Selzer
 * @see OpDependency
 * @see DemoDependentOp
 * @see DemoDependency
 */
public class WriteOpWithDependencies {

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
		Producer<String> demoOp = opEnv.op("demo.dependentOp") //
			.input() // Producers have no inputs
			.outType(String.class) // Our op needs to return a String
			.producer(); // We want a Producer back.

		// -- Run the Op -- //
		String output = demoOp.create();

		// -- Print results -- //
		System.out.println(demoOp.create());
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();
		run(opEnv);
	}

}

/**
 * A barebones {@link Op} defined as a Scijava {@link Plugin}. Note the
 * following:
 * <ul>
 * <li>The {@code @Plugin} annotation makes the Op discoverable and available to
 * the {@link OpEnvironment}
 * <li>The Op implements {@link Producer}, one of the many vanilla Op types.
 * <li><strong>The {@link OpDependency} allows this {@code Op} to use the
 * functionality of other {@code Op}s </strong>. Every {@code OpDependency} will
 * be automagically injected before the {@code Op} is returned by the matcher.
 * </ul>
 * 
 * @author Gabriel Selzer
 * @see Producer
 */
@Plugin(type = Op.class, name = "demo.dependentOp")
@Parameter(key = "output")
class DemoDependentOp implements Producer<String> {

	@OpDependency(name = "demo.dependency")
	public Producer<String> neededOp;

	@Override
	public String create() {
		return "This is how you create an Op " + neededOp.create();
	}

}

/**
 * A barebones {@link Op} defined as a Scijava {@link Plugin}. Nothing special
 * to see here.
 * 
 * @author Gabriel Selzer
 * @see Producer
 */
@Plugin(type = Op.class, name = "demo.dependency")
@Parameter(key = "output")
class DemoDependency implements Producer<String> {

	@Override
	public String create() {
		return "with a dependency!";
	}

}
