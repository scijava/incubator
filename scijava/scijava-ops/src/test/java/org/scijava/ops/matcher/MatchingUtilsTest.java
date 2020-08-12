/*
 * #%L
 * SciJava Operations: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2018 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.ops.matcher;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;
import org.scijava.types.Nil;
import org.scijava.types.Types;

public class MatchingUtilsTest {

	private void assertAll(Class<?> from, boolean condition, Nil<?>... tos) {
		for (Nil<?> to : tos) {
			assertAll(from, condition, to.getType());
		}
	}

	private void assertAll(Class<?> from, boolean condition, Type... tos) {
		for (Type to : tos) {
			if (to instanceof ParameterizedType) {
				assertTrue(MatchingUtils.checkGenericAssignability(from, (ParameterizedType) to, false) == condition);
			} else {
				assertTrue(Types.isAssignable(from, to, new HashMap<TypeVariable<?>, Type>()) == condition);
			}
		}
	}
	
	@Test
	public <E, N extends Number> void genericAssignabilityVarToVar() {
		abstract class Single<I> implements Supplier<I> {
		}
		abstract class SingleBounded<I extends Number> implements Supplier<I> {
		}
		Nil<Supplier<E>> y1 = new Nil<Supplier<E>>() {
		};
		Nil<Supplier<N>> y2 = new Nil<Supplier<N>>() {
		};
		Nil<Double> n1 = new Nil<>() {
		};

		assertAll(Single.class, true, y1, y2);
		assertAll(Single.class, false, n1);
		
		assertAll(SingleBounded.class, true, y2);
		assertAll(SingleBounded.class, false, y1, n1);
	}

	@Test
	public void genericAssignabilitySingleVar() {
		abstract class Single<I> implements Supplier<I> {
		}
		Nil<Supplier<Double>> y1 = new Nil<Supplier<Double>>() {
		};
		Nil<Supplier<Number>> y2 = new Nil<Supplier<Number>>() {
		};
		Nil<Double> n1 = new Nil<>() {
		};

		assertAll(Single.class, true, y1, y2);
		assertAll(Single.class, false, n1);
	}

	@Test
	public void genericAssignabilitySingleVarBounded() {
		abstract class SingleBounded<I extends Number> implements Supplier<I> {
		}

		Nil<Supplier<Double>> y1 = new Nil<Supplier<Double>>() {
		};
		Nil<Double> n1 = new Nil<>() {
		};
		Nil<String> n2 = new Nil<String>() {
		};

		assertAll(SingleBounded.class, true, y1);
		assertAll(SingleBounded.class, false, n1, n2);
	}

	@Test
	public void genericAssignabilitySingleVarBoundedUsedNested() {
		abstract class SingleVarBoundedUsedNested<I extends Number> implements Supplier<List<I>> {
		}

		Nil<Double> n1 = new Nil<>() {
		};
		Nil<String> n2 = new Nil<String>() {
		};
		Nil<Supplier<Double>> n3 = new Nil<Supplier<Double>>() {
		};
		Nil<Supplier<List<String>>> n4 = new Nil<Supplier<List<String>>>() {
		};
		Nil<Supplier<List<Double>>> y1 = new Nil<Supplier<List<Double>>>() {
		};

		assertAll(SingleVarBoundedUsedNested.class, true, y1);
		assertAll(SingleVarBoundedUsedNested.class, false, n1, n2, n3, n4);
	}

	@Test
	public void genericAssignabilitySingleVarBoundedUsedNestedAndOther() {
		abstract class SingleVarBoundedUsedNestedAndOther<I extends Number> implements Function<List<I>, Double> {
		}
		abstract class SingleVarBoundedUsedNestedAndOtherNested<I extends Number>
				implements Function<List<I>, List<Double>> {
		}
		abstract class SingleVarBoundedUsedNestedAndOtherNestedWildcard<I extends Number>
				implements Function<List<I>, List<? extends Number>> {
		}

		Nil<Function<List<Double>, Double>> y1 = new Nil<>() {
		};
		Nil<Function<Double, Double>> n1 = new Nil<>() {
		};
		Nil<Function<List<String>, Double>> n2 = new Nil<>() {
		};
		Nil<Function<List<Double>, String>> n3 = new Nil<>() {
		};

		assertAll(SingleVarBoundedUsedNestedAndOther.class, true, y1);
		assertAll(SingleVarBoundedUsedNestedAndOther.class, false, n1, n2, n3);

		Nil<Function<List<Double>, List<Double>>> y2 = new Nil<>() {
		};
		Nil<Function<Double, List<Double>>> n4 = new Nil<>() {
		};
		Nil<Function<List<String>, List<Double>>> n5 = new Nil<Function<List<String>, List<Double>>>() {
		};
		Nil<Function<List<Double>, List<String>>> n6 = new Nil<>() {
		};

		assertAll(SingleVarBoundedUsedNestedAndOtherNested.class, true, y2);
		assertAll(SingleVarBoundedUsedNestedAndOtherNested.class, false, n4, n5, n6);

		Nil<Function<Double, List<Double>>> n7 = new Nil<>() {
		};
		Nil<Function<List<String>, List<Double>>> n8 = new Nil<Function<List<String>, List<Double>>>() {
		};
		Nil<Function<List<Double>, List<String>>> n9 = new Nil<>() {
		};
		Nil<Function<List<Double>, List<Double>>> n10 = new Nil<>() {
		};
		Nil<Function<List<Double>, List<Integer>>> n11 = new Nil<>() {
		};
		Nil<Function<List<Double>, List<? extends Number>>> y3 = new Nil<>() {
		};

		assertAll(SingleVarBoundedUsedNestedAndOtherNestedWildcard.class, false, n7, n8, n9, n10, n11);
		assertAll(SingleVarBoundedUsedNestedAndOtherNestedWildcard.class, true, y3);
	}

	@Test
	public void genericAssignabilitySingleVarNestedBoundNestedAndOther() {
		abstract class SingleVarBoundedNestedAndOther<I extends Iterable<String>> implements Function<I, Double> {
		}
		abstract class SingleVarBoundedNestedWildcardAndOther<I extends Iterable<? extends Number>>
				implements Function<I, List<Double>> {
		}

		Nil<Function<List<String>, Double>> y1 = new Nil<>() {
		};
		Nil<Function<Iterable<String>, Double>> y2 = new Nil<>() {
		};
		Nil<Function<Double, Double>> n1 = new Nil<>() {
		};
		Nil<Function<List<String>, String>> n2 = new Nil<Function<List<String>, String>>() {
		};
		Nil<Function<Iterable<Double>, Double>> n3 = new Nil<>() {
		};

		assertAll(SingleVarBoundedNestedAndOther.class, true, y1, y2);
		assertAll(SingleVarBoundedNestedAndOther.class, false, n1, n2, n3);

		Nil<Function<List<Integer>, List<Double>>> y3 = new Nil<>() {
		};
		Nil<Function<List<Double>, List<Double>>> y4 = new Nil<>() {
		};
		Nil<Function<Iterable<Double>, List<Double>>> y5 = new Nil<>() {
		};
		Nil<Function<List<Integer>, Double>> n4 = new Nil<>() {
		};
		Nil<Function<List<Double>, List<Integer>>> n5 = new Nil<>() {
		};
		Nil<Function<Iterable<Double>, Iterable<Double>>> n6 = new Nil<>() {
		};
		Nil<Function<Integer, List<Double>>> n7 = new Nil<>() {
		};
		Nil<Function<List<String>, List<Double>>> n8 = new Nil<Function<List<String>, List<Double>>>() {
		};

		assertAll(SingleVarBoundedNestedWildcardAndOther.class, true, y3, y4, y5);
		assertAll(SingleVarBoundedNestedWildcardAndOther.class, false, n4, n5, n6, n7, n8);
	}

	@Test
	public void genericAssignabilitySingleVarMultipleOccurence() {
		abstract class SingleVarBoundedNestedMultipleOccurence<I extends Iterable<String>> implements Function<I, I> {
		}

		abstract class SingleVarBoundedNestedWildcardMultipleOccurence<I extends Iterable<? extends Number>>
				implements Function<I, I> {
		}

		abstract class SingleVarBoundedNestedWildcardMultipleOccurenceUsedNested<I extends Iterable<? extends Number>>
				implements Function<I, List<I>> {
		}

		Nil<Function<List<String>, List<String>>> y1 = new Nil<Function<List<String>, List<String>>>() {
		};
		Nil<Function<Iterable<String>, Iterable<String>>> y2 = new Nil<>() {
		};
		Nil<Function<List<String>, List<Integer>>> n1 = new Nil<Function<List<String>, List<Integer>>>() {
		};
		Nil<Function<List<String>, Double>> n2 = new Nil<Function<List<String>, Double>>() {
		};

		assertAll(SingleVarBoundedNestedMultipleOccurence.class, true, y1, y2);
		assertAll(SingleVarBoundedNestedMultipleOccurence.class, false, n1, n2);

		Nil<Function<List<Double>, List<Double>>> y3 = new Nil<>() {
		};
		Nil<Function<Iterable<Double>, Iterable<Double>>> y4 = new Nil<>() {
		};
		Nil<Function<Iterable<Double>, Iterable<Integer>>> n3 = new Nil<>() {
		};
		Nil<Function<List<String>, Integer>> n4 = new Nil<Function<List<String>, Integer>>() {
		};

		assertAll(SingleVarBoundedNestedWildcardMultipleOccurence.class, true, y3, y4);
		assertAll(SingleVarBoundedNestedWildcardMultipleOccurence.class, false, n3, n4);

		Nil<Function<List<Double>, Iterable<List<Double>>>> n5 = new Nil<>() {
		};
		Nil<Function<Iterable<Double>, List<Iterable<Double>>>> y5 = new Nil<>() {
		};

		assertAll(SingleVarBoundedNestedWildcardMultipleOccurenceUsedNested.class, true, y5);
		assertAll(SingleVarBoundedNestedWildcardMultipleOccurenceUsedNested.class, false, n5);
		
		abstract class SingleVarMultipleOccurenceUsedNested<I> implements Function<I, List<I>> {}
		
		Nil<Function<Integer, List<Number>>> n6 = new Nil<>() {
		};
		assertAll(SingleVarMultipleOccurenceUsedNested.class, false, n6);
	}

	@Test
	public void genericAssignabilityDoubleVar() {
		abstract class DoubleVar<I, B> implements Function<I, B> {
		}

		abstract class DoubleVarBounded<I extends List<String>, B extends Number> implements Function<I, B> {
		}

		Nil<Function<List<String>, List<String>>> y1 = new Nil<Function<List<String>, List<String>>>() {
		};
		Nil<Function<Iterable<String>, Iterable<String>>> y2 = new Nil<>() {
		};
		Nil<Function<List<String>, List<Integer>>> y3 = new Nil<Function<List<String>, List<Integer>>>() {
		};
		Nil<Function<List<String>, Double>> y4 = new Nil<Function<List<String>, Double>>() {
		};

		assertAll(DoubleVar.class, true, y1, y2, y3, y4);

		Nil<Function<List<String>, Double>> y5 = new Nil<Function<List<String>, Double>>() {
		};
		Nil<Function<List<String>, Float>> y6 = new Nil<Function<List<String>, Float>>() {
		};
		Nil<Function<Iterable<String>, Double>> n1 = new Nil<>() {
		};
		Nil<Function<List<Double>, Integer>> n2 = new Nil<>() {
		};
		Nil<Function<List<String>, String>> n3 = new Nil<Function<List<String>, String>>() {
		};

		assertAll(DoubleVarBounded.class, true, y5, y6);
		assertAll(DoubleVarBounded.class, false, n1, n2, n3);
	}

	@Test
	public void genericAssignabilityDoubleVarDepending() {
		abstract class BExtendsI<I extends Iterable<? extends Number>, B extends I> implements Function<I, B> {
		}

		abstract class IBoundedByN<N extends Number, I extends Iterable<N>> implements BiFunction<I, I, N> {
		}

		Nil<Function<List<Integer>, List<Integer>>> y1 = new Nil<>() {
		};
		Nil<Function<Iterable<Integer>, List<Integer>>> y2 = new Nil<>() {
		};
		Nil<Function<Iterable<Integer>, Iterable<Integer>>> y3 = new Nil<>() {
		};
		Nil<Function<List<String>, List<Integer>>> n1 = new Nil<Function<List<String>, List<Integer>>>() {
		};
		Nil<Function<List<String>, Double>> n2 = new Nil<Function<List<String>, Double>>() {
		};
		Nil<Function<List<Integer>, List<Double>>> n3 = new Nil<>() {
		};
		Nil<Function<Integer, List<Integer>>> n4 = new Nil<>() {
		};
		Nil<Function<Iterable<Integer>, Integer>> n5 = new Nil<>() {
		};
		
		assertAll(BExtendsI.class, true, y1, y2, y3);
		assertAll(BExtendsI.class, false, n1, n2, n3, n4, n5);

		Nil<BiFunction<List<Integer>, List<Integer>, Integer>> y4 = new Nil<>() {
		};
		Nil<BiFunction<Iterable<Integer>, Iterable<Integer>, Integer>> y5 = new Nil<>() {
		};
		Nil<BiFunction<List<Integer>, List<Integer>, Double>> n6 = new Nil<>() {
		};
		Nil<BiFunction<Iterable<Double>, Iterable<Integer>, Integer>> n7 = new Nil<>() {
		};
		Nil<BiFunction<Iterable<Integer>, List<Integer>, Integer>> n8 = new Nil<>() {
		};
		Nil<BiFunction<Iterable<String>, List<String>, String>> n9 = new Nil<>() {
		};

		assertAll(IBoundedByN.class, true, y4, y5);
		assertAll(IBoundedByN.class, false, n6, n7, n8, n9);
	}

	@Test
	public void genericAssignabilityDoubleVarDependingImplicitelyBounded() {
		abstract class IBoundedByNImplicitely<N extends Number, I extends Iterable<N>>
				implements BiFunction<I, I, List<String>> {
		}

		Nil<BiFunction<Iterable<Double>, Iterable<Double>, List<String>>> y1 = new Nil<>() {
		};
		
		assertAll(IBoundedByNImplicitely.class, true, y1);
	}

	@Test
	public void genericAssignabilityDoubleVarBoundedAndWildcard() {
		abstract class DoubleVarBoundedAndWildcard<M extends Number, I extends Iterable<? extends Number>>
				implements BiFunction<I, I, Iterable<M>> {
		}

		Nil<BiFunction<Iterable<Double>, Double, Iterable<Double>>> n1 = new Nil<>() {
		};
		Nil<BiFunction<Double, Iterable<Double>, Iterable<Double>>> n2 = new Nil<>() {
		};
		Nil<BiFunction<List<Float>, List<Number>, Iterable<Double>>> n3 = new Nil<>() {
		};
		Nil<BiFunction<Iterable<Double>, List<Double>, Iterable<Double>>> n4 = new Nil<>() {
		};
		Nil<BiFunction<List<Double>, List<Double>, List<Double>>> n5 = new Nil<>() {
		};
		Nil<BiFunction<List<Integer>, List<Double>, Iterable<Double>>> n6 = new Nil<>() {
		};
		Nil<BiFunction<List<Double>, List<Double>, Iterable<Double>>> y1 = new Nil<>() {
		};
		Nil<BiFunction<Iterable<Double>, Iterable<Double>, Iterable<Double>>> y2 = new Nil<>() {
		};

		assertAll(DoubleVarBoundedAndWildcard.class, true, y1, y2);
		assertAll(DoubleVarBoundedAndWildcard.class, false, n1, n2, n3, n4, n5, n6);
	}
	
	@Test
	public void genericAssignabilityWildcards() {
		abstract class Wildcards implements Function<List<? extends Number>, List<? extends Number>> {
		}

		Nil<Function<List<? extends Number>, List<? extends Number>>> y1 = new Nil<>() {
		};
		Nil<Function<Iterable<Integer>, Iterable<Double>>> n1 = new Nil<>() {
		};
		Nil<Function<List<Double>, List<Integer>>> n2 = new Nil<>() {
		};
		Nil<Function<List<Double>, List<Double>>> n3 = new Nil<>() {
		};
		Nil<Function<Iterable<Double>, Iterable<Double>>> n4 = new Nil<>() {
		};

		assertAll(Wildcards.class, true, y1);
		assertAll(Wildcards.class, false, n1, n2, n3, n4);
	}
	
	@Test
	public void genericAssignabilityWildcardExtendingTypeVar() {
		abstract class StrangeConsumer<T extends Number> implements
			BiConsumer<List<? extends T>, T>
		{}
		
		Nil<BiConsumer<List<? extends Number>, Number>> y1 = new Nil<>() {};
		Nil<BiConsumer<List<? extends Integer>, Integer>> y2 = new Nil<>() {};
		Nil<BiConsumer<List<? extends Number>, ? extends Number>> y3 = new Nil<>() {};

		Nil<BiConsumer<List<? extends Integer>, Double>> n1 = new Nil<>() {};

		assertAll(StrangeConsumer.class, true, y1, y2, y3);
		assertAll(StrangeConsumer.class, false, n1);
		
	}

	/**
	 * Suppose we have a
	 *
	 * <pre>
	 * class Foo&lt;I extends Number&gt implements Function&lt;I[], Double&gt;
	 * </pre>
	 *
	 * It is legal to write
	 *
	 * <pre>
	 *
	 * Function&lt;Double[], Double[]&gt; fooFunc = new Foo&lt;&gt;();
	 * </pre>
	 *
	 * If we instead have a
	 *
	 * <pre>
	 * class Bar implements Function&lt;O[], Double&gt;
	 * </pre>
	 *
	 * where <code>O extends Number</code>, is <strong>not</strong> legal to write
	 *
	 * <pre>
	 *
	 * Function&lt;Double[], Double[]&gt; barFunc = new Bar&lt;&gt;();
	 * </pre>
	 *
	 * @param <O>
	 */
	@Test
	public <O extends Number> void testGenericArrayFunction() {
		class Foo<I extends Number> implements Function<I[], Double> {
			@Override
			public Double apply(I[] t) {
				// TODO Auto-generated method stub
				return null;
			}
		}

		class Bar implements Function<O[], Double> {
			@Override
			public Double apply(O[] t) {
				// TODO Auto-generated method stub
				return null;
			}
		}
		Nil<Function<Double[], Double>> doubleFunction = new Nil<>() {};
		assertAll(Foo.class, true, doubleFunction);
		assertAll(Bar.class, false, doubleFunction);
	}

	@Test
	public void testGenericArrayToWildcardWithinParameterizedType() {
		abstract class Foo<T extends Number> implements List<T[]> {}
		final Nil<List<? extends Double[]>> upperType = new Nil<>() {};
		final Nil<List<? super Double[]>> lowerType = new Nil<>() {};

		// Since it is legal to write
		// List<? extends Double[]> list = new Foo<>() {...};
		// assertAll must return true
		assertAll(Foo.class, true, upperType, lowerType);
	}

	@Test(expected = NullPointerException.class)
	public void testIsAssignableNullToNull() {
		MatchingUtils.checkGenericAssignability(null, null, false);
	}

	@Test(expected = NullPointerException.class)
	public void testIsAssignableClassToNull() {
		MatchingUtils.checkGenericAssignability(Object.class, null, false);
	}

	@Test
	public <T extends Number> void testIsAssignableT() {
		final Type t = new Nil<T>() {
		}.getType();
		final Type listT = new Nil<List<T>>() {
		}.getType();
		final Type listNumber = new Nil<List<Number>>() {
		}.getType();
		final Type listInteger = new Nil<List<Integer>>() {
		}.getType();
		final Type listExtendsNumber = new Nil<List<? extends Number>>() {
		}.getType();

		assertAll(List.class, true, listT, listNumber, listInteger, listExtendsNumber);
		assertAll(List.class, false, t);
	}
	
	class Thing<T> {}
	
	class StrangeThing<N extends Number, T> extends Thing<T> {}
	
	/**
	 * {@link MatchingUtils#checkGenericOutputsAssignability(Type[], Type[], HashMap)} not yet fully
	 * implemented. If this is done, all the tests below should not fail.
	 */
	@Test
	public <N> void testOutputAssignability() {
//		Nil<N> n = new Nil<N>() {};
//		Nil<List<N>> ln = new Nil<List<N>>() {};
//		Nil<List<? extends Number>> lWildNum = new Nil<List<? extends Number>>() {};
//		Nil<List<Number>> lNum = new Nil<List<Number>>() {};
//		Nil<List<?>> lwild = new Nil<List<?>>() {};
//		
//		HashMap<TypeVariable<?>, TypeVarInfo> typeBounds = new HashMap<>();
//		assertTrue(-1 == Types.isApplicable(new Type[]{Integer.class}, new Type[]{n.getType()}, typeBounds));
//		Type[] toOuts = new Type[]{lWildNum.getType()};
//		Type[] fromOuts = new Type[]{ln.getType()};
//		assertTrue(-1 == MatchingUtils.checkGenericOutputsAssignability(fromOuts, toOuts, typeBounds));
//		
//		toOuts = new Type[]{lNum.getType()};
//		assertTrue(-1 == MatchingUtils.checkGenericOutputsAssignability(fromOuts, toOuts, typeBounds));
//		
//		toOuts = new Type[]{lwild.getType()};
//		assertTrue(-1 == MatchingUtils.checkGenericOutputsAssignability(fromOuts, toOuts, typeBounds));
//		
//		typeBounds = new HashMap<>();
//		assertTrue(-1 == Types.isApplicable(new Type[]{String.class}, new Type[]{n.getType()}, typeBounds));
//		toOuts = new Type[]{lWildNum.getType()};
//		fromOuts = new Type[]{ln.getType()};
//		assertFalse(-1 == MatchingUtils.checkGenericOutputsAssignability(fromOuts, toOuts, typeBounds));
	}
}
