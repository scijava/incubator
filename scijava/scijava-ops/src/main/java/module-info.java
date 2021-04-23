module org.scijava.ops {

/*
 * This is autogenerated source code -- DO NOT EDIT. Instead, edit the
 * corresponding template in templates/ and rerun bin/generate.groovy.
 */

	//TODO: rearrange packages to export only needed classes
	exports org.scijava.ops; //contains OpDependency interface
	exports org.scijava.ops.core; // contains OpCollection, Op interfaces
	exports org.scijava.ops.core.builder; // contains OpBuilder classes
	exports org.scijava.ops.matcher;
	exports org.scijava.ops.math;
	exports org.scijava.ops.simplify;
	exports org.scijava.ops.conversionLoss;
	exports org.scijava.ops.provenance;
	// TODO: move OpWrapper to its own package (org.scijava.ops.wrap??)
	exports org.scijava.ops.util; // contains OpWrapper interface
	exports org.scijava.struct;
	exports org.scijava.param;

	opens org.scijava.ops.conversionLoss to therapi.runtime.javadoc;
	opens org.scijava.ops.log to therapi.runtime.javadoc;
	opens org.scijava.ops.core.builder to therapi.runtime.javadoc;
	opens org.scijava.ops.copy to therapi.runtime.javadoc;
	opens org.scijava.ops.hints to therapi.runtime.javadoc;
	opens org.scijava.ops.adapt.complexLift to therapi.runtime.javadoc;
	opens org.scijava.ops.math to therapi.runtime.javadoc;
	opens org.scijava.ops.adapt.lift to therapi.runtime.javadoc;
	opens org.scijava.ops.create to therapi.runtime.javadoc;
	opens org.scijava.ops.impl to therapi.runtime.javadoc, org.scijava;
	opens org.scijava.ops.monitor to therapi.runtime.javadoc;
	opens org.scijava.ops.provenance to therapi.runtime.javadoc;
	opens org.scijava.ops.hints.impl to therapi.runtime.javadoc;
	opens org.scijava.ops.adapt.functional to therapi.runtime.javadoc;
	opens org.scijava.ops to therapi.runtime.javadoc, org.scijava;
	opens org.scijava.ops.util to therapi.runtime.javadoc;
	opens org.scijava.struct to therapi.runtime.javadoc;
	opens org.scijava.ops.matcher to therapi.runtime.javadoc;
	opens org.scijava.ops.simplify to therapi.runtime.javadoc;
	opens org.scijava.ops.stats to therapi.runtime.javadoc;
	opens org.scijava.ops.core to therapi.runtime.javadoc;
	opens org.scijava.ops.provenance.impl to therapi.runtime.javadoc, org.scijava;
	opens org.scijava.param to therapi.runtime.javadoc;

	// FIXME: This is a file name and is thus unstable
	requires geantyref;

	requires java.desktop;

	requires org.scijava;
	requires org.scijava.function;
	requires org.scijava.types;
	requires javassist;
	requires java.compiler;
	requires therapi.runtime.javadoc;

	uses javax.annotation.processing.Processor;
}
