
package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.Patterns.tagElementSeparator;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * {@link OpImplData} implementation handling {@link Field}s annotated with
 * "implNote op"
 *
 * @author Gabriel Selzer
 */
public class OpFieldImplData extends OpImplData {

	public OpFieldImplData(Element source, String doc,
		ProcessingEnvironment env)
	{
		super(source, doc, env);
	}

	/**
	 * Parse javadoc tags pertaining exclusively to {@link Field}s
	 *
	 * @param source the {@link Element} representing the {@link Field}.
	 * @param tagType the tag type e.g. @author or @param
	 * @param doc the text following the tag type
	 */
	@Override
	void parseTag(Element source, String tagType, String doc) {
		switch (tagType) {
			case "@input":
				String[] inData = tagElementSeparator.split(doc, 2);
				params.add(new OpParameter(inData[0], null, OpParameter.IO_TYPE.INPUT,
						inData[1]));
				break;
			case "@output":
				// NB outputs generally don't have names
				params.add(new OpParameter("output", null, OpParameter.IO_TYPE.OUTPUT,
						doc));
				break;
			case "@container":
				String[] containerData = tagElementSeparator.split(doc, 2);
				params.add(new OpParameter(containerData[0], null, OpParameter.IO_TYPE.CONTAINER,
						containerData[1]));
				break;
			case "@mutable":
				String[] mutableData = tagElementSeparator.split(doc, 2);
				params.add(new OpParameter(mutableData[0], null, OpParameter.IO_TYPE.MUTABLE,
						mutableData[1]));
				break;
		}
	}

	@Override
	String formulateSource(Element source) {
		return "javaField:/" + URLEncoder.encode(source.getEnclosingElement() +
			"$" + source, StandardCharsets.UTF_8);
	}

}
