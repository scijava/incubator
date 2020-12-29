package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.util.Arrays;

import org.scijava.ops.OpInfo;

/**
 * Container class storing typing information for the creation of simplified Ops
 * 
 * @author Gabriel Selzer
 */
public class SimplificationTypings {
	
	/** The {@link Type}s provided by the user in the {@link OpRef}*/
	private final Type[] originalTypes;
	/** The {@link Type}s of the {@link SimplifiedOpRef} */
	private final Type[] simpleTypes;
	/** The {@link Type}s of the {@link SimplifiedOpInfo}*/
	private final Type[] unfocusedTypes;
	/** The {@link Type}s declared by the {@link OpInfo}*/
	private final Type[] focusedTypes;
	/** The number of {@link Type}s */
	private final int numTypes;
	
	public SimplificationTypings(Type[] originalTypes, Type[] simpleTypes, Type[] unfocusedTypes, Type[] focusedTypes) {
		this.originalTypes = originalTypes;
		numTypes = this.originalTypes.length;
		this.simpleTypes = simpleTypes;
		if (this.simpleTypes.length != numTypes) throw new IllegalStateException(
			"simpleTypes array " + Arrays.toString(this.simpleTypes) +
				" is not of the same length as originalTypes array " + Arrays.toString(
					this.originalTypes));
		this.unfocusedTypes = unfocusedTypes;
		if (this.unfocusedTypes.length != numTypes) throw new IllegalStateException(
			"unfocusedTypes array " + Arrays.toString(this.unfocusedTypes) +
				" is not of the same length as originalTypes array " + Arrays.toString(
					this.originalTypes));
		this.focusedTypes = focusedTypes;
		if (this.focusedTypes.length != numTypes) throw new IllegalStateException(
			"focusedTypes array " + Arrays.toString(this.focusedTypes) +
				" is not of the same length as originalTypes array " + Arrays.toString(
					this.originalTypes));
	}

	public Type[] originalTypes() {
		return originalTypes;
	}

	public Type[] simpleTypes() {
		return simpleTypes;
	}

	public Type[] unfocusedTypes() {
		return unfocusedTypes;
	}

	public Type[] focusedTypes() {
		return focusedTypes;
	}

	public int numTypes() {
		return numTypes;
	}

}
