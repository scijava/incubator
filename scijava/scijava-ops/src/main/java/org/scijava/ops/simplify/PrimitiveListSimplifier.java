package org.scijava.ops.simplify;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

/**
 * TODO: This is probably bad practice. It would be ideal to maintain the exact
 * subclass of the {@link List} so that behavior is retained. e.g. we do not
 * want {@link LinkedList}s becoming {@link ArrayList}s.
 * 
 * @author Gabriel Selzer
 */
@Plugin(type = Op.class, name = "simplify")
@Parameter(key = "inList")
@Parameter(key = "simpleList", itemIO = ItemIO.OUTPUT)
public class PrimitiveListSimplifier<T, U> implements Function<List<T>, List<Number>>{

	@OpDependency(name = "simplify")
	private Function<T, Number> elementSimplifier;

	@Override
	public List<Number> apply(List<T> t) {
		return t.stream().map(e -> (Number) e).collect(Collectors.toList());
	}

}

