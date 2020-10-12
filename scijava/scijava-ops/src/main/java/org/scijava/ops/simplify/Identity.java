package org.scijava.ops.simplify;

import java.lang.reflect.Type;

public class Identity<T> implements Simplifier<T, T> {
	private Type type;
	
	public Identity(Type type) {
		this.type = type;
	}
	
	@Override
	public T simplify(T p) {
		return p;
	}

	@Override
	public T focus(T g) {
		return g;
	}

	@Override
	public Type simpleType() {
		return type;
	}
	
	@Override
	public Type focusedType() {
		return type;
	}
	
	
	@Override
	public String toString() {
		return "Identity: " + type;
	}
}
