package org.scijava.ops.indexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ImplData {


	String type();

	String source();

	List<String> names();

	String description();

	double priority();

	List<String> authors();

	List<ParameterTagData> params();

	Map<String, Object> tags();

	default Map<String, Object> dumpData() {
		Map<String, Object> map = new HashMap<>();
		map.put("source", source());
		map.put("names", names().toArray(String[]::new));
		map.put("description", description());
		map.put("priority", priority());
		map.put("authors", authors().toArray(String[]::new));
		List<Map<String, Object>> params = params().stream() //
			.map(ParameterTagData::data) //
			.collect(Collectors.toList());
		map.put("parameters", params.toArray(Map[]::new));
		map.put("tags", tags());
		return map;
	}


}
