
package org.scijava.ops.indexer;

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

public class OpFieldImplData implements OpImplData {

	private final Map<String, Object> implNotes = new HashMap<>();

	private final List<String> authors = new ArrayList<>();

	private final List<String> names = new ArrayList<>();

	private final List<ParameterTagData> params = new ArrayList<>();

	private final String source;

	private double priority = 0.0;

	private final String type;
	private String description = "";

	public OpFieldImplData(Element source, String doc, ProcessingEnvironment env) {
		String[] sections = RuntimeJavadocHelper.blockSeparator.split(doc);
		for (String section : sections) {
			String[] elements = tagElementSeparator.split(section,2);
			switch (elements[0]) {
				case "input":
					String[] inData = tagElementSeparator.split(elements[1], 2);
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.INPUT,
							inData[0], inData[1], null));
					break;
				case "output":
					// NB outputs generally don't have names
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.OUTPUT,
							"output", elements[1], null));
					break;
				case "container":
					String[] containerData = tagElementSeparator.split(elements[1], 2);
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.CONTAINER,
							containerData[0], containerData[1], null));
					break;
				case "mutable":
					String[] mutableData = tagElementSeparator.split(elements[1], 2);
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.MUTABLE,
							mutableData[0], mutableData[1], null));
					break;
				case "author":
					authors.add(elements[1]);
					break;
				case "implNote":
					var implElements = tagElementSeparator.split(elements[1]);
					if (implElements.length > 1) {
						for (int i = 1; i < implElements.length; i++) {
							String[] kv = implElements[i].split("=", 2);
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

		this.source = "javaField:/" + URLEncoder.encode(source
			.getEnclosingElement() + "$" + source, StandardCharsets.UTF_8);
		this.type = source.asType().toString();

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
		return params;
	}

	@Override
	public Map<String, Object> tags() {
		return implNotes;
	}

}
