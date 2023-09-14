
package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.Patterns.blockSeparator;
import static org.scijava.ops.indexer.Patterns.tagElementSeparator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;

public class OpClassImplData extends OpImplData {

	/**
	 * @param source
	 * @param doc
	 */
	public OpClassImplData(Element source, ExecutableElement fMethod, String doc,
		String fMethodDoc, ProcessingEnvironment env)
	{
		super(source, doc, env);
		parseFunctionalMethod(fMethod, fMethodDoc, env);
	}

	private void parseFunctionalMethod(ExecutableElement fMethod,
		String fMethodDoc, ProcessingEnvironment env)
	{
		if (fMethodDoc == null || fMethodDoc.isEmpty()) return;
		String[] sections = blockSeparator.split(fMethodDoc);
		var paramTypes = fMethod.getParameters().iterator();
		for (String section : sections) {
			String[] elements = tagElementSeparator.split(section, 2);
			if (elements[0].equals("@param")) {
				String[] foo = tagElementSeparator.split(elements[1], 2);
				String name = foo[0];
				String description = foo[1];
				if (paramTypes.hasNext()) {
					String type = paramTypes.next().asType().toString();
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.INPUT, name,
						description, type));
				}
				else {
					var msg = "Skipping param tag " + name +
						" as it does not have a corresponding parameter in Method " +
						fMethod;
					env.getMessager().printMessage(Diagnostic.Kind.WARNING, msg);
				}
			}
			else if (elements[0].equals("@return")) {
				String name = "output";
				String description = elements[1];
				String type = fMethod.getReturnType().toString();
				params.add(new ParameterTagData(ParameterTagData.IO_TYPE.OUTPUT, name,
					description, type));
			}
			else if (elements[0].equals("@author")) {
				addAuthor(tagElementSeparator.split(section, 2)[1]);
			}
		}
	}

	@Override
	void parseTag(Element source, String tagType, String doc) {
		if (tagType == null) {
			description = doc;
		}
	}

	protected String formulateSource(Element source) {
		var srcString = source.toString();
		var parent = source.getEnclosingElement();
		// handle inner classes
		while (parent.getKind() == ElementKind.CLASS) {
			int badPeriod = srcString.lastIndexOf('.');
			srcString = srcString.substring(0, badPeriod) + '$' + srcString.substring(
				badPeriod + 1);
			parent = parent.getEnclosingElement();
		}
		return "javaClass:/" + URLEncoder.encode(srcString, StandardCharsets.UTF_8);
	}

}
