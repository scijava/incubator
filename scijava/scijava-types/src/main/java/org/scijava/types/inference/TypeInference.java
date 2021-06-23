
package org.scijava.types.inference;

import com.google.common.base.Objects;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

import org.scijava.types.Any;
import org.scijava.types.Types;

public class TypeInference {

	/**
	 * Tries to infer type vars contained in types from corresponding types from
	 * inferFrom, putting them into the specified map. <b>When a
	 * {@link TypeInferenceException} is thrown, the caller should assume that
	 * some of the mappings within {@code typeMappings} are incorrect.</b>
	 *
	 * @param types - the types containing {@link TypeVariable}s
	 * @param inferFroms - the types used to infer the {@link TypeVariable}s
	 *          within {@code types}
	 * @param typeVarAssigns - the mapping of {@link TypeVariable}s to
	 *          {@link Type}s
	 */
	public static void inferTypeVariables(Type[] types, Type[] inferFroms,
		Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		Map<TypeVariable<?>, TypeMapping> typeMappings = new HashMap<>();
		try {
			inferTypeVariables(types, inferFroms, typeMappings, true);
			typeVarAssigns.putAll(new TypeVarAssigns(typeMappings));
		}
		catch (TypeInferenceException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Tries to infer type vars contained in types from corresponding types from
	 * inferFrom, putting them into the specified map. <b>When a
	 * {@link TypeInferenceException} is thrown, the caller should assume that
	 * some of the mappings within {@code typeMappings} are incorrect.</b>
	 *
	 * @param type
	 * @param inferFrom
	 * @param typeMappings
	 */
	static void inferTypeVariablesWithTypeMappings(Type type[], Type[] inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		inferTypeVariables(type, inferFrom, typeMappings, true);
	}

	/**
	 * Current java language specifications allow either:
	 * <ul>
	 * <li>one {@code Object} upper bound and one lower bound
	 * <li>one (arbitrary) upper bound and no lower bounds
	 * </ul>
	 * We rely on this fact for the purposes of inferring type variables.
	 *
	 * @param type
	 * @return the <b>singular</b> {@link Type} that bounds this
	 *         {@link TypeVariable}. The returned {@code Type} could be
	 *         <b>either</b> a lower <b>or</b> upper bound (we do not care for the
	 *         sole purpose of type inference).
	 */
	private static Type getInferrableBound(WildcardType type) {
		Type[] lBounds = type.getLowerBounds();
		Type[] uBounds = type.getUpperBounds();
		if (lBounds.length == 1 && uBounds.length == 1 &&
			uBounds[0] == Object.class) return lBounds[0];
		else if (lBounds.length == 0 && uBounds.length == 1) return uBounds[0];
		else throw new IllegalArgumentException(
			"Illegal WildcardType: Current Java Language Specification does not allow " +
				type + " to simultaneously have upper bounds " + uBounds +
				" and lower bounds " + lBounds);
	}

	private static void inferTypeVariables(Class<?> type, Type inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		if (inferFrom instanceof TypeVariable<?>) {
			TypeVarAssigns typeVarAssigns = new TypeVarAssigns(typeMappings);
			// If current type var is absent put it to the map. Otherwise,
			// we already encountered that var.
			// Hence, we require them to be exactly the same.
			if (Types.isAssignable(type, inferFrom, typeVarAssigns)) {
				Type current = typeVarAssigns.putIfAbsent((TypeVariable<?>) inferFrom,
					type);
				if (current != null) {
					if (current instanceof Any) {
						typeVarAssigns.put((TypeVariable<?>) inferFrom, type);
					}
					else if (!Objects.equal(type, current)) {
						throw new TypeInferenceException();
					}
				}
			}
		}
	}

	private static void inferTypeVariables(GenericArrayType type, Type inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		if (inferFrom instanceof Class<?> && ((Class<?>) inferFrom).isArray()) {
			Type componentType = type.getGenericComponentType();
			Type componentInferFrom = ((Class<?>) inferFrom).getComponentType();
			inferTypeVariables(componentType, componentInferFrom, typeMappings);
		}
		else if (inferFrom instanceof WildcardType) {
			Type inferrableBound = getInferrableBound((WildcardType) inferFrom);
			inferTypeVariables(type, inferrableBound, typeMappings);
		}
		else throw new TypeInferenceException(inferFrom +
			" cannot be implicitly cast to " + type +
			", thus it is impossible to infer type variables for " + inferFrom);
	}

	private static void inferTypeVariables(ParameterizedType type, Type inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		if (inferFrom instanceof WildcardType) {
			inferFrom = getInferrableBound((WildcardType) inferFrom);
		}
		if (inferFrom instanceof Any) {
			Any any = (Any) inferFrom;
			mapTypeVarsToAny(type, any, typeMappings);
			return;
		}
		// Finding the supertype here is really important. Suppose that we are
		// inferring from a StrangeThing<Long> extends Thing<Double> and our
		// Op requires a Thing<T>. We need to ensure that T gets
		// resolved to a Double and NOT a Long.
		Type superInferFrom = Types.getExactSuperType(inferFrom, Types.raw(type));
		if (superInferFrom instanceof ParameterizedType) {
			ParameterizedType paramInferFrom = (ParameterizedType) superInferFrom;
			inferTypeVariables(type.getActualTypeArguments(), paramInferFrom
				.getActualTypeArguments(), typeMappings, false);
		}
		else if (superInferFrom instanceof Class) {
			TypeVarAssigns typeVarAssigns = new TypeVarAssigns(typeMappings);
			Type mappedType = Types.mapVarToTypes(type, typeVarAssigns);
			// Use isAssignable to attempt to infer the type variables present in type
			if (!Types.isAssignable(superInferFrom, mappedType, typeVarAssigns)) {
				throw new TypeInferenceException(inferFrom +
					" cannot be implicitly cast to " + mappedType +
					", thus it is impossible to infer type variables for " + inferFrom);
			}
			// for all remaining unmapped type vars, map to Any
			mapTypeVarsToAny(type, typeMappings);
		}
		// -- edge cases -> do our best -- //
		else if (superInferFrom == null) {
			// edge case 1: if inferFrom is an Object, superInferFrom will be null
			// when type is some interface.
			if (Object.class.equals(inferFrom)) {
				mapTypeVarsToAny(type, typeMappings);
				return;
			}
			// edge case 2: if inferFrom is a superType of type, we can get (some of)
			// the types of type by finding the exact superType of type w.r.t.
			// inferFrom.
			Type superTypeOfType = Types.getExactSuperType(type, Types.raw(
				inferFrom));
			if (superTypeOfType == null) {
				throw new TypeInferenceException(inferFrom +
					" cannot be implicitly cast to " + type +
					", thus it is impossible to infer type variables for " + inferFrom);
			}
			inferTypeVariables(superTypeOfType, inferFrom, typeMappings, false);
			mapTypeVarsToAny(type, typeMappings);
		}
		// TODO: elaborate
		else throw new IllegalStateException(superInferFrom +
			" is the supertype of " + inferFrom + " with respect to " + type +
			", however this cannot be (since " + type +
			" is a ParamterizedType)! (Only a ParameterizedType, Class, or null " +
			"can be returned from Types.getExactSuperType when it is called with a ParameterizedType!)");
	}

	/**
	 * Tries to infer type vars contained in types from corresponding types from
	 * inferFrom, putting them into the specified map. <b>When a
	 * {@link TypeInferenceException} is thrown, the caller should assume that
	 * some of the mappings within {@code typeMappings} are incorrect.</b>
	 *
	 * @param type
	 * @param inferFrom
	 * @param typeMappings
	 */
	static void inferTypeVariables(Type type, Type inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		inferTypeVariables(type, inferFrom, typeMappings, true);
	}

	private static void inferTypeVariables(Type type, Type inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings, boolean malleable)
	{
		if (type instanceof TypeVariable) {
			inferTypeVariables((TypeVariable<?>) type, inferFrom, typeMappings,
				malleable);
		}
		else if (type instanceof ParameterizedType) {
			inferTypeVariables((ParameterizedType) type, inferFrom, typeMappings);
		}
		else if (type instanceof WildcardType) {
			inferTypeVariables((WildcardType) type, inferFrom, typeMappings);
		}
		else if (type instanceof GenericArrayType) {
			inferTypeVariables((GenericArrayType) type, inferFrom, typeMappings);
		}
		else if (type instanceof Class) {
			inferTypeVariables((Class<?>) type, inferFrom, typeMappings);
		}

	}

	private static void inferTypeVariables(Type[] types, Type[] inferFroms,
		Map<TypeVariable<?>, TypeMapping> typeMappings, boolean malleable)
	{
		// Ensure that the user has not passed a null map
		if (typeMappings == null) throw new IllegalArgumentException(
			"Type Variable map is null, cannot store mappings of TypeVariables to Types!");

		if (types.length != inferFroms.length) throw new TypeInferenceException(
			"Could not infer type variables: Type arrays must be of the same size");

		for (int i = 0; i < types.length; i++) {
			inferTypeVariables(types[i], inferFroms[i], typeMappings, malleable);
		}
		// Check if the inferred types satisfy their bounds
		// TODO: can we do this in an efficient manner?
		TypeVarAssigns typeVarAssigns = new TypeVarAssigns(typeMappings);
		if (!Types.typesSatisfyVariables(typeVarAssigns)) {
			throw new TypeInferenceException();
		}
	}

	private static void inferTypeVariables(TypeVariable<?> type, Type inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings, boolean malleable)
	{
		TypeMapping typeData = typeMappings.get(type);
		// If current is not null then we have already encountered that
		// variable. If so, we require them to be exactly the same, and throw a
		// TypeInferenceException if they are not.
		if (typeData != null) {
			typeData.refine(inferFrom, malleable);
		}
		else {
			resolveTypeInMap(type, inferFrom, typeMappings, malleable);
			// Bounds could also contain type vars, hence possibly go into
			// recursion
			for (Type bound : type.getBounds()) {
				if (bound instanceof TypeVariable && typeMappings.get(bound) != null) {
					// If the bound of the current var (let's call it A) to
					// infer is also a var (let's call it B):
					// If we already encountered B, we check if the current
					// type to infer from is assignable to
					// the already inferred type for B. In this case we do
					// not require equality as one var is
					// bounded by another and it is not the same. E.g.
					// assume we want to infer the types of vars:
					// - - - A extends Number, B extends A
					// From types:
					// - - - Number, Double
					// First A is bound to Number, next B to Double. Then we
					// check the bounds for B. We encounter A,
					// for which we already inferred Number. Hence, it
					// suffices to check whether Double can be assigned
					// to Number, it does not have to be equal as it is just
					// a transitive bound for B.
					Type typeAssignForBound = typeMappings.get(bound).getType();
					if (!Types.isAssignable(inferFrom, typeAssignForBound)) {
						throw new TypeInferenceException();
					}
				}
				else {
					// Else go into recursion as we encountered a new var.
					inferTypeVariables(bound, inferFrom, typeMappings);
				}
			}

		}
	}

	private static void inferTypeVariables(WildcardType type, Type inferFrom,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		Type inferrableBound = getInferrableBound(type);
		if (inferFrom instanceof WildcardType) {
			// NB if both type and inferFrom are Wildcards, it doesn't really matter
			// (for the purpose of Type inference) whether those Wildcards have a
			// defined upper or lower bound. It is only important that we compare
			// those defined bounds, even if one is an upper bound and the other is a
			// lower bound. If the Wildcards are not assignable (which is (always?)
			// the case when one bound is an upper bound and the other is a lower
			// bound), it is still possible to infer the type variables; despite doing
			// so, checkGenericAssignability will return false.
			inferFrom = getInferrableBound((WildcardType) inferFrom);
		}
		if (inferrableBound instanceof TypeVariable<?>) {
			resolveTypeInMap((TypeVariable<?>) inferrableBound, inferFrom,
				typeMappings, true);
		}
		else if (inferrableBound instanceof ParameterizedType) {
			ParameterizedType parameterizedUpperBound =
				(ParameterizedType) inferrableBound;
			inferTypeVariables(parameterizedUpperBound, inferFrom, typeMappings,
				true);
		}
		// TODO: consider checking inferrableBounds instanceof Class
	}

	private static void mapTypeVarsToAny(Type type, Any any,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		if (!Types.containsTypeVars(type)) return;

		if (type instanceof TypeVariable) {
			if (typeMappings.containsKey(type)) return;
			TypeVariable<?> typeVar = (TypeVariable<?>) type;
			typeMappings.put(typeVar, suitableTypeMapping(typeVar, any, true));
		}
		else if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			Type[] typeParams = pType.getActualTypeArguments();
			for (Type typeParam : typeParams) {
				mapTypeVarsToAny(typeParam, typeMappings);
			}
		}
		else if (type instanceof WildcardType) {
			WildcardType wildcard = (WildcardType) type;
			for (Type lowerBound : wildcard.getLowerBounds())
				mapTypeVarsToAny(lowerBound, typeMappings);
			for (Type upperBound : wildcard.getUpperBounds())
				mapTypeVarsToAny(upperBound, typeMappings);
		}
		else if (type instanceof Class) {
			Class<?> clazz = (Class<?>) type;
			for (Type typeParam : clazz.getTypeParameters())
				mapTypeVarsToAny(typeParam, typeMappings);
		}
	}

	private static void mapTypeVarsToAny(Type type,
		Map<TypeVariable<?>, TypeMapping> typeMappings)
	{
		mapTypeVarsToAny(type, new Any(), typeMappings);
	}

	private static void resolveTypeInMap(TypeVariable<?> typeVar, Type newType,
		Map<TypeVariable<?>, TypeMapping> typeMappings, boolean malleability)
	{
		if (typeMappings.containsKey(typeVar)) {
			typeMappings.get(typeVar).refine(newType, malleability);
		}
		else {
			typeMappings.put(typeVar, suitableTypeMapping(typeVar, newType,
				malleability));
		}
	}

	static TypeMapping suitableTypeMapping(TypeVariable<?> typeVar, Type newType,
		boolean malleability)
	{
		if (newType instanceof WildcardType) {
			return new WildcardTypeMapping(typeVar, (WildcardType) newType,
				malleability);
		}
		return new TypeMapping(typeVar, newType, malleability);
	}

}
