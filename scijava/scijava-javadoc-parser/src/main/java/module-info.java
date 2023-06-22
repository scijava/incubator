module org.scijava.javadoc.parser {
	exports org.scijava.javadoc.parser;

	requires java.compiler;
	requires org.yaml.snakeyaml;

	provides javax.annotation.processing.Processor with org.scijava.javadoc.parser.JavadocAnnotationProcessor;
}
