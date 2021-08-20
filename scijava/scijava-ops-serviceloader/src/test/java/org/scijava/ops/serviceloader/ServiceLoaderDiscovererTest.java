package org.scijava.ops.serviceloader;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.scijava.discovery.Discoverer;
import org.scijava.ops.spi.Op;
import org.scijava.ops.spi.OpCollection;

public class ServiceLoaderDiscovererTest {

	@Test
	public void testServiceLoaderWithOps() {
		Discoverer d = new ServiceLoaderDiscoverer();
		List<Class<Op>> implementingClasses = d.implementingClasses(Op.class);
		Assert.assertTrue(implementingClasses.contains(ServiceBasedAdder.class));
		Assert.assertEquals(implementingClasses.size(), 1);
	}

	@Test
	public void testServiceLoaderWithOpCollections() {
		Discoverer d = new ServiceLoaderDiscoverer();
		List<Class<OpCollection>> implementingClasses = d.implementingClasses(OpCollection.class);
		Assert.assertTrue(implementingClasses.contains(ServiceBasedMultipliers.class));
		Assert.assertEquals(implementingClasses.size(), 1);
	}
}
