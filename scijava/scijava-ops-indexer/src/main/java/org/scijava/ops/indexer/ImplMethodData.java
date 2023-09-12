package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.RuntimeJavadocHelper.blockSeparator;
import static org.scijava.ops.indexer.RuntimeJavadocHelper.tagElementSeparator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class ImplMethodData implements ImplData{
	
	private final List<ParameterTagData> parameterTagData = new ArrayList<>() ;

	private final Map<String, Object> implNotes = new HashMap<>();

	private final List<String> authors = new ArrayList<>();

	private String implType;

	private final String source;

	private String description;



	public ImplMethodData(ProcessingEnvironment processingEnv, ExecutableElement source, String doc) {
		if (!doc.contains("@implNote")){
			throw new IllegalArgumentException(source + "'s Javadoc does not contain an '@implNote' tag!");
		}
		String[] sections = blockSeparator.split(doc);
		Iterator<? extends VariableElement> paramItr =
				source.getParameters().iterator();
		for (String section: sections) {
			String[] elements = tagElementSeparator.split(section);
			switch(elements[0]) {
				case "param":
					// Ignore type variables
					if (elements[1].contains("<.*>")) continue;
					String name;
					String type;
					if (paramItr.hasNext()) {
						var element = paramItr.next();
						type = element.asType().toString();
						name = element.getSimpleName().toString();
					}
					else {
						type = null;
						name = null;
					}
					parameterTagData.add(new ParameterTagData(ParameterTagData.IO_TYPE.INPUT, section, type, name));
					break;
				case "return":
					parameterTagData.add(new ParameterTagData(ParameterTagData.IO_TYPE.OUTPUT, section, source.getReturnType().toString()));
					break;
				case "author":
					authors.add(tagElementSeparator.split(section, 2)[1]);
					break;
				case "implNote" :
						implType = elements[1];
						if (elements.length > 2) {
							for (int i = 2; i < elements.length; i++) {
								String[] kv = elements[i].split("=", 2);
								if (kv.length == 2) {
									String value = kv[1].replaceAll("^[,\"']+|[,\"']+$", "");
									if (value.contains(",")) {
										implNotes.put(kv[0], value.split(","));
									}
									else {
										implNotes.put(kv[0], value);
									}
								}
							}
						}
						break;
				default:
					if (description == null) {
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
		Map<String, Object> map = new HashMap<>();
		map.put("description", description);
		if (!parameterTagData.isEmpty()) {
			map.put("parameters",
					parameterTagData.stream().map(ParameterTagData::data).collect(Collectors.toList()));
		}
		map.put("authors", authors);
		map.putAll(implNotes);


		return map;
	}

	@Override
	public String type() {
		return implType;
	}

	@Override
	public String source() {
		return source;
	}

}
