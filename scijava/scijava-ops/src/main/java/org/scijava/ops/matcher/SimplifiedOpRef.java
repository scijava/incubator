package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpInfo;
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
	
	@Override
	public OpCandidate createCandidate(OpEnvironment env, Logger log, OpInfo info,
		Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		if (info instanceof SimplifiedOpInfo) {
			SimplifiedOpInfo simpleInfo = (SimplifiedOpInfo) info;
			return new SimplifiedOpCandidate(env, log, this, simpleInfo, typeVarAssigns);
		}
		return super.createCandidate(env, log, info, typeVarAssigns);
	}
}
