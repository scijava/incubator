package org.scijava.ops.engine.matcher;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;
import org.scijava.Priority;
import org.scijava.function.Computers;
import org.scijava.function.Producer;
import org.scijava.ops.engine.AbstractTestEnvironment;
import org.scijava.ops.spi.Op;
import org.scijava.plugin.Plugin;
import org.scijava.types.Any;
import org.scijava.types.TypeExtractor;

/**
 * Tests op matcher functionality with {@link Any} types.
 * 
 * @author Gabriel Selzer
 */
public class MatchingWithAnyTest extends AbstractTestEnvironment {

	@Test
	public void testAny() {

		NestedThing<String, Thing<String>> nthing = new NestedThing<>();
		Double e = ops.op("test.nestedAny").input(nthing).outType(Double.class).apply();

		Thing<Double> thing = new Thing<>();
		Double d = ops.op("test.any").input(thing).outType(Double.class).apply();

		assert d == 5.;
		assert e == 5.;

	}

	/**
	 * NOTE: this is where ops.run() and the Any paradigm fail. However, this can
	 * easily be avoided by making TypeExtractors for any class for which this kind
	 * of exception can happen.
	 */
	@Test(expected = ClassCastException.class)
	public void testExceptionalThing() {

		ExceptionalThing<Double> ething = new ExceptionalThing<>(0.5);
		Double d = ops.op("test.exceptionalAny").input(ething).outType(Double.class).apply();

	}

	@Plugin(type = TypeExtractor.class, priority = Priority.LOW)
	public static class ThingTypeExtractor implements TypeExtractor<Thing<?>> {

		@Override
		public Type reify(final Thing<?> o, final int n) {
			if (n != 0)
				throw new IndexOutOfBoundsException();

			return new Any();
		}

		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class<Thing<?>> getRawType() {
			return (Class) Thing.class;
		}

	}

	// TODO: Note that this wouldn't work for Computer -> Function because here
	// LiftFunctionToArrayTransformer is the first transformer which is asked for
	// source refs. This transformer doesn't support Any and would fail.
	// TODO: can we remove this test?
	@Test
	public void testRunAnyFunction1FromComputer2() {
		final int in1 = 11;
		final long in2 = 31;
		final StringContainer out = ops.op("test.integerAndLongAndNotAnyComputer").input(in1, in2).outType(StringContainer.class).apply();
		assertEquals(Long.toString(in1 + in2), out.getValue());
	}
	
}

@Plugin(type = Op.class, name = "test.functionAndLongToLong")
class FunctionAndLongToLong implements BiFunction<Function<Long, Long>, Long, Long> {

	@Override
	public Long apply(Function<Long, Long> t, Long u) {
		return t.apply(u);
	}
	
}

@Plugin(type = Op.class, name = "test.integerAndLongAndNotAnyComputer")
class IntegerAndLongAndNotAnyComputer implements Computers.Arity2<Integer, Long, StringContainer> {

	@Override
	public void compute(Integer in1, Long in2, StringContainer out) {
		out.setValue(Long.toString(in1 + in2));
	}
}

class StringContainer {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

@Plugin(type = Op.class, name = "create, create.stringContainer")
class StringContainerCreator implements Producer<StringContainer> {

	@Override
	public StringContainer create() {
		return new StringContainer();
	}
}

class Thing<U> {

	public double create(U u) {
		return 5.;
	}
}

class ExceptionalThing<U> {

	public ExceptionalThing(U u) {
		thing = u;
	};

	U thing;

	U getU() {
		return thing;
	}

	public double create(U u) {
		thing = u;
		return 5.;
	}
}

class NestedThing<U, V extends Thing<?>> {
	public double create(V u) {
		return 5.;
	}
}

@Plugin(type = Op.class, name = "test.any")
class ThingFunction implements Function<Thing<String>, Double> {

	@Override
	public Double apply(Thing<String> t) {
		return t.create("Hello");
	}

}

@Plugin(type = Op.class, name = "test.exceptionalAny")
class ExceptionalThingFunction implements Function<ExceptionalThing<String>, Double> {

	@Override
	public Double apply(ExceptionalThing<String> t) {
		String s = t.getU();
		return t.create("Hello");
	}

}

@Plugin(type = Op.class, name = "test.nestedAny")
class NestedThingFunction implements Function<NestedThing<String, Thing<String>>, Double> {

	@Override
	public Double apply(NestedThing<String, Thing<String>> t) {
		return 5.;
	}

}
