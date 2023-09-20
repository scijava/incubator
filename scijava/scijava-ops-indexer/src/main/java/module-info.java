import org.scijava.ops.indexer.JavadocAnnotationProcessor;

module org.scijava.javadoc.parser {
	exports org.scijava.ops.indexer;

	requires java.compiler;
	requires org.yaml.snakeyaml;
	requires org.scijava.ops.spi;

	provides javax.annotation.processing.Processor with JavadocAnnotationProcessor;
}
