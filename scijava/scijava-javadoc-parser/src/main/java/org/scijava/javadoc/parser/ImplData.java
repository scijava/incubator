package org.scijava.javadoc.parser;

import java.util.HashMap;
import java.util.Map;

public interface ImplData {


	String type();

	String source();

	Map<String, Object> tags();

	default Map<String, Object> dumpData() {
		Map<String, Object> map = new HashMap<>();
		map.put("source", source());
		map.put("tags", tags());
		return map;
	}


}
