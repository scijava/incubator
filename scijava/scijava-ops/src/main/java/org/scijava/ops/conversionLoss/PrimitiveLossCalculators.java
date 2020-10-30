package org.scijava.ops.conversionLoss;

import org.scijava.plugin.Plugin;

public class PrimitiveLossCalculators {
	
	@Plugin(type = LossCalculator.class)
	public static class LongDoubleLoss extends LossCalculator<Long, Double> {

		@Override
		public double calculateForward(Long t) {
			long maxValue = Long.MAX_VALUE - 1;
			double converted = maxValue;
			return Math.abs(maxValue - (long)converted);
		}

		@Override
		public double calculateReverse(Double r) {
			double maxValue = Double.MAX_VALUE;
			long converted = (long) maxValue;
			return maxValue - converted;
		}
		
	}

}
