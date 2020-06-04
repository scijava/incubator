module org.scijava.ops {
	
	exports org.scijava.ops.function; // contains functional inferfaces
	exports org.scijava.ops.core; // contains OpCollection, Op interfaces
	exports org.scijava.ops.core.builder; // contains OpBuilder classes
	// TODO: move OpWrapper to its own package (org.scijava.ops.wrap??)
	exports org.scijava.ops.util; // contains OpWrapper interface

	// -- Open plugins to scijava-common
	opens org.scijava.ops to org.scijava;

  // FIXME: This is a file name and is thus unstable
  requires geantyref;
  
  requires java.desktop;

	requires org.scijava;
	requires org.scijava.types;
}
