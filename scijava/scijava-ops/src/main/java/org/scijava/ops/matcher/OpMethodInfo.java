/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2016 Board of Regents of the University of
 * Wisconsin-Madison and University of Konstanz.
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

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.scijava.Priority;
import org.scijava.ops.OpDependencyMember;
import org.scijava.ops.OpInfo;
import org.scijava.ops.OpMethod;
import org.scijava.ops.OpUtils;
import org.scijava.ops.util.Adapt;
import org.scijava.param.ParameterStructs;
import org.scijava.param.ValidityException;
import org.scijava.param.ValidityProblem;
import org.scijava.struct.Struct;
import org.scijava.struct.StructInstance;
import org.scijava.types.Types;

/**
 * @author Marcel Wiedenmann
 */
public class OpMethodInfo implements OpInfo {

	private final Method method;
	private Type opType;
	private Struct struct;
	private final ValidityException validityException;

	public OpMethodInfo(final Method method) {
		final List<ValidityProblem> problems = new ArrayList<>();
		// Reject all non public methods
		if (!Modifier.isPublic(method.getModifiers())) {
			problems.add(new ValidityProblem("Method to parse: " + method +
				" must be public."));
		}
		if (!Modifier.isStatic(method.getModifiers())) {
			// TODO: Should throw and error if the method is not static.
			// TODO: We can't properly infer the generic types of static methods at
			// the moment. This might be a Java limitation.
			problems.add(new ValidityProblem("Method to parse: " + method +
				" must be static."));
		}
		this.method = method;
		try {
			struct = ParameterStructs.structOf(method.getDeclaringClass(), method);
			final OpMethod methodAnnotation = method.getAnnotation(OpMethod.class);
			try {
				opType = ParameterStructs.getOpMethodType(methodAnnotation.type(),
					method);
			}
			catch (IllegalArgumentException e) {
				opType = Types.parameterizeRaw(methodAnnotation.type());
			}
		}
		catch (final ValidityException e) {
			problems.addAll(e.problems());
		}
		validityException = problems.isEmpty() ? null : new ValidityException(
			problems);
	}

	// -- OpInfo methods --

	@Override
	public Type opType() {
		return opType;
	}

	@Override
	public Struct struct() {
		return struct;
	}

	@Override
	public double priority() {
		final OpMethod opMethod = method.getAnnotation(OpMethod.class);
		return opMethod == null ? Priority.NORMAL : opMethod.priority();
	}

	@Override
	public String implementationName() {
		// TODO: This includes all of the modifiers etc. of the method which are not
		// necessary to identify it. Use something custom? We need to be careful
		// because of method overloading, so just using the name is not sufficient.
		return method.toGenericString();
	}

	@Override
	public StructInstance<?> createOpInstance(
		final List<? extends Object> dependencies)
	{
		try {
			method.setAccessible(true);
			MethodHandles.Lookup lookup = MethodHandles.lookup();
			MethodHandle handle = lookup.unreflect(method);
			// bind dependencies
			int dIndex = 0;
			int argIndex = -1;
			// TODO: we could make this easier if we assume that dependencies are always in a line.
			for (int i = 0; i < struct.members().size(); i++) {
				
				boolean dep = struct.members().get(i) instanceof OpDependencyMember;
				if (struct.members().get(i).isInput() || dep) argIndex++;
				if (!dep) continue;
				handle = MethodHandles.insertArguments(handle, argIndex--, dependencies
					.get(dIndex++));
			}
			
			Object op = Adapt.Methods.lambdaize(Types.raw(opType), handle);
			
			
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
		catch (final Throwable ex)
		{
			throw new IllegalStateException("Failed to invoke Op method: " + method +
				". Provided Op dependencies were: " + Objects.toString(dependencies),
				ex);
		}
	}

	@Override
	public boolean isValid() {
		return validityException == null;
	}

	@Override
	public ValidityException getValidityException() {
		return validityException;
	}

	// -- Object methods --

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof OpMethodInfo)) return false;
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
	public AnnotatedElement getAnnotationBearer() {
		return method;
	}
}