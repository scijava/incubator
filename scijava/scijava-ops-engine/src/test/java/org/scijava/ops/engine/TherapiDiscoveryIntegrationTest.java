package org.scijava.ops.engine;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.discovery.Discoverer;
import org.scijava.discovery.Discovery;
import org.scijava.discovery.therapi.TherapiDiscoverer;
import org.scijava.log2.Logger;
import org.scijava.log2.StderrLogFactory;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.OpInfoGenerator;
import org.scijava.ops.engine.impl.OpClassBasedClassOpInfoGenerator;
import org.scijava.ops.engine.impl.OpCollectionInfoGenerator;
import org.scijava.ops.spi.Op;
import org.scijava.ops.spi.OpCollection;
import org.scijava.parse2.Parser;

import java.util.List;
import java.util.ServiceLoader;

public class TherapiDiscoveryIntegrationTest {

	@Test
	public void opDiscoveryRegressionIT() {
		Parser p = ServiceLoader.load(Parser.class).findFirst().get();
		final Discoverer d = new TherapiDiscoverer(p);
		final List<Discovery<Class<Op>>> discoveries = d.discoveriesOfType(Op.class);
		Assert.assertEquals(0, discoveries.size());

		final Logger l = new StderrLogFactory().create();
		final OpInfoGenerator g = new OpClassBasedClassOpInfoGenerator(l, d);
		final List<OpInfo> infos = g.generateInfos();
		Assert.assertEquals(0, infos.size());
	}

}