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

package org.scijava.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.scijava.discovery.Discoverer;

public class DefaultTypeReifier implements TypeReifier {

	private List<TypeExtractor> extractors;
	private final Collection<Discoverer> discoverers;

	public DefaultTypeReifier(Discoverer... discoverers) {
		this(Arrays.asList(discoverers));
	}

	public DefaultTypeReifier(Collection<Discoverer> discoverers) {
		this.discoverers = discoverers;
	}

	@Override
	public Optional<TypeExtractor> getExtractor(final Class<?> c) {
		return extractors().stream().filter(t -> t.canReify(this, c)).findFirst();
	}

	// -- Helper methods --

	private List<TypeExtractor> extractors() {
		if (extractors == null) initExtractors();
		return extractors;
	}

	private synchronized void initExtractors() {
		if (extractors != null) return;
		extractors = getInstances();
	}

	private List<TypeExtractor> getInstances() {
		return discoverers.parallelStream() //
			.flatMap(d -> d.discover(TypeExtractor.class).stream()) //
			.sorted().collect(Collectors.toList());
	}
}
