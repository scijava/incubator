module org.scijava.types {

	exports org.scijava.types;

	opens org.scijava.types.extractors to scijava.common;
	// FIXME: This was put in so that scijava-common can access the TypeService.
	// Ideally we would narrow its access.
	opens org.scijava.types to scijava.common;

	requires com.google.common;
	requires scijava.common;
}
