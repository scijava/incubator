package org.scijava.ops.image.simplify;

import java.util.function.Function;

import com.google.common.primitives.UnsignedLong;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.ByteType;
import net.imglib2.type.numeric.integer.LongType;
import net.imglib2.type.numeric.integer.ShortType;
import net.imglib2.type.numeric.integer.Unsigned128BitType;
import net.imglib2.type.numeric.integer.Unsigned12BitType;
import net.imglib2.type.numeric.integer.Unsigned2BitType;
import net.imglib2.type.numeric.integer.Unsigned4BitType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.integer.UnsignedLongType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;
import org.scijava.function.Computers;
import org.scijava.ops.spi.OpDependency;

public class RealTypeSimplifiers <T extends RealType<T>, U extends RealType<U>> {

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.simplify", type=Function
	 */
	public final Function<T, RealType<?>> realTypeSimplifier = //
			t -> t;

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T, BitType> bitTypeFocuser = //
			t -> new BitType(t.getRealDouble() != 0);

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T, Unsigned2BitType> u2bitTypeFocuser = //
			t -> new Unsigned2BitType((long) t.getRealDouble());

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T, Unsigned4BitType> u4bitTypeFocuser = //
			t -> new Unsigned4BitType((long) t.getRealDouble());

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , ByteType> byteTypeFocuser = //
			t -> new ByteType((byte) t.getRealDouble());

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , UnsignedByteType> uByteTypeFocuser = //
			t -> new UnsignedByteType((byte) t.getRealDouble());

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , Unsigned12BitType> u12BitTypeFocuser = //
			t -> new Unsigned12BitType((long) t.getRealDouble());

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , UnsignedShortType> uShortTypeFocuser = //
			t -> new UnsignedShortType((int) t.getRealDouble());

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , ShortType> shortTypeFocuser = //
			t -> new ShortType((short) t.getRealDouble());

	/**
	 * Converting to {@link UnsignedLongType} is tricky, so let's lean on a convert op.
	 *
	 * @param converter an Op tailored to converting {@code from} to an {@link UnsignedLongType}
	 * @param from      some {@link RealType}
	 * @return {@code from}, converted to a {@link UnsignedLongType}
	 * @implNote op names="engine.focus", type=Function
	 */
	public static <T extends RealType<T>> UnsignedLongType uLongTypeFocuser ( //
			@OpDependency(name = "convert.uint64") Computers.Arity1<T, UnsignedLongType> converter, //
			T from //
	) {
		UnsignedLongType to = new UnsignedLongType();
		converter.compute(from, to);
		return to;
	}

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , LongType> longTypeFocuser = //
			t -> new LongType((long) t.getRealDouble());

	/**
	 * Converting to {@link Unsigned128BitType} is tricky, so let's lean on a convert op.
	 *
	 * @param converter an Op tailored to converting {@code from} to an {@link Unsigned128BitType}
	 * @param from      some {@link RealType}
	 * @return {@code from}, converted to a {@link Unsigned128BitType}
	 * @implNote op names="engine.focus", type=Function
	 */
	public static <T extends RealType<T>> Unsigned128BitType u128BitTypeFocuser ( //
			@OpDependency(name = "convert.uint64") Computers.Arity1<T, Unsigned128BitType> converter, //
			T from //
	) {
		Unsigned128BitType to = new Unsigned128BitType();
		converter.compute(from, to);
		return to;
	}

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , FloatType> floatTypeFocuser = //
			t -> new FloatType(t.getRealFloat());

	/**
	 * @input type some {@link RealType}
	 * @output output
	 * @implNote op names="engine.focus", type=Function
	 */
	public final Function<T , DoubleType> doubleTypeFocuser = //
			t -> new DoubleType(t.getRealDouble());

}
