package org.scijava.ops.conversionLoss;

import org.scijava.plugin.Plugin;
import org.scijava.types.Nil;

public class PrimitiveLossCalculators {
	
	@Plugin(type = LossCalculator.class)
	public static class LongDoubleLoss extends LossCalculator<Long, Double> {
		
		Nil<Long> fromType = Nil.of(Long.class);
		Nil<Double> toType = Nil.of(Double.class);

		@Override
		public double calculateForward() {
			long maxValue = Long.MAX_VALUE - 1;
			double converted = maxValue;
			return Math.abs(maxValue - (long)converted);
		}

		@Override
		public double calculateReverse() {
			double maxValue = Double.MAX_VALUE;
			long converted = (long) maxValue;
			return maxValue - converted;
		}

		@Override
		public Nil<Long> fromType() {
			return fromType; 
		}

		@Override
		public Nil<Double> toType() {
			return toType;
		}
		
	}
	
	@Plugin(type = LossCalculator.class)
	public static class LongIntegerLoss extends LossCalculator<Long, Integer> {
		
		Nil<Long> fromType = Nil.of(Long.class);
		Nil<Integer> toType = Nil.of(Integer.class);

		@Override
		public double calculateForward() {
			long maxValue = Long.MAX_VALUE;
			int converted = (int) maxValue;
			return Math.abs(maxValue - converted);
		}

		@Override
		public double calculateReverse() {
			// Integer to Long conversion is lossless
			return 0;
		}

		@Override
		public Nil<Long> fromType() {
			return fromType; 
		}

		@Override
		public Nil<Integer> toType() {
			return toType;
		}
		
	}
	
	@Plugin(type = LossCalculator.class)
	public static class IntegerDoubleLoss extends LossCalculator<Integer, Double> {
		
		Nil<Integer> fromType = Nil.of(Integer.class);
		Nil<Double> toType = Nil.of(Double.class);

		@Override
		public double calculateForward() {
			long maxValue = Integer.MAX_VALUE;
			double converted = maxValue;
			return Math.abs(maxValue - (long)converted);
		}

		@Override
		public double calculateReverse() {
			double maxValue = Double.MAX_VALUE;
			int converted = (int) maxValue;
			return maxValue - converted;
		}

		@Override
		public Nil<Integer> fromType() {
			return fromType; 
		}

		@Override
		public Nil<Double> toType() {
			return toType;
		}
		
	}

}
