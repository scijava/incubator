package org.scijava.ops.image.simplify;

import java.util.function.Function;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.basictypeaccess.DataAccess;
import net.imglib2.img.basictypeaccess.array.ArrayDataAccess;
import net.imglib2.type.NativeType;
import net.imglib2.util.Util;
import org.scijava.function.Computers;
import org.scijava.ops.spi.OpDependency;

public class RAISimplifiers<T, R extends RandomAccessibleInterval<T>> {


	/**
	 * @input type some {@link RandomAccessibleInterval}
	 * @output output
	 * @implNote op names="engine.simplify", type=Function
	 */
	public final Function<R, RandomAccessibleInterval<T>> raiSimplifier = t -> t;

	/**
	 * @param copier an Op responsible for copying from simple to focused
	 * @param simple the {@link RandomAccessibleInterval} to be focused
	 * @return a focused {@link ArrayImg}
	 * @implNote op names="engine.focus", type=Function
	 */
	public static <N extends NativeType<N>> ArrayImg<N, ArrayDataAccess<?>> arrayImgFocuser(
			@OpDependency(name="copy.img") Computers.Arity1<RandomAccessibleInterval<N>, RandomAccessibleInterval<N>> copier,
			final RandomAccessibleInterval<N> simple) {
		ArrayImg<N, ?> output = new ArrayImgFactory<>(Util.getTypeFromInterval(simple)).create(simple);
		copier.compute(simple, output);
		return (ArrayImg<N, ArrayDataAccess<?>>) output;
	}


}
