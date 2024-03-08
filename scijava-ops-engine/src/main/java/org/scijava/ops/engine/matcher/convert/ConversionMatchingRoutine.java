/*-
 * #%L
 * SciJava Operations Engine: a framework for reusable algorithms.
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

package org.scijava.ops.engine.matcher.convert;

import org.scijava.ops.api.OpEnvironment;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.OpMatchingException;
import org.scijava.ops.api.OpRequest;
import org.scijava.ops.engine.BaseOpHints;
import org.scijava.ops.engine.MatchingConditions;
import org.scijava.ops.engine.OpCandidate;
import org.scijava.ops.engine.matcher.MatchingResult;
import org.scijava.ops.engine.matcher.OpMatcher;
import org.scijava.ops.engine.matcher.impl.RuntimeSafeMatchingRoutine;
import org.scijava.priority.Priority;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class ConversionMatchingRoutine extends RuntimeSafeMatchingRoutine {

	@Override
	public void checkSuitability(MatchingConditions conditions)
		throws OpMatchingException
	{
		if (conditions.hints().containsAny(BaseOpHints.Conversion.IN_PROGRESS,
			BaseOpHints.Conversion.FORBIDDEN)) //
			throw new OpMatchingException("Conversion is not disabled");
	}

	@Override
	public OpCandidate findMatch(MatchingConditions conditions, OpMatcher matcher,
		OpEnvironment env)
	{
		if (conditions.request().getName().equals("engine.create"))
			throw new OpMatchingException(
				"TODO: Avoid converting engine namespace Ops");
		final var convertConditions = MatchingConditions.from( //
			conditions.request(), //
			conditions.hints().plus(BaseOpHints.Conversion.IN_PROGRESS) //
		);
		OpRequest request = conditions.request();
		final ArrayList<OpCandidate> candidates = new ArrayList<>();
		for (final OpInfo info : env.infos(request.getName(), convertConditions
			.hints()))
		{
			Map<TypeVariable<?>, Type> typeVarAssigns = new HashMap<>();
			Conversions.tryConvert(env, info, request).ifPresent(converted -> {
				// TODO: This ConvertedOpInfo is built for the request - do we even
				// need to check assignability?
				boolean assignable = typesMatch( //
					converted.opType(), //
					convertConditions.request().getType(), //
					typeVarAssigns //
				);
				if (assignable) {
					candidates.add(new OpCandidate( //
						env, //
						request, //
						converted, //
						typeVarAssigns //
					));
				}
			});
		}
		final List<OpCandidate> matches = filterMatches(candidates);
		return new MatchingResult(candidates, matches, Collections.singletonList(
			request)).singleMatch();
	}

	@Override
	public double priority() {
		return Priority.VERY_LOW;
	}

}
