package org.scijava.ops.matcher;

import java.lang.reflect.Type;

import org.scijava.ops.OpInfo;

/**
 * Container class storing typing information for the creation of simplified Ops
 * @author Gabriel Selzer
 *
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
	
	public SimplificationTypings(Type[] originalTypes, Type[] simpleTypes, Type[] unfocusedTypes, Type[] focusedTypes) {
		this.originalTypes = originalTypes;
		this.simpleTypes = simpleTypes;
		this.unfocusedTypes = unfocusedTypes;
		this.focusedTypes = focusedTypes;
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

}
