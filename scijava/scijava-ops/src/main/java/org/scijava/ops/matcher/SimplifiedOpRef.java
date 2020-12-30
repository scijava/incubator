package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

	private final OpRef originalRef;
	private final List<OpInfo> simplifiers;
	private final OpInfo outputFocuser;

	// TODO: we need the original OpRef so that we can find the correct
	// simplifiers. Is there a better way to ensure that we get one?
	public SimplifiedOpRef(String name, Type type, Type outType, Type[] args) {
		super(name, type, outType, args);
		throw new UnsupportedOperationException("Must provide an original OpRef!");
	}

	public SimplifiedOpRef(String name, Type type, Type outType, Type[] args, List<OpInfo> simplifiers, OpInfo outputFocuser) {
		super(name, type, outType, args);
		throw new UnsupportedOperationException("Must provide an original OpRef!");
	}

	public SimplifiedOpRef(OpRef originalRef, Type type, Type outType, Type[] args, List<OpInfo> simplifiers, OpInfo outputFocuser) {
		super(originalRef.getName(), type, outType, args);
		this.originalRef = originalRef;
		this.simplifiers = simplifiers;
		this.outputFocuser = outputFocuser;
	}

	public static SimplifiedOpRef identitySimplification(OpRef ref) {
		// This seems slow. Can we make this faster?
		OpInfo identityInfo = new OpClassInfo(Identity.class);
		List<OpInfo> identityList = Arrays.stream(ref.getArgs()).map(
			type -> identityInfo).collect(Collectors.toList());
		// TODO: can we make a constructor that takes an original OpRef and a list
		// of Simplifiers?
		return new SimplifiedOpRef(ref, ref.getType(), ref.getOutType(),
			ref.getArgs(), identityList, identityInfo);
	}

	public List<OpInfo> simplifierInfos() {
		return simplifiers;
	}

	public OpInfo focuserInfo() {
		return outputFocuser;
	}

	public OpRef srcRef() {
		return originalRef;
	}
}
