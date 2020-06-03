/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2017 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
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

package org.scijava.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scijava.plugin.Attr;
import org.scijava.struct.ItemIO;
import org.scijava.struct.MemberInstance;

/**
 * An annotation for indicating a field is an input or output parameter. This
 * annotation is a useful way for plugins to declare their inputs and outputs
 * simply.
 * 
 * @author Johannes Schindelin
 * @author Grant Harris
 * @author Curtis Rueden
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Repeatable(Parameters.class)
public @interface Parameter {

	/**
	 * IMPORTANT: Parameter annotation instances are mutated using reflections
	 * in order to resolve {@link ItemIO#AUTO} which accesses this field
	 * by name. If the name is changed, this has to be changed accordingly.
	 */
	public static final String ITEMIO_FIELD_NAME = "itemIO";
	
	/**
	 * IMPORTANT: Parameter annotation instances are mutated using reflections
	 * in order to resolve {@link ItemIO#AUTO} which accesses this field
	 * by name. If the name is changed, this has to be changed accordingly.
	 */
	public static final String KEY_FIELD_NAME = "key";
	
	/** Defines a key for the parameter. */
	String key() default "";

	/** Defines a label for the parameter. */
	String label() default "";

	/** Defines a description for the parameter. */
	String description() default "";

	/**
	 * Defines the input/output type of the parameter.
	 * 
	 * @see ItemIO
	 */
	ItemIO itemIO() default ItemIO.AUTO;

	/**
	 * Defines whether the parameter references an object which itself has
	 * parameters. This allows for nested structures to be created.
	 */
	boolean struct() default false;

	/**
	 * Defines the "visibility" of the parameter.
	 * <p>
	 * Choices are:
	 * </p>
	 * <ul>
	 * <li>NORMAL: parameter is included in the history for purposes of data
	 * provenance, and included as a parameter when recording scripts.</li>
	 * <li>TRANSIENT: parameter is excluded from the history for the purposes of
	 * data provenance, but still included as a parameter when recording scripts.</li>
	 * <li>INVISIBLE: parameter is excluded from the history for the purposes of
	 * data provenance, and also excluded as a parameter when recording scripts.
	 * This option should only be used for parameters with no effect on the final
	 * output, such as a "verbose" flag.</li>
	 * <li>MESSAGE: parameter value is intended as a message only, not editable by
	 * the user nor included as an input or output parameter.</li>
	 * </ul>
	 */
	ItemVisibility visibility() default ItemVisibility.NORMAL;

	/**
	 * Defines whether the parameter value should be filled programmatically, if
	 * possible.
	 */
	boolean autoFill() default true;

	/** Defines whether the parameter value must be non-null. */
	boolean required() default true;

	/** Defines whether to remember the most recent value of the parameter. */
	boolean persist() default true;

	/** Defines a key to use for saving the value persistently. */
	String persistKey() default "";

	/** Defines a function that is called to initialize the parameter. */
	String initializer() default "";

	/**
	 * Defines a function that is called to validate the parameter value after it
	 * is marked as resolved.
	 * 
	 * @see MemberInstance#resolve()
	 */
	String validater() default "";

	/**
	 * Defines a function that is called whenever this parameter changes.
	 * <p>
	 * This mechanism enables interdependent parameters of various types. For
	 * example, two {@code int} parameters "width" and "height" could update each
	 * other when another {@code boolean} "Preserve aspect ratio" flag is set.
	 * </p>
	 */
	String callback() default "";

	/**
	 * Defines the preferred widget style.
	 * <p>
	 * We do not use an {@code enum} because the styles need to be extensible. And
	 * we cannot use an interface-driven extensible enum pattern, because
	 * interfaces cannot be used as attributes within a Java annotation interface.
	 * So we fall back to strings!
	 * </p>
	 */
	String style() default "";

	/** Defines the minimum allowed value (numeric parameters only). */
	String min() default "";

	/** Defines the maximum allowed value (numeric parameters only). */
	String max() default "";

	/** Defines the step size to use (numeric parameters only). */
	String stepSize() default "";

	/** Defines the list of possible values (multiple choice text fields only). */
	String[] choices() default {};

	/**
	 * A list of additional attributes which can be used to extend this annotation
	 * beyond its built-in capabilities.
	 */
	Attr[] attrs() default {};
}
