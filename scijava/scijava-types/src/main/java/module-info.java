module org.scijava.types {

	exports org.scijava.types;

	// TODO: We need to open these classes to scijava-common so that the plugins
	// can make use of the Type Extractors within.
	// Decisions:
	// Separate module for the type extractors?
	opens org.scijava.types to org.scijava;

	requires org.scijava;
	// TODO: fix
	requires com.google.common;
}
