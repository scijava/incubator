package org.scijava.ops.engine.conversionLoss.impl;

import java.lang.reflect.Type;
import java.util.UUID;

import org.scijava.ops.api.Hints;
import org.scijava.ops.api.OpExecutionSummary;
import org.scijava.ops.api.OpHistory;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.OpWrapper;
import org.scijava.ops.engine.BaseOpHints.DependencyMatching;
import org.scijava.ops.engine.conversionLoss.LossReporter;
import org.scijava.plugin.Plugin;
import org.scijava.types.GenericTyped;
import org.scijava.types.Nil;

/**
 * An {@link OpWrapper} for {@link LossReporter}s.
 * TODO: would be nice if this was unnecessary.
 *
 * @author Gabriel Selzer
 *
 * @param <I>
 * @param <O>
 */
@Plugin(type = OpWrapper.class)
public class LossReporterWrapper<I, O> //
	implements //
	OpWrapper<LossReporter<I, O>>
{

	@Override
	public LossReporter<I, O> wrap( //
		final LossReporter<I, O> op, //
		final OpInfo info, //
		final Hints hints, //
		final OpHistory history, //
		final UUID executionID, //
		final Type reifiedType)
	{
		class GenericTypedLossReporter implements //
			LossReporter<I, O>, //
			GenericTyped //
		{

			@Override
			public Double apply(Nil<I> from, Nil<O> to) //
			{
				// Call the op
				Double output = op.apply(from, to);

				// Log a new execution
					if (!hints.containsHint(DependencyMatching.IN_PROGRESS)) {
						OpExecutionSummary e = new OpExecutionSummary(executionID, info, op, this, output);
						history.addExecution(e);
					}
				return output;
			}

			@Override
			public Type getType() {
				return reifiedType;
			}
		}
		return new GenericTypedLossReporter();
	}
}
