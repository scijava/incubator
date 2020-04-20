module org.scijava.ops {

  exports org.scijava.ops;
  opens org.scijava.ops to org.scijava;

  // FIXME: This is a file name and is thus unstable
  requires geantyref;
  
  requires java.desktop;

	requires org.scijava;
	requires org.scijava.types;
}
