package org.scijava.javadoc.parser;

import static org.scijava.javadoc.parser.RuntimeJavadocHelper.blockSeparator;
import static org.scijava.javadoc.parser.RuntimeJavadocHelper.tagElementSeparator;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class ImplClassData implements ImplData {
	private final Map<String, Object> implNotes = new HashMap<>();

	private final List<String> authors = new ArrayList<>();

	private final List<ParameterTagData> params = new ArrayList<>();

	private String implType;

	private final String source;

	private String description;



	public ImplClassData(Element source, String doc) {
		if (!doc.contains("@implNote")){
			throw new IllegalArgumentException(source + "'s Javadoc does not contain an '@implNote' tag!");
		}
		String[] sections = blockSeparator.split(doc);
		for (String section: sections) {
			String[] elements = tagElementSeparator.split(section);
			switch(elements[0]) {
				case "input":
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.INPUT, section));
					break;
				case "output":
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.OUTPUT, section));
					break;
				case "container":
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.CONTAINER, section));
					break;
				case "mutable":
					params.add(new ParameterTagData(ParameterTagData.IO_TYPE.MUTABLE, section));
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
										implNotes.put(kv[0], value.split("\\s*,\\s*"));
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
		// handle inner classes
		String srcString = source.toString();
		Element parent = source.getEnclosingElement();
		while(parent.getKind() == ElementKind.CLASS) {
			int badPeriod = srcString.lastIndexOf('.');
			srcString = srcString.substring(0, badPeriod) + '$' + srcString.substring(badPeriod+1);
			parent = parent.getEnclosingElement();
		}
		this.source = "javaClass:/" + URLEncoder.encode(srcString, StandardCharsets.UTF_8);
	}

	@Override public String type() {
		return implType;
	}

	@Override public String source() {
		return source;
	}

	@Override public Map<String, Object> tags() {
		Map<String, Object> map = new HashMap<>();
		map.put("description", description);
		map.put("authors", authors);
		map.put("parameters", params.stream().map(ParameterTagData::data).collect(
				Collectors.toList()));
		map.putAll(implNotes);
		return map;
	}

}
