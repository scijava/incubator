/*-
 * #%L
 * SciJava library for generic type reasoning.
 * %%
 * Copyright (C) 2016 - 2023 SciJava developers.
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
package org.scijava.types.inference;

import com.google.common.base.Objects;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.scijava.types.Any;
import org.scijava.types.Types;

/**
 * A data structure retaining information about the mapping of a
 * {@link TypeVariable} to a {@link Type} within a type-inferring context.
 * 
 * @author Gabriel Selzer
 */
public class TypeMapping {

	protected final TypeVariable<?> typeVar;
	protected Type mappedType;

	/**
	 * A boolean describing whether {@code mappedType} can be mutated in within
	 * this set of {@link Type}s. The most common scenario in which a
	 * {@link Type} <b>cannot</b> be mutated is when it is a type parameter of a
	 * {@link ParameterizedType}. Once {@code malleable} is set to
	 * {@code false}, {@code mappedType} <b>cannot</b> change, and
	 * {@link TypeMapping#refine(Type, boolean)} will throw a
	 * {@link TypeInferenceException} so long as {@code newType} is not the
	 * exact same {@code Type} as {@mappedType}.
	 */
	boolean malleable;

	public TypeMapping(TypeVariable<?> typeVar, Type mappedType,
		boolean malleable)
	{
		this.typeVar = typeVar;
		this.mappedType = mappedType;
		this.malleable = malleable;
	}

	/**
	 * Attempts to accommodate {@code newType} into the current mapping between
	 * {@code typeVar} and {@code mappedType} <em>given</em> the existing
	 * malleability of {@code mappedType} and the malleability imposed by
	 * {@code newType}. If {@code newType} cannot be accommodated, a
	 * {@link TypeInferenceException} will be thrown. Note that it is not a
	 * guarantee that either the existing {@code mappedType} or {@code newType}
	 * will become the new {@link #mappedType} after the method ends;
	 * {@link #mappedType} could be a supertype of these two {@link Type}s.
	 * 
	 * @param otherType - the type that will be refined into {@link #mappedType}
	 * @param newTypeMalleability - the malleability of {@code otherType},
	 *          determined by the context from which {@code otherType} came.
	 */
	public void refine(Type otherType, boolean newTypeMalleability)
	{
		malleable &= newTypeMalleability;
		if (mappedType instanceof Any) {
			mappedType = otherType;
			return;
		}
		if (otherType instanceof Any) {
			return;
		}
		if (mappedType.equals(otherType)) {
			return;
		}
		if (malleable) {
			// TODO: consider the correct value of that boolean
			Type superType = Types.greatestCommonSuperType(new Type[] { otherType,
				mappedType }, false);
			if (Types.isAssignable(superType, typeVar)) {
				mappedType = superType;
				return;
			}
			throw new TypeInferenceException(typeVar +
				" cannot simultaneoustly be mapped to " + otherType + " and " +
				mappedType);
		}
		if (Objects.equal(mappedType, otherType)) return;
		throw new TypeInferenceException(typeVar +
			" cannot simultaneoustly be mapped to " + otherType + " and " +
			mappedType);
	}

	/**
	 * @return the {@link Type} associated with this {@link TypeVariable}
	 */
	public Type getType() {
		return mappedType;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(typeVar, mappedType, malleable);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TypeMapping)) return false;
		final TypeMapping that = (TypeMapping) o;
		return Objects.equal(typeVar, that.typeVar) && //
			Objects.equal(mappedType, that.mappedType) && //
			Objects.equal(malleable, that.malleable);
	}

	@Override
	public String toString() {
		return mappedType.toString();
	}
}