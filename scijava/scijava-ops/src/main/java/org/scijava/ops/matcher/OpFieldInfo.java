/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2018 ImageJ developers.
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

package org.scijava.ops.matcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.scijava.Priority;
import org.scijava.ops.OpField;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.simplify.SimplificationUtils;
import org.scijava.ops.simplify.Unsimplifiable;
import org.scijava.param.Optional;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.param.ValidityProblem;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;

/**
 * Metadata about an op implementation defined as a field.
 * 
 * @author Curtis Rueden
 */
public class OpFieldInfo implements OpInfo {

	private final Object instance;
	private final Field field;

	private Struct struct;
	private ValidityException validityException;

	private final Boolean[] paramOptionality;
	private final boolean simplifiable;

	public OpFieldInfo(final Object instance, final Field field) {
		this.instance = instance;
		this.field = field;

		if (Modifier.isStatic(field.getModifiers())) {
			// Field is static; instance must be null.
			if (instance != null) {
				// Static field; instance should be null!
			}
		}
		else {
			// NB: Field is not static; instance must match field.getDeclaringClass().
			if (!field.getDeclaringClass().isInstance(instance)) {
				// Mismatch between given object and the class containing the field
				// But: we need to have proper case logic for the field being static or not.
			}
		}
		List<ValidityProblem> problems = new ArrayList<>();
		// Reject all non public fields
		if (!Modifier.isPublic(field.getModifiers())) {
			problems.add(new ValidityProblem("Field to parse: " + field + " must be public."));
		}

		// NB: Subclassing a collection and inheriting its fields is NOT
		// ALLOWED!
		try {
			struct = ParameterStructs.structOf(field);
			OpUtils.checkHasSingleOutput(struct);
			// NB: Contextual parameters not supported for now.
		} catch (ValidityException e) {
			problems.addAll(e.problems());
		}
		if (!problems.isEmpty()) {
			validityException = new ValidityException(problems);
		}

		// we cannot simplify the Op iff it has the Unsimplifiable annotation.
		simplifiable = field.getAnnotation(Unsimplifiable.class) == null;

		// determine parameter optionality
		paramOptionality = getParameterOptionality(instance, field,
			struct, problems);
	}

	// -- OpInfo methods --

	@Override
	public Type opType() {
		return field.getGenericType();
		// return Types.fieldType(field, subClass);
	}

	@Override
	public Struct struct() {
		return struct;
	}

	@Override
	public double priority() {
		final OpField opField = field.getAnnotation(OpField.class);
		return opField == null ? Priority.NORMAL : opField.priority();
	}

	@Override
	public String implementationName() {
		return field.getDeclaringClass().getName() + "." + field.getName();
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies)
	{
		if (dependencies != null && !dependencies.isEmpty())
			throw new IllegalArgumentException(
				"Op fields are not allowed to have any Op dependencies.");
		// NB: In general, there is no way to create a new instance of the field value.
		// Calling clone() may or may not work; it does not work with e.g. lambdas.
		// Better to just use the same value directly, rather than trying to copy.
		try {
			final Object object = field.get(instance);
			// TODO: Wrap object in a generic holder with the same interface.
			return struct().createInstance(object);
		} catch (final IllegalAccessException exc) {
			// FIXME
			exc.printStackTrace();
			throw new RuntimeException(exc);
		}
	}

	@Override
	public ValidityException getValidityException() {
		return validityException;
	}

	@Override
	public boolean isValid() {
		return validityException == null;
	}
	
	@Override
	public AnnotatedElement getAnnotationBearer() {
		return field;
	}

	// -- Object methods --

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof OpFieldInfo))
			return false;
		final OpInfo that = (OpInfo) o;
		return struct().equals(that.struct());
	}

	@Override
	public int hashCode() {
		return struct().hashCode();
	}

	@Override
	public String toString() {
		return OpUtils.opString(this);
	}

	@Override
	public boolean isSimplifiable() {
		return simplifiable;
	}

	@Override
	public boolean isOptional(Member<?> m) {
		if (!struct.members().contains(m)) throw new IllegalArgumentException(
			"Member " + m + " is not a Memeber of OpInfo " + this);
		if (m.isOutput()) return false;
		int inputIndex = OpUtils.inputs(struct).indexOf(m);
		// TODO: call this method once?
		return paramOptionality[inputIndex];
	}

	private static Boolean[] getParameterOptionality(Object instance, Field field,
		Struct struct, List<ValidityProblem> problems)
	{
		// the number of parameters we need to determine
		int opParams = OpUtils.inputs(struct).size();

		Class<?> fieldClass;
		try {
			fieldClass = field.get(instance).getClass();
		}
		catch (IllegalArgumentException | IllegalAccessException exc) {
			// TODO Auto-generated catch block
			problems.add(new ValidityProblem(exc));
			return generateAllRequiredArray.apply(opParams);
		}
		List<Method> fMethodsWithOptionals = fMethodsWithOptional(fieldClass);
		Class<?> fIface = ParameterStructs.findFunctionalInterface(fieldClass);
		List<Method> fIfaceMethodsWithOptionals = fMethodsWithOptional(fIface);

		if (fMethodsWithOptionals.isEmpty() && fIfaceMethodsWithOptionals.isEmpty()) {
			return generateAllRequiredArray.apply(opParams);
		}
		if (!fMethodsWithOptionals.isEmpty() && !fIfaceMethodsWithOptionals.isEmpty()) {
			problems.add(new ValidityProblem(
				"Multiple methods from the op type have optional parameters!"));
			return generateAllRequiredArray.apply(opParams);
		}
		if (fMethodsWithOptionals.isEmpty()) {
			return findParameterOptionality.apply(fIfaceMethodsWithOptionals.get(0));
		}
		if (fIfaceMethodsWithOptionals.isEmpty()) {
			return findParameterOptionality.apply(fMethodsWithOptionals.get(0));
		}
		return generateAllRequiredArray.apply(opParams);
	}

	private static Function<Integer, Boolean[]> generateAllRequiredArray =
		num -> {
			Boolean[] arr = new Boolean[num];
			Arrays.fill(arr, false);
			return arr;
		};

	private static List<Method> fMethodsWithOptional(Class<?> opClass) {
		Method superFMethod = SimplificationUtils.findFMethod(opClass);
		return Arrays.stream(opClass.getMethods()) //
			.filter(m -> m.getName().equals(superFMethod.getName())) //
			.filter(m -> m.getParameterCount() == superFMethod.getParameterCount()) //
			.filter(m -> hasOptionalAnnotations.apply(m)) //
			.collect(Collectors.toList());
	}

	private static Function<? super Method, Boolean[]> findParameterOptionality =
		m -> Arrays.stream(m.getParameters()).map(p -> p.isAnnotationPresent(
			Optional.class)).toArray(Boolean[]::new);

	private static Function<? super Method, Boolean> hasOptionalAnnotations =
		m -> Arrays.stream(m.getParameters()).anyMatch(p -> p.isAnnotationPresent(
			Optional.class));

}
