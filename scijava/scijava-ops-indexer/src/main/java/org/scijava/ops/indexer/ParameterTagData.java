package org.scijava.ops.indexer;

import static org.scijava.ops.indexer.RuntimeJavadocHelper.tagElementSeparator;

import java.util.HashMap;
import java.util.Map;

public class ParameterTagData {

	public enum IO_TYPE {
		INPUT,
		OUTPUT,
		MUTABLE,
		CONTAINER
	}

	private final IO_TYPE ioType;
	private final String name;
	private final String type;

	private final String desc;

	public ParameterTagData(IO_TYPE ioType, String block)
	{
		this(ioType, block, null);
	}

	public ParameterTagData(IO_TYPE ioType, String block, String type)
	{
		this(ioType, block, type, null);
	}

	public ParameterTagData(IO_TYPE ioType, String block,
			String type, String paramName)
	{
		// Assign io
		this.ioType = ioType;
		// Assign name
		String[] elements = tagElementSeparator.split(block, 2);
		if (paramName != null) {
			this.name = paramName;
		}
		else if (hasName(elements[0])) {
			elements = tagElementSeparator.split(block, 3);
			this.name = elements[1];
		}
		else {
			this.name = ioType.toString();
		}
		// Assign description
		this.desc = elements[elements.length - 1];
		// Assign type
		this.type = type;
	}

	public Map<String, Object> data() {
		Map<String, Object> map = new HashMap<>();
		map.put(ioType.toString(), name);
		if (type != null) {
			map.put("type", type);
		}
		map.put("description", desc);

		return map;
	}

	// -- Utility methods -- //

	private static final String[] tagsWithNames = {
			"input",
			"output",
			"container",
			"mutable",
			"param",
	};

	private static boolean hasName(String tag) {
		for(String s: tagsWithNames)
			if(tag.equals(s)) return true;
		return false;
	}
}
