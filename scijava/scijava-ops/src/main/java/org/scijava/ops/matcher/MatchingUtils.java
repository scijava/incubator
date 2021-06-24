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
