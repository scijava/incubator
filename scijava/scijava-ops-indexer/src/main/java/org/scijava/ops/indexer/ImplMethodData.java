package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.RuntimeJavadocHelper.blockSeparator;
import static org.scijava.ops.indexer.RuntimeJavadocHelper.tagElementSeparator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class ImplMethodData implements ImplData{

	private final Map<String, Object> implNotes = new HashMap<>();

	private final List<String> authors = new ArrayList<>();

	private final List<String> names = new ArrayList<>();

	private final List<ParameterTagData> parameterTags = new ArrayList<>();

	private final String source;

	private String implType;

	private double priority = 0.0;

	private String description = "";

	public ImplMethodData(ProcessingEnvironment processingEnv, ExecutableElement source, String doc) {
		String[] sections = blockSeparator.split(doc);
		Iterator<? extends VariableElement> paramItr =
				source.getParameters().iterator();
		for (String section: sections) {
			String[] elements = tagElementSeparator.split(section, 2);
			switch(elements[0]) {
				case "param":
					// Ignore type variables
					if (elements[1].contains("<.*>")) continue;
					String name = null;
					String type = null;
					if (paramItr.hasNext()) {
						var element = paramItr.next();
						type = element.asType().toString();
						name = element.getSimpleName().toString();
					}
					String[] paramData = tagElementSeparator.split(elements[1], 2);
					parameterTags.add(new ParameterTagData(ParameterTagData.IO_TYPE.INPUT, name, paramData[1], type));
					break;
				case "return":
					parameterTags.add(new ParameterTagData(ParameterTagData.IO_TYPE.OUTPUT, "output", elements[1], source.getReturnType().toString()));
					break;
				case "author":
					authors.add(elements[1]);
					break;
				case "implNote" :
					var implElements = tagElementSeparator.split(elements[1]);
					implType = implElements[0];
					if (implElements.length > 2) {
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

		// First, append the class
		StringBuilder sb = new StringBuilder();
		sb.append(source.getEnclosingElement());
		sb.append(".");
		// Then, append the method
		sb.append(source.getSimpleName());

		// Then, append the parameters
		var params = source.getParameters();
		sb.append("(");
		for (int i = 0; i < params.size(); i++) {
			var d = processingEnv.getTypeUtils().erasure(params.get(i).asType());
			sb.append(d);
			if (i < params.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(")");

		this.source = "javaMethod:/" + URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8);

	}

	@Override
	public Map<String, Object> tags() {
		return implNotes;
	}

	@Override
	public String type() {
		return implType;
	}

	@Override
	public String source() {
		return source;
	}

	@Override public List<String> names() {
		return names;
	}

	@Override public String description() {
		return description;
	}

	@Override public double priority() {
		return priority;
	}

	@Override public List<String> authors() {
		return authors;
	}

	@Override public List<ParameterTagData> params() {
		return parameterTags;
	}

}
