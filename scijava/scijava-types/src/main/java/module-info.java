module org.scijava.types {

	exports org.scijava.types;

	opens org.scijava.types.extractors to org.scijava;
	// FIXME: This was put in so that scijava-common can access the TypeService.
	// Ideally we would narrow its access.
	opens org.scijava.types to org.scijava;

	requires org.scijava;
	// TODO: fix
	requires com.google.common;
}
