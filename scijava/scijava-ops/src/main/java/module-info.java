module org.scijava.ops {

  exports org.scijava.ops;
  exports org.scijava.ops.function to org.scijava;
  exports org.scijava.ops.adapt.functional to org.scijava;
  exports org.scijava.ops.util to org.scijava;
  opens org.scijava.ops to org.scijava;
  opens org.scijava.ops.function to org.scijava;
  opens org.scijava.ops.adapt.functional to org.scijava;
  opens org.scijava.ops.util to org.scijava;

  // FIXME: This is a file name and is thus unstable
  requires geantyref;
  
  requires java.desktop;

	requires org.scijava;
	requires org.scijava.types;
}
