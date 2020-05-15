module org.scijava.ops {

  exports org.scijava.core;
  exports org.scijava.monitor;
	exports org.scijava.ops;
  exports org.scijava.ops.adapt;
  exports org.scijava.ops.adapt.complexLift;
  exports org.scijava.ops.adapt.functional;
  exports org.scijava.ops.adapt.lift;
  exports org.scijava.ops.copy;
  exports org.scijava.ops.core;
  exports org.scijava.ops.core.builder;
  exports org.scijava.ops.create;
  exports org.scijava.ops.function;
  exports org.scijava.ops.log;
	exports org.scijava.ops.matcher;
	exports org.scijava.ops.math;
	exports org.scijava.ops.stats;
	exports org.scijava.ops.util;
	exports org.scijava.param;
	exports org.scijava.struct;

  opens org.scijava.core;
  opens org.scijava.monitor;
	opens org.scijava.ops;
  opens org.scijava.ops.adapt;
  opens org.scijava.ops.adapt.complexLift;
  opens org.scijava.ops.adapt.functional;
  opens org.scijava.ops.adapt.lift;
  opens org.scijava.ops.copy;
  opens org.scijava.ops.core;
  opens org.scijava.ops.core.builder;
  opens org.scijava.ops.create;
  opens org.scijava.ops.function;
  opens org.scijava.ops.log;
	opens org.scijava.ops.matcher;
	opens org.scijava.ops.math;
	opens org.scijava.ops.stats;
	opens org.scijava.ops.util;
	opens org.scijava.param;
	opens org.scijava.struct;

  // FIXME: This is a file name and is thus unstable
  requires geantyref;
  
  requires java.desktop;

	requires org.scijava;
	requires org.scijava.types;
}
