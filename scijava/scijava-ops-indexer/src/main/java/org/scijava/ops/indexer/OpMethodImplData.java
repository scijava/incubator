
package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.Patterns.tagElementSeparator;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

/**
 * {@link OpImplData} implementation handling {@link Method}s annotated with
 * "implNote op"
 *
 * @author Gabriel Selzer
 */
public class OpMethodImplData extends OpImplData {

	private Iterator<? extends VariableElement> paramItr = null;

	public OpMethodImplData(ExecutableElement source, String doc,
		ProcessingEnvironment env)
	{
		super(source, doc, env);
	}

	/**
	 * Parse javadoc tags pertaining exclusively to {@link Method}s
	 * 
	 * @param source the {@link Element} representing the {@link Method}. In
	 *          practice, this will always be an {@link ExecutableElement}
	 * @param tagType the tag type e.g. @author or @param
	 * @param doc the text following the tag type
	 */
	@Override
	void parseTag(Element source, String tagType, String doc) {
		if ("@param".equals(tagType)) {
			// First, Ignore type variables
			if (doc.contains("<.*>")) return;
			// Then, get a handle on the parameters if needed
			if (paramItr == null) {
				paramItr = ((ExecutableElement) source).getParameters().iterator();
			}
			String name = null;
			String type = null;
			if (paramItr.hasNext()) {
				var element = paramItr.next();
				type = element.asType().toString();
				name = element.getSimpleName().toString();
			}
			String[] paramData = tagElementSeparator.split(doc, 1);
			params.add(new OpParameter(name, type, OpParameter.IO_TYPE.INPUT,
					paramData[0]));
		}
		else if ("@return".equals(tagType)) {
			var returnType = ((ExecutableElement) source);
			params.add(new OpParameter("output", returnType.toString(), OpParameter.IO_TYPE.OUTPUT,
					doc));
		}
		else if (description.isBlank()) {
			description = tagType + " " + doc;
		}
	}

	protected String formulateSource(Element source) {
		ExecutableElement exSource = (ExecutableElement) source;
		// First, append the class
		StringBuilder sb = new StringBuilder();
		sb.append(source.getEnclosingElement());
		sb.append(".");
		// Then, append the method
		sb.append(source.getSimpleName());

		// Then, append the parameters
		var params = exSource.getParameters();
		sb.append("(");
		for (int i = 0; i < params.size(); i++) {
			var d = env.getTypeUtils().erasure(params.get(i).asType());
			sb.append(d);
			if (i < params.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(")");

		return "javaMethod:/" + URLEncoder.encode(sb.toString(),
			StandardCharsets.UTF_8);
	}
}
