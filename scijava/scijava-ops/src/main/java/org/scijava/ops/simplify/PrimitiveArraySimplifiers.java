package org.scijava.ops.simplify;

import org.scijava.plugin.Plugin;
import org.scijava.util.ObjectArray;

public class PrimitiveArraySimplifiers {
	
	@Plugin(type = Simplifier.class)
	public static class ByteArraySimplifier implements Simplifier<ObjectArray<Number>, Byte[]> {

		@Override
		public ObjectArray<Number> simplify(Byte[] p) {
			return new ObjectArray<>(p);
		}

		@Override
		public Byte[] focus(ObjectArray<Number> g) {
			return g.stream().map(e -> e.byteValue()).toArray(Byte[]::new);
		}

		@Override
		public String toString() {
			return "Byte[] Simplifier";
		}

	}
	
	@Plugin(type = Simplifier.class)
	public static class IntegerArraySimplifier implements Simplifier<ObjectArray<Number>, Integer[]> {

		@Override
		public ObjectArray<Number> simplify(Integer[] p) {
			return new ObjectArray<>(p);
		}

		@Override
		public Integer[] focus(ObjectArray<Number> g) {
			return g.stream().map(e -> e.intValue()).toArray(Integer[]::new);
		}

		@Override
		public String toString() {
			return "Integer[] Simplifier";
		}

	}
}
