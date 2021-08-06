package org.scijava.ops.spi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotates a progress reporter as a field that should be auto injected.*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
public @interface OpProgressReporter {}
