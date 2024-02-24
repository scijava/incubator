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

package org.scijava.ops.engine.matcher.simplify;

import org.scijava.ops.api.OpEnvironment;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.OpMatchingException;
import org.scijava.ops.api.OpRequest;
import org.scijava.ops.engine.BaseOpHints.Simplification;
import org.scijava.ops.engine.MatchingConditions;
import org.scijava.ops.engine.OpCandidate;
import org.scijava.ops.engine.matcher.MatchingResult;
import org.scijava.ops.engine.matcher.MatchingRoutine;
import org.scijava.ops.engine.matcher.OpMatcher;
import org.scijava.ops.engine.matcher.impl.RuntimeSafeMatchingRoutine;
import org.scijava.priority.Priority;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class FocusedMatchingRoutine implements MatchingRoutine {

	@Override
	public void checkSuitability(MatchingConditions conditions)
		throws OpMatchingException
	{
		var hints = conditions.hints();
		var suitable = hints.containsNone(
			Simplification.IN_PROGRESS,
			Simplification.FORBIDDEN
		);
		if (!suitable) throw new OpMatchingException(
			"Focusing is not suitable: Simplification is not in progress");
	}

	@Override
	public OpCandidate findMatch(MatchingConditions conditions, OpMatcher matcher,
		OpEnvironment env)
	{
		var simpleReq = new SimplifiedOpRequest(conditions.request(), env);
		var hints = conditions.hints().plus(Simplification.IN_PROGRESS);
		var focusedConditions = MatchingConditions.from(simpleReq, hints);
		// Pass 2 - Focus if needed
		return matcher.match(focusedConditions, env);
	}

	@Override
	public double priority() {
		return Priority.EXTREMELY_LOW;
	}

}
