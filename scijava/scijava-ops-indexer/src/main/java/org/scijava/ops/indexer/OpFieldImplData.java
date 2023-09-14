
package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.Patterns.tagElementSeparator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

public class OpFieldImplData extends OpImplData {

	public OpFieldImplData(Element source, String doc,
		ProcessingEnvironment env)
	{
		super(source, doc, env);
	}

	@Override
	void parseTag(Element source, String tagType, String doc) {
		switch (tagType) {
			case "@input":
				String[] inData = tagElementSeparator.split(doc, 2);
				params.add(new ParameterTagData(ParameterTagData.IO_TYPE.INPUT,
					inData[0], inData[1], null));
				break;
			case "@output":
				// NB outputs generally don't have names
				params.add(new ParameterTagData(ParameterTagData.IO_TYPE.OUTPUT,
					"output", doc, null));
				break;
			case "@container":
				String[] containerData = tagElementSeparator.split(doc, 2);
				params.add(new ParameterTagData(ParameterTagData.IO_TYPE.CONTAINER,
					containerData[0], containerData[1], null));
				break;
			case "@mutable":
				String[] mutableData = tagElementSeparator.split(doc, 2);
				params.add(new ParameterTagData(ParameterTagData.IO_TYPE.MUTABLE,
					mutableData[0], mutableData[1], null));
				break;
		}
	}

	@Override
	String formulateSource(Element source) {
		return "javaField:/" + URLEncoder.encode(source.getEnclosingElement() +
			"$" + source, StandardCharsets.UTF_8);
	}

}
