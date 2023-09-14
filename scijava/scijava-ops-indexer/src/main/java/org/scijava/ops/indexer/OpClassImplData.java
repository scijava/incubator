
package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.RuntimeJavadocHelper.blockSeparator;
import static org.scijava.ops.indexer.RuntimeJavadocHelper.tagElementSeparator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;

public class OpClassImplData implements OpImplData {

	private final Map<String, Object> implNotes = new HashMap<>();

	private final List<String> authors = new ArrayList<>();

	private final List<String> names = new ArrayList<>();

	private final List<ParameterTagData> parameterTags = new ArrayList<>();

	private final String source;

	private double priority = 0.0;

	private String description = "";

	/**
	 * @param source
	 * @param doc
	 */
	public OpClassImplData(Element source, ExecutableElement fMethod, String doc,
		String fMethodDoc, ProcessingEnvironment env)
	{
		String[] sections = blockSeparator.split(doc);
		for (String section : sections) {
			String[] elements = tagElementSeparator.split(section);
			switch (elements[0]) {
				case "author":
					addAuthor(tagElementSeparator.split(section, 2)[1]);
					break;
				case "implNote":
					if (elements.length > 2) {
						for (int i = 2; i < elements.length; i++) {
							String[] kv = elements[i].split("=", 2);
							if (kv.length == 2) {
								String value = kv[1].replaceAll("^[,\"']+|[,\"']+$", "");
								if ("priority".equals(kv[0])) {
									this.priority = Double.parseDouble(value);
								}
								else if ("names".equals(kv[0]) || "name".equals(kv[0])) {
									names.addAll(Arrays.asList(value.split("\\s*,\\s*")));
								}
								else {
									if (value.contains(",")) {
										implNotes.put(kv[0], value.split(","));
									}
									else {
										implNotes.put(kv[0], value);
									}
								}
							}
						}
					}
					break;
				default:
					if (description.isBlank()) {
						description = section;
					}
					break;
			}

		}
		if (this.names.isEmpty()) {
			throw new IllegalArgumentException("Op " + source +
				" does not declare any names");
		}

		parseFunctionalMethod(fMethod, fMethodDoc, env);
		this.source = formulateSource(source);
	}

	private void parseFunctionalMethod(
		ExecutableElement fMethod, String fMethodDoc, ProcessingEnvironment env)
	{
		if (fMethodDoc == null || fMethodDoc.isEmpty())
			return;
		String[] sections = blockSeparator.split(fMethodDoc);
		var paramTypes = fMethod.getParameters().iterator();
		for (String section : sections) {
			String[] elements = tagElementSeparator.split(section, 2);
			if (elements[0].equals("param")) {
				String[] foo = tagElementSeparator.split(elements[1], 2);
				String name = foo[0];
				String description = foo[1];
				if (paramTypes.hasNext()) {
					String type = paramTypes.next().asType().toString();
					parameterTags.add(new ParameterTagData(ParameterTagData.IO_TYPE.INPUT,
							name, description, type));
				}
				else {
					var msg = "Skipping param tag " + name + " as it does not have a corresponding parameter in Method " + fMethod;
					env.getMessager().printMessage(Diagnostic.Kind.WARNING, msg);
				}
			}
			else if (elements[0].equals("return")) {
				String name = "output";
				String description = elements[1];
				String type = fMethod.getReturnType().toString();
				parameterTags.add(new ParameterTagData(ParameterTagData.IO_TYPE.OUTPUT,
					name, description, type));
			}
			else if (elements[0].equals("author")) {
				addAuthor(tagElementSeparator.split(section, 2)[1]);
			}
		}
	}

	private void addAuthor(String author) {
		if (!authors.contains(author)) authors.add(author);
	}

	@Override
	public String source() {
		return source;
	}

	@Override
	public List<String> names() {
		return names;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public double priority() {
		return priority;
	}

	@Override
	public List<String> authors() {
		return authors;
	}

	@Override
	public List<ParameterTagData> params() {
		return parameterTags;
	}

	@Override
	public Map<String, Object> tags() {
		return implNotes;
	}

	private String formulateSource(Element source) {
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
