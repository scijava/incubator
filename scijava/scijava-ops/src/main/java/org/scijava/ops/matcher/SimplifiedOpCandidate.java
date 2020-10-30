package org.scijava.ops.matcher;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.scijava.Priority;
import org.scijava.log.Logger;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.conversionLoss.LossCalculation;
import org.scijava.ops.conversionLoss.LossCalculator;
import org.scijava.ops.conversionLoss.PrimitiveLossCalculators.LongDoubleLoss;

public class SimplifiedOpCandidate extends OpCandidate {
	
	private SimplifiedOpInfo simplifiedInfo;
	private SimplifiedOpRef simplifiedRef;

	public SimplifiedOpCandidate(OpEnvironment env, Logger log, SimplifiedOpRef ref,
		SimplifiedOpInfo info, Map<TypeVariable<?>, Type> typeVarAssigns)
	{
		super(env, log, ref, info, typeVarAssigns);
		this.simplifiedInfo = info;
		this.simplifiedRef = ref;
		List<LossCalculator<?, ?>> list = getLossCalculators(1l, 1.0);
		LossCalculator<Long, Double> calc = (LossCalculator<Long, Double>) list.get(0);
		LossCalculation<Long, Double> c = calc.calculateLoss(1l, 1.0);
	}
	
	@Override
	public double priority() {
		// TODO
		double base = Priority.VERY_LOW + simplifiedInfo.priority();
		
		// Penalty
		double penalty = 0; // TODO
		
		return base + penalty;
	}
	

	

	
	@SuppressWarnings("unchecked")
	private List<LossCalculator<?, ?>> getLossCalculators(Object t, Object u) {
		return env.
	}

}
