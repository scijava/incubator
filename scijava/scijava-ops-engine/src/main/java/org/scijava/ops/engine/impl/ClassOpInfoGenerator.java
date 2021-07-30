
package org.scijava.ops.engine.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.OpInfoGenerator;
import org.scijava.ops.api.OpUtils;
import org.scijava.ops.discovery.Discoverer;
import org.scijava.ops.engine.matcher.impl.OpClassInfo;
import org.scijava.ops.spi.Op;
import org.scijava.plugin.Plugin;

public class ClassOpInfoGenerator implements OpInfoGenerator {

	private final List<Discoverer> discoverers;

	public ClassOpInfoGenerator(Discoverer... d) {
		this.discoverers = Arrays.asList(d);
	}

	@Override
	public List<OpInfo> generateInfos() {
		List<OpInfo> infos = discoverers.stream() //
			.flatMap(d -> d.implementingClasses(Op.class).stream()) //
			.filter(cls -> cls.getAnnotation(Plugin.class) != null) //
			.map(cls -> {
				Plugin p = cls.getAnnotation(Plugin.class);
				String[] parsedOpNames = OpUtils.parseOpNames(p.name());
				return new OpClassInfo(cls, parsedOpNames);
			}) //
			.collect(Collectors.toList());
		return infos;
	}

}
