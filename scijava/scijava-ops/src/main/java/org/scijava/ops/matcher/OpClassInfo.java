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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scijava.Priority;
import org.scijava.ops.FieldOpDependencyMember;
import org.scijava.ops.OpDependencyMember;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpUtils;
import org.scijava.ops.simplify.SimplificationUtils;
import org.scijava.ops.simplify.Unsimplifiable;
import org.scijava.param.Optional;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.plugin.Plugin;
import org.scijava.struct.Member;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.types.Types;

/**
 * Metadata about an op implementation defined as a class.
 * 
 * @author Curtis Rueden
 * @author David Kolb
 */
public class OpClassInfo implements OpInfo {

	private final Class<?> opClass;
	private Struct struct;
	private ValidityException validityException;
	private final double priority;

	private final boolean simplifiable;

	public OpClassInfo(final Class<?> opClass) {
		this(opClass, priorityFromAnnotation(opClass), simplifiableFromAnnotation(
			opClass));
	}

	public OpClassInfo(final Class<?> opClass, final double priority,
		final boolean simplifiable)
	{
		this.opClass = opClass;
		try {
			struct = ParameterStructs.structOf(opClass);
			OpUtils.checkHasSingleOutput(struct);
		}
		catch (ValidityException e) {
			validityException = e;
		}
		this.priority = priority;
		this.simplifiable = simplifiable;
	}

	// -- OpInfo methods --

	@Override
	public Type opType() {
		// TODO: Check whether this is correct!
		return Types.parameterizeRaw(opClass);
		// return opClass;
	}

	@Override
	public Struct struct() {
		return struct;
	}

	@Override
	public double priority() {
		return priority;
	}

	@Override
	public String implementationName() {
		return opClass.getName();
	}

	@Override
	public StructInstance<?> createOpInstance(List<?> dependencies) {
		final Object op;
		try {
			// TODO: Consider whether this is really the best way to
			// instantiate the op class here. No framework usage?
			// E.g., what about pluginService.createInstance?
			Constructor<?> ctor = opClass.getDeclaredConstructor();
			ctor.setAccessible(true);
			op = ctor.newInstance();
		}
		catch (final InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException | IllegalArgumentException
				| InvocationTargetException e)
		{
			// TODO: Think about whether exception handling here should be
			// different.
			throw new IllegalStateException("Unable to instantiate op: '" + opClass
				.getName() + "' Ensure that the Op has a no-args constructor.", e);
		}
		final List<OpDependencyMember<?>> dependencyMembers = dependencies();
		for (int i = 0; i < dependencyMembers.size(); i++) {
			final OpDependencyMember<?> dependencyMember = dependencyMembers.get(i);
			try {
				dependencyMember.createInstance(op).set(dependencies.get(i));
			}
			catch (final Exception ex) {
				// TODO: Improve error message. Used to include exact OpRef of Op
				// dependency.
				throw new IllegalStateException(
					"Exception trying to inject Op dependency field.\n" +
						"\tOp dependency field to resolve: " + dependencyMember.getKey() +
						"\n" + "\tFound Op to inject: " + dependencies.get(i).getClass()
							.getName() + //
						"\n" + "\tField signature: " + dependencyMember.getType(), ex);
			}
		}
		return struct().createInstance(op);
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
		return opClass;
	}

	// -- Object methods --

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof OpClassInfo)) return false;
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

	// -- Helper methods

	private static double priorityFromAnnotation(Class<?> annotationBearer) {
		final Plugin opAnnotation = annotationBearer.getAnnotation(Plugin.class);
		return opAnnotation == null ? Priority.NORMAL : opAnnotation.priority();
	}

	private static boolean simplifiableFromAnnotation(Class<?> annotationBearer) {
		final Unsimplifiable opAnnotation = annotationBearer.getAnnotation(
			Unsimplifiable.class);
		return opAnnotation == null ? true : false;
	}

	@Override
	public boolean isSimplifiable() {
		return simplifiable;
	}

	/**
	 * TODO: this implementation seems hacky, for two reasons.
	 * <ol>
	 * <li>We are assuming that the Struct's Members and their corresponding
	 * {@link Parameter}s on the functional Method have the same ordering. There
	 * is no real guarantee that this is the case.</li>
	 * <li>There doesn't seem to be a good way to reliably determine where the
	 * {@link Optional} annotations might be. Ideally, they'd be on the functional
	 * method overriden within the class itself. But sometimes (this happens in
	 * ImageJ Ops2's geom package) the functional method is overriden within a
	 * superclass of the class we have, and to reduce code duplication we'd like
	 * to support that too. It could also be useful to put the annotation directly
	 * on the method of the Functional Interface (suppose you write a
	 * {@code BiFunctionWithOptional} interface)</li>
	 * </ol>
	 */
	@Override
	public boolean isOptional(Member<?> m) {
		if (!struct.members().contains(m)) throw new IllegalArgumentException(
			"Member " + m + " is not a Memeber of OpInfo " + this);
		if (m.isOutput()) return false;
		if (m instanceof OpDependencyMember) return false;
		int inputIndex = OpUtils.inputs(struct).indexOf(m);
		// TODO: call this method once?
		return parameters().anyMatch(arr -> arr[inputIndex].isAnnotationPresent(Optional.class));
	}

	private Stream<Parameter[]> parameters() {
		Method superFMethod = SimplificationUtils.findFMethod(opClass);
		return Arrays.stream(opClass.getMethods()) //
				.filter(m -> m.getName().equals(superFMethod.getName())) //
				.filter(m -> m.getDeclaringClass() != ParameterStructs.findFunctionalInterface(opClass)) //
				.map(m -> m.getParameters());
	}
}
