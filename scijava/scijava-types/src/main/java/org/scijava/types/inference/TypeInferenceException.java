package org.scijava.types.inference;

/**
 * Exception indicating that type vars could not be inferred.
 */
class TypeInferenceException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 7147530827546663700L;
	
	public TypeInferenceException() {
		super();
	}
	
	public TypeInferenceException(String message) {
		super(message);
	}
}