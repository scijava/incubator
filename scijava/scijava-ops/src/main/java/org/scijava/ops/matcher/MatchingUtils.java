/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2017 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, Max Planck
 * Institute of Molecular Cell Biology and Genetics, University of
 * Konstanz, and KNIME GmbH.
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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.scijava.types.Any;
import org.scijava.types.Nil;
import org.scijava.types.Types;
import org.scijava.types.Types.TypeVarFromParameterizedTypeInfo;
import org.scijava.types.Types.TypeVarInfo;
import org.scijava.types.inference.TypeInference;

public final class MatchingUtils {

	private MatchingUtils() {
		// prevent instantiation of utility class
	}

	/**
	 * Checks for raw assignability. TODO This method is not yet fully
	 * implemented. The correct behavior should be as follows. Suppose we have a
	 * generic typed method like:
	 *
	 * <pre>
	 *public static &lt;N&gt; List&lt;N&gt; foo(N in) {
	 *	...
	 *}
	 * </pre>
	 *
	 * This method should discern if the following assignments would be legal,
	 * possibly using predetermined {@link TypeVariable} assignments:
	 *
	 * <pre>
	 *List&lt;Integer&gt; listOfInts = foo(new Integer(0)) //legal
	 *List&lt;Number&gt; listOfNumbers = foo(new Integer(0)) //legal
	 *List&lt;? extends Number&gt; listOfBoundedWildcards = foo(new Integer(0)) //legal
	 * </pre>
	 *
	 * The corresponding calls to this method would be:
	 *
	 * <pre>
	 * Nil&lt;List&lt;N&gt;&gt; nilN = new Nil&lt;List&lt;N&gt;&gt;(){}
	 * Nil&lt;List&lt;Integer&gt;&gt; nilInteger = new Nil&lt;List&lt;Integer&gt;&gt;(){}
	 * Nil&lt;List&lt;Number&gt;&gt; nilNumber = new Nil&lt;List&lt;Number&gt;&gt;(){}
	 * Nil&lt;List&lt;? extends Number&gt;&gt; nilWildcardNumber = new Nil&lt;List&lt;? extends Number&gt;&gt;(){}
	 *
	 * checkGenericOutputsAssignability(nilN.getType(), nilInteger.getType, ...)
	 * checkGenericOutputsAssignability(nilN.getType(), nilNumber.getType, ...)
	 * checkGenericOutputsAssignability(nilN.getType(), nilWildcardNumber.getType, ...)
	 * </pre>
	 *
	 * Using a map where N was already bound to Integer (N -> Integer.class). This
	 * method is useful for the following scenario: During ops matching, we first
	 * check if the arguments (inputs) of the requested op are applicable to the
	 * arguments of an op candidate. During this process, possible type variables
	 * may be inferred. The can then be used with this method to find out if the
	 * outputs of the op candidate would be assignable to the output of the
	 * requested op.
	 *
	 * @param froms
	 * @param tos
	 * @param typeBounds
	 * @return the index {@code i} such that {@code from[i]} cannot be assigned to
	 *         {@code to[i]}, or {@code -1} iff {@code from[i]} can be assigned to
	 *         {@code to[i]} for all {@code 0 <= i < from.length}.
	 */
	static int checkGenericOutputsAssignability(Type[] froms, Type[] tos,
			HashMap<TypeVariable<?>, TypeVarInfo> typeBounds) {
		for (int i = 0; i < froms.length; i++) {
			Type from = froms[i];
			Type to = tos[i];
			
			if (to instanceof Any) continue;

			if (from instanceof TypeVariable) {
				TypeVarInfo typeVarInfo = typeBounds.get(from);
				// HACK: we CAN assign, for example, a Function<Iterable<N>, O> to a
				// Function<Iterable<Integer>, Double>,
				// because in this situation O is not bounded to any other types. However
				// isAssignable will fail,
				// since we cannot just cast Double to O without that required knowledge that O
				// can be fixed to Double.
				// We get around this by recording in typeBounds that our previously unbounded
				// TypeVariable (from) \
				// is now fixed to (to), then simply assigning (from) to (to), since from only
				// has one bound, being to.
				if (typeVarInfo == null) {
					TypeVariable<?> fromTypeVar = (TypeVariable<?>) from;
					TypeVarFromParameterizedTypeInfo fromInfo = new TypeVarFromParameterizedTypeInfo(fromTypeVar);
					fromInfo.fixBounds(to, true);
					typeBounds.put(fromTypeVar, fromInfo);
					from = to;
				}
				// similar to the above, if we know that O is already bound to a Type, and that
				// Type is to, then we can assign this without any issues.
				else {
					if(typeVarInfo.allowType(to, true)) from = to;
				}
			}

			if (!Types.isAssignable(Types.raw(from), Types.raw(to)))
				return i;
		}
		return -1;
	}

	/**
	 * Checks whether it would be legal to assign the {@link Type} source to the
	 * specified {@link ParameterizedType} destination (which could possibly be a
	 * supertype of the source type). Thereby, possible {@link TypeVariable}s
	 * contained in the parameters of the source are tried to be inferred in the
	 * sense of empty angle brackets when a new object is created:
	 *
	 * <pre>
	 * 
	 * List&lt;Integer&gt; listOfInts = new ArrayList&lt;&gt;();
	 * </pre>
	 *
	 * Hence, the types to put between the brackets are tried to be determined.
	 * Inference will be done by simple matching of an encountered
	 * {@link TypeVariable} in the source to the corresponding type in the
	 * parameters of the destination. If an {@link TypeVariable} is encountered
	 * more than once, the corresponding type in the destination needs to
	 * perfectly match. Else, false will be returned.</br>
	 * </br>
	 * Examples:
	 * <ul>
	 * If we have a class:
	 * <li>
	 *
	 * <pre>
	 * class NumberSupplier&lt;M extends Number&gt; implements Supplier&lt;M&gt;
	 * </li>
	 * </ul>
	 * <ul>
	 * The following check will return true:
	 * <li>
	 *
	 * <pre>
	 * checkGenericAssignability(NumberSupplier.class, new
	 * Nil&lt;Supplier&lt;Double&gt;&gt;() {}.getType())</li>
	 * </ul>
	 * </ul>
	 * <ul>
	 * Which will check if the following assignment would be legal:
	 * <li>
	 *
	 * <pre>
	 * Supplier&lt;Double&gt; list = new NumberSupplier&lt;&gt;()</li>
	 * </ul>
	 * </ul>
	 * <ul>
	 * Here, the parameter {@code <M extends Number>} can be inferred to be of
	 * type {@code Double} from the type {@code Supplier<Double>}
	 * </ul>
	 * <ul>
	 * Consequently the following will return false:
	 * <li>
	 *
	 * <pre>
	 * checkGenericAssignability(NumberSupplier.class, new
	 * Nil&lt;Supplier&lt;String&gt;&gt;() {}.getType())</li>
	 * </ul>
	 * <ul>
	 * {@code <M extends Number>} can't be inferred, as type {@code String} is not
	 * within the bounds of {@code M}.
	 * </ul>
	 * <ul>
	 * Furthermore, the following will return false for:
	 * {@code class NumberFunc<M extends Number> implements Function<M, M>}:
	 * <li>
	 *
	 * <pre>
	 * checkGenericAssignability(NumberSupplier.class, new
	 * Nil&lt;Function&lt;Double, Integer&gt;&gt;() {}.getType())</li>
	 * </ul>
	 * <ul>
	 * {@code <M extends Number>} can't be inferred, as types {@code Double} and
	 * {@code Integer} are ambiguous for {@code M}.
	 * </ul>
	 *
	 * @param src the type for which assignment should be checked from
	 * @param dest the parameterized type for which assignment should be checked
	 *          to
	 * @param typeVarAssigns the map of {@link TypeVariable}s to {@link Type}s to
	 *          populate with what would occur in this scenario; must be empty or
	 *          null
	 * @param safeAssignability used to determine if we want to check if the
	 *          src->dest assignment would be safely assignable even though it
	 *          would cause a compiler error if we explicitly tried to do this
	 *          (useful pretty much only for Op matching)
	 * @return whether and assignment of source to destination would be a legal
	 *         java statement
	 */
	public static boolean checkGenericAssignability(Type src,
		ParameterizedType dest, Map<TypeVariable<?>, Type> typeVarAssigns,
		boolean safeAssignability)
	{
		if (typeVarAssigns == null) {
			typeVarAssigns = new HashMap<>();
		}
		else if (!typeVarAssigns.isEmpty()) {
			throw new IllegalArgumentException(
				"Expected empty typeVarAssigns but contained " + typeVarAssigns.size() +
					" entries");
		}

		// fail fast when raw types are not assignable
		if (!Types.isAssignable(Types.raw(src), Types.raw(dest))) return false;

		// when raw types are assignable, check the type variables of src and dest
		Type[] srcTypes = typeParamsAgainstClass(src, Types.raw(dest));
		Type[] destTypes = dest.getActualTypeArguments();

		// if there are no type parameters in src (w.r.t. dest), do a basic
		// assignability check.
		if (srcTypes.length == 0) return Types.isAssignable(src, dest);
		// if there are type parameters, do a more complicated assignability check.
		boolean result = checkGenericAssignability(srcTypes, destTypes, src, dest,
			typeVarAssigns, safeAssignability);
		return result;
	}

	/**
	 * Obtains the type parameters of {@link Type} {@code src} <b>with respect
	 * to</b> the {@link Class} {@code dest}. When {@code src} has no type
	 * parameters (or is not a subclass of {@code dest}), an empty array is
	 * returned.
	 *
	 * @param src - the {@code Type} whose type parameters will be returned.
	 * @param superclass - the {@code Class} against which we want the type parameters of {@code src}
	 * @return an array of {@code Type}s denoting the type
	 */
	private static Type[] typeParamsAgainstClass(Type src, Class<?> superclass) {
		// only classes and ParameterizedTypes can have type parameters
		if (!(src instanceof Class || src instanceof ParameterizedType)) return new Type[0];
		Type superSrc = superType(src, superclass);
		if (superSrc == null) return new Type[0];
		if (superSrc instanceof ParameterizedType) return ((ParameterizedType) superSrc).getActualTypeArguments();
		return getParams(Types.raw(src), superclass);
	}

	/**
	 * This method is designed to handle edge cases when calling
	 * {@link Types#getExactSuperType(Type, Class)}. When
	 * {@code getExactSuperType} returns an error, this usually implies some funny
	 * business going on with the {@link Type} that was passed to it. We are not
	 * interested in supporting this business, since it usually results from poor
	 * practice in Class construction. TODO: determine some way of conveying to
	 * the user that GenTyRef doesn't like their inputs.
	 *
	 * @param src
	 * @param superClass
	 * @return - the supertype of {@code src} with rawtype {@code superClass}, or
	 *         {@code null} if no such supertype exists.
	 */
	private static Type superType(Type src, Class<?> superClass) {
			try {
				return Types.getExactSuperType(src, superClass);
			} catch (AssertionError e) {
				// can be thrown when
				return null;
			}
	}

	/**
	 * @param src
	 *            the type for which assignment should be checked from
	 * @param dest
	 *            the parameterized type for which assignment should be checked to
	 * @param safeAssignability
	 *            used to determine if we want to check if the src->dest assignment
	 *            would be safely assignable even though it would cause a compiler
	 *            error if we explicitly tried to do this (useful pretty much only
	 *            for Op matching)
	 * @return whether and assignment of source to destination would be a legal java
	 *         statement
	 */
	public static boolean checkGenericAssignability(Type src, ParameterizedType dest, boolean safeAssignability) {
		return checkGenericAssignability(src, dest, null, safeAssignability);
	}

	/**
	 * @param srcTypes the Type arguments for the source Type
	 * @param destTypes the Type arguments for the destination Type
	 * @param src the type for which assignment should be checked from
	 * @param dest the parameterized type for which assignment should be checked
	 *          to
	 * @param typeVarAssigns the map of {@link TypeVariable}s to
	 *          {@link TypeMapping}s that would occur in this scenario
	 * @param safeAssignability used to determine if we want to check if the
	 *          src->dest assignment would be safely assignable even though it
	 *          would cause a compiler error if we explicitly tried to do this
	 *          (useful pretty much only for Op matching)
	 * @return whether and assignment of source to destination would be a legal
	 *         java statement
	 */
	private static boolean checkGenericAssignability(Type[] srcTypes, Type[] destTypes, Type src, Type dest,
			Map<TypeVariable<?>, Type> typeVarAssigns, boolean safeAssignability) {
		// if the number of type arguments does not match, the types can't be
		// assignable
		if (srcTypes.length != destTypes.length) {
			return false;
		}
		
		try {
			// Try to infer type variables contained in the type arguments of
			// sry
			TypeInference.inferTypeVariables(srcTypes, destTypes, typeVarAssigns);
		} catch (IllegalArgumentException e) {
			// types can't be inferred
			// TODO: Consider the situations in which it is okay that the type
			// variables cannot be inferred. For example, if we have a
			// Function<Comparable<T>, Comparable<T>> and we ask for a
			// Function<Double, Object>, it is okay that we cannot infer the T of
			// Comparable<T> from Object since a Comparable<T> is an Object for any T.
			// It would be nice if we could just return false any time we catch a
			// TypeInferenceException, but until we sort this out, we cannot do so.
			return safeAssignability && isSafeAssignable(destTypes, typeVarAssigns, src, dest);
		}

		// Map TypeVariables in src to Types
		Class<?> matchingRawType = Types.raw(dest);
		Type[] mappedSrcTypes = mapVarToTypes(srcTypes, typeVarAssigns);
		Type inferredSrcType = Types.parameterize(matchingRawType, mappedSrcTypes);

		// Check assignability
		if (Types.isAssignable(inferredSrcType, dest, typeVarAssigns)) return true;

		return safeAssignability && isSafeAssignable(destTypes, typeVarAssigns, src,
			dest);
	}

	/**
	 * We know that the special types for the Op candidate and what we asked for are
	 * the same (i.e. that we are trying to determine if one Function can be
	 * assigned to another Function). There are some situations (that are
	 * particularly common when using ops.run()) where the Function SHOULD NOT
	 * NORMALLY MATCH UP but WE KNOW IT WILL BE SAFE TO ASSIGN. This method attempts
	 * to tease those situations out as a last resort.
	 * 
	 * @param destTypes
	 *            - the array of Parameterized types of the OpInfo we called the
	 *            matcher on (in the case of ops.run(), it is a Type array of the
	 *            types of the args we passed through.)
	 * @param typeVarAssigns
	 *            - a Map of all of the Type Variables already determined.
	 * @param dest
	 *            - the speical type of the Op that we want to find a match for
	 *            (determined by the user / ops.run())
	 * @return boolean - true if we can safely match this Op even though the types
	 *         do not directly match up. False otherwise.
	 */
	public static boolean isSafeAssignable(Type[] destTypes, Map<TypeVariable<?>, Type> typeVarAssigns, Type src,
			Type dest) {

		Method[] destMethods = Arrays.stream(Types.raw(dest).getDeclaredMethods())
				.filter(method -> Modifier.isAbstract(method.getModifiers())).toArray(Method[]::new);
		if (destMethods.length == 0) {
			throw new IllegalArgumentException(src + " does not have an abstract method!");
		}
		Type[] params = Types.getExactParameterTypes(destMethods[0], src);
		Type returnType = Types.getExactReturnType(destMethods[0], src);
		for (int i = 0; i < params.length; i++) {
			if (!Types.isAssignable(destTypes[i], params[i], typeVarAssigns))
				return false;
		}

		// Computers will have void as their return type, meaning that there is no
		// output to check.
		if (returnType == void.class)
			return true;

		return Types.isAssignable(returnType, destTypes[destTypes.length - 1], typeVarAssigns);
	}

	/**
	 * Map type vars in specified type list to types using the specified map. In
	 * doing so, type vars mapping to other type vars will not be followed but
	 * just replaced.
	 *
	 * @param typesToMap
	 * @param typeAssigns
	 * @return a copy of {@code typesToMap} in which the {@link TypeVariable}s
	 *         (that are present in {@code typeAssigns}) are mapped to the
	 *         associated values within the {@code Map}.
	 */
	private static Type[] mapVarToTypes(Type[] typesToMap, Map<TypeVariable<?>, Type> typeAssigns) {
		return Arrays.stream(typesToMap).map(type -> Types.unrollVariables(typeAssigns, type, false))
				.toArray(Type[]::new);
	}

	/**
	 * Finds the type parameters of the most specific super type of the specified
	 * subType whose erasure is the specified superErasure. Hence, will return the
	 * type parameters of superErasure possibly narrowed down by subType. If
	 * superErasure is not raw or not a super type of subType, an empty array will
	 * be returned.
	 *
	 * @param subType
	 *            the type to narrow down type parameters
	 * @param superErasure
	 *            the erasure of an super type of subType to get the parameters from
	 * @return type parameters of superErasure possibly narrowed down by subType, or
	 *         empty type array if no exists or superErasure is not a super type of
	 *         subtype
	 */
	public static Type[] getParams(Class<?> subType, Class<?> superErasure) {
		Type pt = Types.parameterizeRaw(subType);
		Type superType = Types.getExactSuperType(pt, superErasure);
		if (superType != null && superType instanceof ParameterizedType) {
			return ((ParameterizedType) superType).getActualTypeArguments();
		}
		return new Type[0];
	}

	/**
	 * Gets the "useful" class information carries on the given object, which
	 * depends on the actual type of the object.
	 */
	public static Class<?> getClass(final Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Nil)
			return getClass(((Nil<?>) obj).getType());
		if (obj instanceof Class)
			return (Class<?>) obj;
		if (obj instanceof ParameterizedType)
			return (Class<?>) ((ParameterizedType) obj).getRawType();
		return obj.getClass();
	}

	/**
	 * Finds the levels of casting between <code>origin</code> and
	 * <code>dest</code>. Returns 0 if dest and origin are the same. Returns -1 if
	 * dest is not assignable from origin.
	 */
	public static int findCastLevels(final Class<?> dest, final Class<?> origin) {
		if (dest.equals(origin))
			return 0;

		int level = 1;
		Class<?> currType = origin;
		// BFS if dest is an interface
		if (dest.isInterface()) {
			final HashSet<String> seen = new HashSet<>();
			final ArrayList<Type> currIfaces = new ArrayList<>(Arrays.asList(currType.getGenericInterfaces()));
			do {
				final ArrayList<Type> nextIfaces = new ArrayList<>();
				for (final Type iface : currIfaces) {
					if (seen.contains(iface.getTypeName()))
						continue;

					final Class<?> cls = getClass(iface);
					if (cls.equals(dest))
						return level;
					seen.add(iface.getTypeName());
					nextIfaces.addAll(Arrays.asList(cls.getGenericInterfaces()));
				}
				currIfaces.clear();
				currIfaces.addAll(nextIfaces);
				if (currType.getSuperclass() != null) {
					currType = currType.getSuperclass();
					currIfaces.addAll(Arrays.asList(currType.getGenericInterfaces()));
				}
				level++;
			} while (!currIfaces.isEmpty() || currType.getSuperclass() != null);
		}
		// otherwise dest is a class, so search the list of ancestors
		else {
			while (currType.getSuperclass() != null) {
				currType = currType.getSuperclass();
				if (currType.equals(dest))
					return level;
				level++;
			}
		}
		return -1;
	}

}
