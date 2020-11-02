package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
import org.scijava.ops.simplify.Identity;
import org.scijava.ops.simplify.Simplifier;

/**
 * An OpRef that has been simplified into a new {@link Type} via a set of
 * {@link Simplifier}s.
 * 
 * @author Gabriel Selzer
 */
public class SimplifiedOpRef extends OpRef {
	
	final List<Simplifier<?, ?>> simplifiers;

	public SimplifiedOpRef(String name, Type type, Type outType, Type[] args) {
		super(name, type, outType, args);
		simplifiers = null;
	}

	public SimplifiedOpRef(String name, Type type, Type outType, Type[] args, List<Simplifier<?, ?>> simplifiers) {
		super(name, type, outType, args);
		this.simplifiers = simplifiers;
	}
	
	public static SimplifiedOpRef identitySimplification(OpRef ref) {
		List<Simplifier<?, ?>> identityList = Arrays.stream(ref.getArgs()).map(
			type -> new Identity<>(type)).collect(Collectors.toList());
		// TODO: can we make a constructor that takes an original OpRef and a list
		// of Simplifiers?
		return new SimplifiedOpRef(ref.getName(), ref.getType(), ref.getOutType(),
			ref.getArgs(), identityList);
	}
}
