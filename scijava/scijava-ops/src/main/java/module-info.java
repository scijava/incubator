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

  opens org.scijava.core to org.scijava;
  opens org.scijava.monitor to org.scijava;
	opens org.scijava.ops to org.scijava;
  opens org.scijava.ops.adapt to org.scijava;
  opens org.scijava.ops.adapt.complexLift to org.scijava;
  opens org.scijava.ops.adapt.functional to org.scijava;
  opens org.scijava.ops.adapt.lift to org.scijava;
  opens org.scijava.ops.copy to org.scijava;
  opens org.scijava.ops.core to org.scijava;
  opens org.scijava.ops.core.builder to org.scijava;
  opens org.scijava.ops.create to org.scijava;
  opens org.scijava.ops.function to org.scijava;
  opens org.scijava.ops.log to org.scijava;
	opens org.scijava.ops.matcher to org.scijava;
	opens org.scijava.ops.math to org.scijava;
	opens org.scijava.ops.stats to org.scijava;
	opens org.scijava.ops.util to org.scijava;
	opens org.scijava.param to org.scijava;
	opens org.scijava.struct to org.scijava;

  // FIXME: This is a file name and is thus unstable
  requires geantyref;
  
  requires java.desktop;

	requires org.scijava;
	requires org.scijava.types;
}
