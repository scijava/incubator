package org.scijava.ops.indexer;

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

	public ParameterTagData(IO_TYPE ioType, String name, String description, String type)
	{
		// Assign io
		this.ioType = ioType;
		this.name = name;
		this.desc = description;
		this.type = type;
	}

	public Map<String, Object> data() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("parameter type", ioType.toString());
		map.put("description", desc);
		if (type != null) {
			map.put("type", type);
		}
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
