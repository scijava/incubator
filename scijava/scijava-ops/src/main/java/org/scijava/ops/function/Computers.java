/*
 * This is autogenerated source code -- DO NOT EDIT. Instead, edit the
 * corresponding template in templates/ and rerun bin/generate.groovy.
 */

package org.scijava.ops.function;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.scijava.ops.OpService;
import org.scijava.types.Nil;
import org.scijava.param.Mutable;
import org.scijava.types.Types;

/**
 * Container class for computer-style functional interfaces at various
 * <a href="https://en.wikipedia.org/wiki/Arity">arities</a>.
 * <p>
 * A computer has functional method {@code compute} with a number of arguments
 * corresponding to the arity, plus an additional argument for the preallocated
 * output to be populated by the computation.
 * </p>
 * <p>
 * Each computer interface implements a corresponding {@link Consumer}-style
 * interface (see {@link Consumers}) with arity+1; the consumer's {@code accept}
 * method simply delegates to {@code compute}. This pattern allows computer ops
 * to be used directly as consumers as needed.
 * </p>
 *
 * @author Curtis Rueden
 * @author Gabriel Selzer
 */
public final class Computers {

	private Computers() {
		// NB: Prevent instantiation of utility class.
	}

	// -- BEGIN TEMP --

	/**
	 * All known computer types and their arities. The entries are sorted by
	 * arity, i.e., the {@code i}-th entry has an arity of {@code i}.
	 */
	public static final BiMap<Class<?>, Integer> ALL_COMPUTERS;

	static {
		final Map<Class<?>, Integer> computers = new HashMap<>();
		computers.put(Arity0.class, 0);
		computers.put(Arity1.class, 1);
		computers.put(Arity2.class, 2);
		computers.put(Arity3.class, 3);
		computers.put(Arity4.class, 4);
		computers.put(Arity5.class, 5);
		computers.put(Arity6.class, 6);
		computers.put(Arity7.class, 7);
		computers.put(Arity8.class, 8);
		computers.put(Arity9.class, 9);
		computers.put(Arity10.class, 10);
		computers.put(Arity11.class, 11);
		computers.put(Arity12.class, 12);
		computers.put(Arity13.class, 13);
		computers.put(Arity14.class, 14);
		computers.put(Arity15.class, 15);
		computers.put(Arity16.class, 16);
		ALL_COMPUTERS = ImmutableBiMap.copyOf(computers);
	}

	/**
	 * @return {@code true} if the given type is a {@link #ALL_COMPUTERS known}
	 *         computer type, {@code false} otherwise. <br>
	 *         Note that only the type itself and not its type hierarchy is
	 *         considered.
	 * @throws NullPointerException If {@code type} is {@code null}.
	 */
	public static boolean isComputer(Type type) {
		return ALL_COMPUTERS.containsKey(Types.raw(type));
	}

	@SuppressWarnings("unchecked")
	public static <O> Computers.Arity0<O> match(final OpService ops, final String opName, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity0.class, outType);
	}

	@SuppressWarnings("unchecked")
	public static <I, O> Computers.Arity1<I, O> match(final OpService ops, final String opName, final Nil<I> inType, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity1.class, outType, inType);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, O> Computers.Arity2<I1, I2, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity2.class, outType, in1Type, in2Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, O> Computers.Arity3<I1, I2, I3, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity3.class, outType, in1Type, in2Type, in3Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, O> Computers.Arity4<I1, I2, I3, I4, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity4.class, outType, in1Type, in2Type, in3Type, in4Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, O> Computers.Arity5<I1, I2, I3, I4, I5, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity5.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, O> Computers.Arity6<I1, I2, I3, I4, I5, I6, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity6.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, O> Computers.Arity7<I1, I2, I3, I4, I5, I6, I7, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity7.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Computers.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity8.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Computers.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity9.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Computers.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity10.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O> Computers.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity11.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O> Computers.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity12.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O> Computers.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity13.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O> Computers.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<I14> in14Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity14.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type, in14Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O> Computers.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<I14> in14Type, final Nil<I15> in15Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity15.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type, in14Type, in15Type);
	}

	@SuppressWarnings("unchecked")
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O> Computers.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O> match(final OpService ops, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<I14> in14Type, final Nil<I15> in15Type, final Nil<I16> in16Type, final Nil<O> outType)
	{
		return matchHelper(ops, opName, Computers.Arity16.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type, in14Type, in15Type, in16Type);
	}

	@SuppressWarnings({ "unchecked" })
	private static <T> T matchHelper(final OpService ops, final String opName, final Class<T> opClass, final Nil<?> outType, final Nil<?>... inTypes)
	{
		final Type[] types = new Type[inTypes.length + 1];
		for (int i = 0; i < inTypes.length; i++)
			types[i] = inTypes[i].getType();
		types[types.length - 1] = outType.getType();
		final Type specialType = Types.parameterize(opClass, types);
		final Nil<?>[] nils = new Nil[inTypes.length + 1];
		System.arraycopy(inTypes, 0, nils, 0, inTypes.length);
		nils[nils.length - 1] = outType;
		return (T) ops.findOp(opName, Nil.of(specialType), nils, outType);
	}

	// -- END TEMP --

	@FunctionalInterface
	public interface Arity0<O> extends
		Consumer<O>
	{

		void compute(@Mutable O out);

		@Override
		default void accept(final O out)
		{
			compute(out);
		}
	}

	@FunctionalInterface
	public interface Arity1<I, O> extends
		BiConsumer<I, O>
	{

		void compute(I in, @Mutable O out);

		@Override
		default void accept(final I in, final O out)
		{
			compute(in, out);
		}
	}

	@FunctionalInterface
	public interface Arity2<I1, I2, O> extends
		Consumers.Arity3<I1, I2, O>
	{

		void compute(I1 in1, I2 in2, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final O out)
		{
			compute(in1, in2, out);
		}
	}

	@FunctionalInterface
	public interface Arity3<I1, I2, I3, O> extends
		Consumers.Arity4<I1, I2, I3, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final O out)
		{
			compute(in1, in2, in3, out);
		}
	}

	@FunctionalInterface
	public interface Arity4<I1, I2, I3, I4, O> extends
		Consumers.Arity5<I1, I2, I3, I4, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final O out)
		{
			compute(in1, in2, in3, in4, out);
		}
	}

	@FunctionalInterface
	public interface Arity5<I1, I2, I3, I4, I5, O> extends
		Consumers.Arity6<I1, I2, I3, I4, I5, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final O out)
		{
			compute(in1, in2, in3, in4, in5, out);
		}
	}

	@FunctionalInterface
	public interface Arity6<I1, I2, I3, I4, I5, I6, O> extends
		Consumers.Arity7<I1, I2, I3, I4, I5, I6, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, out);
		}
	}

	@FunctionalInterface
	public interface Arity7<I1, I2, I3, I4, I5, I6, I7, O> extends
		Consumers.Arity8<I1, I2, I3, I4, I5, I6, I7, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, out);
		}
	}

	@FunctionalInterface
	public interface Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O> extends
		Consumers.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, out);
		}
	}

	@FunctionalInterface
	public interface Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> extends
		Consumers.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, out);
		}
	}

	@FunctionalInterface
	public interface Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> extends
		Consumers.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, I10 in10, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final I10 in10, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, out);
		}
	}

	@FunctionalInterface
	public interface Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O> extends
		Consumers.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, I10 in10, I11 in11, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final I10 in10, final I11 in11, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, out);
		}
	}

	@FunctionalInterface
	public interface Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O> extends
		Consumers.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, I10 in10, I11 in11, I12 in12, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final I10 in10, final I11 in11, final I12 in12, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, out);
		}
	}

	@FunctionalInterface
	public interface Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O> extends
		Consumers.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, I10 in10, I11 in11, I12 in12, I13 in13, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final I10 in10, final I11 in11, final I12 in12, final I13 in13, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, out);
		}
	}

	@FunctionalInterface
	public interface Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O> extends
		Consumers.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, I10 in10, I11 in11, I12 in12, I13 in13, I14 in14, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final I10 in10, final I11 in11, final I12 in12, final I13 in13, final I14 in14, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, out);
		}
	}

	@FunctionalInterface
	public interface Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O> extends
		Consumers.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, I10 in10, I11 in11, I12 in12, I13 in13, I14 in14, I15 in15, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final I10 in10, final I11 in11, final I12 in12, final I13 in13, final I14 in14, final I15 in15, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, out);
		}
	}

	@FunctionalInterface
	public interface Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O> extends
		Consumers.Arity17<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O>
	{

		void compute(I1 in1, I2 in2, I3 in3, I4 in4, I5 in5, I6 in6, I7 in7, I8 in8, I9 in9, I10 in10, I11 in11, I12 in12, I13 in13, I14 in14, I15 in15, I16 in16, @Mutable O out);

		@Override
		default void accept(final I1 in1, final I2 in2, final I3 in3, final I4 in4, final I5 in5, final I6 in6, final I7 in7, final I8 in8, final I9 in9, final I10 in10, final I11 in11, final I12 in12, final I13 in13, final I14 in14, final I15 in15, final I16 in16, final O out)
		{
			compute(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, out);
		}
	}
}
