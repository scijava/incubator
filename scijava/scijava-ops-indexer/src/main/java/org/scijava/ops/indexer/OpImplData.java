
package org.scijava.ops.indexer;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A data structure containing all the metadata needed to define an Op
 *
 * @author Gabriel Selzer
 */
public interface OpImplData {

	/**
	 * A {@link URI} identifying the code providing an Op's functionality. In this
	 * URI, the path is to a Java {@link Class}, {@link java.lang.reflect.Method},
	 * or {@link java.lang.reflect.Field}, and the corresponding scheme being
	 * either {@code javaClass}, {@code javaMethod}, or {@code javaField}.
	 *
	 * @return the source
	 */
	String source();

	/**
	 * A {@link List} of {@link String}s describing the name(s) of the Op. There
	 * must be at least one.
	 * 
	 * @return the {@link List} of names of this Op
	 */
	List<String> names();

	/**
	 * A description of the functionality provided by this Op.
	 * 
	 * @return the description of this Op
	 */
	String description();

	/**
	 * The priority of this Op.
	 * 
	 * @return the priority of this Op
	 */
	double priority();

	/**
	 * A {@link List} of the authors of this Op
	 * 
	 * @return the {@link List} of authors
	 */
	List<String> authors();

	/**
	 * A {@link List} of {@link ParameterTagData}, describing the input and output
	 * parameters of this Op.
	 * 
	 * @return the {@link List} of parameters
	 */
	List<ParameterTagData> params();

	/**
	 * A {@link Map} used to store any implementation-specific and/or optional
	 * tags.
	 * 
	 * @return the {@link Map} of additional tags
	 */
	Map<String, Object> tags();

	/**
	 * Returns a {@link Map} storing the needed Op data hierarchically.
	 * 
	 * @return the {@link Map} of data.
	 */
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
