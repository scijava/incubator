module org.scijava.ops.engine {

/*
 * This is autogenerated source code -- DO NOT EDIT. Instead, edit the
 * corresponding template in templates/ and rerun bin/generate.groovy.
 */

	exports org.scijava.ops.engine;
	exports org.scijava.ops.engine.conversionLoss;
	exports org.scijava.ops.engine.util;

	requires java.compiler;

	requires org.scijava.common3;
	requires org.scijava.collections;
	requires org.scijava.discovery;
	requires org.scijava.function;
	requires org.scijava.log2;
	requires org.scijava.meta;
	requires org.scijava.parse2;
	requires org.scijava.priority;
	requires org.scijava.progress;
	requires org.scijava.struct;
	requires transitive org.scijava.ops.api;
	requires org.scijava.ops.spi;
	requires org.scijava.types;

	requires org.javassist;
	requires org.yaml.snakeyaml;


	uses javax.annotation.processing.Processor;
	uses org.scijava.discovery.Discoverer;
	uses org.scijava.ops.api.InfoChainGenerator;
	uses org.scijava.ops.api.OpEnvironment;
	uses org.scijava.ops.api.OpInfoGenerator;
	uses org.scijava.ops.api.OpWrapper;
	uses org.scijava.ops.api.features.MatchingRoutine;
	uses org.scijava.ops.api.features.YAMLOpInfoCreator;
	uses org.scijava.ops.engine.matcher.reduce.InfoReducer;
	uses org.scijava.ops.spi.Op;
	uses org.scijava.ops.spi.OpCollection;
	uses org.scijava.parse2.Parser;
	uses org.scijava.types.TypeReifier;
	uses org.scijava.types.TypeExtractor;

	provides org.scijava.discovery.Discoverer with
		org.scijava.ops.engine.yaml.YAMLOpInfoDiscoverer;

	provides org.scijava.ops.api.InfoChainGenerator with
		org.scijava.ops.engine.matcher.adapt.AdaptationInfoChainGenerator,
		org.scijava.ops.engine.impl.DefaultInfoChainGenerator,
		org.scijava.ops.engine.matcher.simplify.SimplificationInfoChainGenerator;

	provides org.scijava.ops.api.OpEnvironment with
	    org.scijava.ops.engine.impl.DefaultOpEnvironment;

	provides org.scijava.ops.api.OpHistory with
	    org.scijava.ops.engine.impl.DefaultOpHistory;

	provides org.scijava.ops.api.OpInfoGenerator with
	    org.scijava.ops.engine.impl.OpClassOpInfoGenerator,
	    org.scijava.ops.engine.impl.OpCollectionInfoGenerator,
		org.scijava.ops.engine.matcher.reduce.ReducedOpInfoGenerator;

	provides org.scijava.ops.api.OpWrapper with
		org.scijava.ops.engine.matcher.impl.OpWrappers.ProducerOpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function12OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function13OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function14OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function15OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Function16OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer0OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer12OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer13OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer14OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer15OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Computer16OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace2_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace2_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace3_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace3_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace3_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace4_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace4_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace4_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace4_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace5_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace5_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace5_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace5_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace5_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace6_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace6_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace6_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace6_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace6_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace6_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace7_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace7_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace7_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace7_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace7_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace7_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace7_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace8_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace9_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace10_10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace11_11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace12_12OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_12OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace13_13OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_12OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_13OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace14_14OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_12OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_13OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_14OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace15_15OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_1OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_2OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_3OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_4OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_5OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_6OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_7OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_8OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_9OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_10OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_11OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_12OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_13OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_14OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_15OpWrapper,
		org.scijava.ops.engine.matcher.impl.OpWrappers.Inplace16_16OpWrapper,
		org.scijava.ops.engine.matcher.impl.LossReporterWrapper;

	provides org.scijava.ops.api.features.MatchingRoutine with
		org.scijava.ops.engine.matcher.impl.RuntimeSafeMatchingRoutine,
		org.scijava.ops.engine.matcher.adapt.AdaptationMatchingRoutine,
		org.scijava.ops.engine.matcher.simplify.SimplificationMatchingRoutine;

	provides org.scijava.ops.spi.OpCollection with
	    org.scijava.ops.engine.adapt.lift.ComputerToArrays,
	    org.scijava.ops.engine.adapt.lift.ComputerToIterables,
	    org.scijava.ops.engine.adapt.lift.FunctionToArrays,
	    org.scijava.ops.engine.adapt.lift.FunctionToIterables,
	    org.scijava.ops.engine.adapt.lift.InplaceToArrays,
	    org.scijava.ops.engine.matcher.simplify.PrimitiveArrayLossReporters,
	    org.scijava.ops.engine.matcher.simplify.PrimitiveLossReporters,
	    org.scijava.ops.engine.copy.CopyOpCollection,
	    org.scijava.ops.engine.create.CreateOpCollection,
	    org.scijava.ops.engine.math.Add,
	    org.scijava.ops.engine.math.MathOpCollection,
	    org.scijava.ops.engine.math.Power,
	    org.scijava.ops.engine.math.Sqrt,
	    org.scijava.ops.engine.math.Zero,
	    org.scijava.ops.engine.matcher.simplify.PrimitiveArraySimplifiers,
	    org.scijava.ops.engine.matcher.simplify.PrimitiveSimplifiers,
		org.scijava.ops.engine.stats.Size;


	provides org.scijava.ops.spi.Op with //
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer1ToFunction1AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function1ToComputer1AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function1ToComputer1AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer1ToFunction1ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer2ToFunction2AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function2ToComputer2AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function2ToComputer2AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer2ToFunction2ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer3ToFunction3AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function3ToComputer3AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function3ToComputer3AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer3ToFunction3ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer4ToFunction4AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function4ToComputer4AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function4ToComputer4AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer4ToFunction4ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer5ToFunction5AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function5ToComputer5AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function5ToComputer5AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer5ToFunction5ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer6ToFunction6AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function6ToComputer6AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function6ToComputer6AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer6ToFunction6ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer7ToFunction7AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function7ToComputer7AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function7ToComputer7AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer7ToFunction7ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer8ToFunction8AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function8ToComputer8AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function8ToComputer8AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer8ToFunction8ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer9ToFunction9AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function9ToComputer9AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function9ToComputer9AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer9ToFunction9ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer10ToFunction10AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function10ToComputer10AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function10ToComputer10AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer10ToFunction10ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer11ToFunction11AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function11ToComputer11AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function11ToComputer11AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer11ToFunction11ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer12ToFunction12AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function12ToComputer12AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function12ToComputer12AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer12ToFunction12ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer13ToFunction13AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function13ToComputer13AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function13ToComputer13AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer13ToFunction13ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer14ToFunction14AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function14ToComputer14AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function14ToComputer14AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer14ToFunction14ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer15ToFunction15AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function15ToComputer15AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function15ToComputer15AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer15ToFunction15ViaFunction,
		org.scijava.ops.engine.adapt.complexLift.ComputersToFunctionsAndLift.Computer16ToFunction16AndLiftViaSource,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function16ToComputer16AndLiftAfter,
		org.scijava.ops.engine.adapt.complexLift.FunctionsToComputersAndLift.Function16ToComputer16AndLiftBefore,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaFunction.Computer16ToFunction16ViaFunction,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer0ToFunction0ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function0ToComputer0,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer1ToFunction1ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function1ToComputer1,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer2ToFunction2ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function2ToComputer2,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer3ToFunction3ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function3ToComputer3,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer4ToFunction4ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function4ToComputer4,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer5ToFunction5ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function5ToComputer5,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer6ToFunction6ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function6ToComputer6,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer7ToFunction7ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function7ToComputer7,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer8ToFunction8ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function8ToComputer8,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer9ToFunction9ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function9ToComputer9,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer10ToFunction10ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function10ToComputer10,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer11ToFunction11ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function11ToComputer11,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer12ToFunction12ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function12ToComputer12,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer13ToFunction13ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function13ToComputer13,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer14ToFunction14ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function14ToComputer14,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer15ToFunction15ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function15ToComputer15,
		org.scijava.ops.engine.adapt.functional.ComputersToFunctionsViaSource.Computer16ToFunction16ViaSource,
		org.scijava.ops.engine.adapt.functional.FunctionsToComputers.Function16ToComputer16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace1ToFunction1,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace2_1ToFunction2,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace2_2ToFunction2,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace3_1ToFunction3,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace3_2ToFunction3,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace3_3ToFunction3,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace4_1ToFunction4,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace4_2ToFunction4,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace4_3ToFunction4,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace4_4ToFunction4,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace5_1ToFunction5,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace5_2ToFunction5,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace5_3ToFunction5,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace5_4ToFunction5,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace5_5ToFunction5,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace6_1ToFunction6,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace6_2ToFunction6,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace6_3ToFunction6,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace6_4ToFunction6,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace6_5ToFunction6,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace6_6ToFunction6,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace7_1ToFunction7,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace7_2ToFunction7,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace7_3ToFunction7,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace7_4ToFunction7,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace7_5ToFunction7,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace7_6ToFunction7,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace7_7ToFunction7,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_1ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_2ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_3ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_4ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_5ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_6ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_7ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace8_8ToFunction8,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_1ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_2ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_3ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_4ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_5ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_6ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_7ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_8ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace9_9ToFunction9,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_1ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_2ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_3ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_4ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_5ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_6ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_7ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_8ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_9ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace10_10ToFunction10,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_1ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_2ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_3ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_4ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_5ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_6ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_7ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_8ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_9ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_10ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace11_11ToFunction11,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_1ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_2ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_3ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_4ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_5ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_6ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_7ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_8ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_9ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_10ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_11ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace12_12ToFunction12,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_1ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_2ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_3ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_4ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_5ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_6ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_7ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_8ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_9ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_10ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_11ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_12ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace13_13ToFunction13,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_1ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_2ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_3ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_4ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_5ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_6ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_7ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_8ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_9ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_10ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_11ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_12ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_13ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace14_14ToFunction14,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_1ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_2ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_3ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_4ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_5ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_6ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_7ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_8ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_9ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_10ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_11ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_12ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_13ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_14ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace15_15ToFunction15,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_1ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_2ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_3ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_4ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_5ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_6ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_7ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_8ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_9ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_10ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_11ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_12ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_13ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_14ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_15ToFunction16,
		org.scijava.ops.engine.adapt.functional.InplacesToFunctions.Inplace16_16ToFunction16,

		org.scijava.ops.engine.stats.Mean.MeanFunction;

    provides org.scijava.ops.engine.matcher.reduce.InfoReducer with //
        org.scijava.ops.engine.matcher.reduce.FunctionReducer,
        org.scijava.ops.engine.matcher.reduce.ComputerReducer;

    provides org.scijava.ops.api.features.YAMLOpInfoCreator with
        org.scijava.ops.engine.yaml.JavaClassYAMLOpInfoCreator,
        org.scijava.ops.engine.yaml.JavaFieldYAMLOpInfoCreator,
        org.scijava.ops.engine.yaml.JavaMethodYAMLInfoCreator;
}
