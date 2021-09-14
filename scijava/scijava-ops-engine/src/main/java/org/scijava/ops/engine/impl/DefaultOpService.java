/*
 * #%L
 * SciJava Operations: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2018 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.ops.engine.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.scijava.Context;
import org.scijava.InstantiableException;
import org.scijava.log.LogService;
import org.scijava.ops.api.OpBuilder;
import org.scijava.ops.api.OpEnvironment;
import org.scijava.ops.api.OpInfoGenerator;
import org.scijava.ops.discovery.Discoverer;
import org.scijava.ops.discovery.Implementation;
import org.scijava.ops.engine.OpHistoryService;
import org.scijava.ops.engine.OpService;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginInfo;
import org.scijava.plugin.PluginService;
import org.scijava.plugin.SciJavaPlugin;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;
import org.scijava.types.TypeService;

/**
 * Service to provide a list of available ops structured in a prefix tree and to
 * search for ops matching specified types.
 *
 * @author David Kolb
 */
@Plugin(type = Service.class)
public class DefaultOpService extends AbstractService implements OpService {

	private OpEnvironment env;

	/**
	 * Begins declaration of an op matching request for locating an op with a
	 * particular name. Additional criteria are specified as chained method calls
	 * on the returned {@link OpBuilder} object. See {@link OpBuilder} for
	 * examples.
	 * 
	 * @param opName The name of the op to be matched.
	 * @return An {@link OpBuilder} for refining the search criteria for an op.
	 * @see OpBuilder
	 */
	@Override
	public OpBuilder op(final String opName) {
		return env().op(opName);
	}

	/** Retrieves the motherlode of available ops. */
	@Override
	public OpEnvironment env() {
		if (env == null) initEnv();
		return env;
	}

	// -- Helper methods - lazy initialization --

	private synchronized void initEnv() {
		if (env != null) return;
		PluginService plugins = context().getService(PluginService.class);
		LogService log = context().getService(LogService.class);
		TypeService types = context().getService(TypeService.class);
		OpHistoryService history = context().getService(OpHistoryService.class);
		List<OpInfoGenerator> infoGenerators = Arrays.asList(
			new PluginBasedClassOpInfoGenerator(plugins),
			new PluginBasedOpCollectionInfoGenerator(plugins));
		env = new DefaultOpEnvironment(new PluginBasedDiscoverer(context()), types,
			log, history, infoGenerators);
	}
}

class PluginBasedDiscoverer implements Discoverer {

	private final PluginService p;

	public PluginBasedDiscoverer(Context ctx) {
		p = ctx.getService(PluginService.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<? extends Class<T>> implementingClasses(Class<T> c) {
		if (!SciJavaPlugin.class.isAssignableFrom(c)) {
			throw new UnsupportedOperationException(
				"Current discovery mechanism tied to SciJava Context; only able to search for SciJavaPlugins");
		}
		List<PluginInfo<SciJavaPlugin>> infos = p.getPluginsOfType(
			(Class<SciJavaPlugin>) c);
		return infos.stream() //
			.map(info -> makeClassOrNull(c, info)) //
			.filter(cls -> cls != null).collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<? extends T> implementingInstances(Class<T> c,
		Class<?>[] constructorClasses, Object[] constructorArgs)
	{
		if (!SciJavaPlugin.class.isAssignableFrom(c)) {
			throw new UnsupportedOperationException(
				"Current discovery mechanism tied to SciJava Context; only able to search for SciJavaPlugins");
		}
		List<SciJavaPlugin> instances = p.createInstancesOfType(
			(Class<SciJavaPlugin>) c);
		return instances.stream().map(instance -> (T) instance).collect(Collectors
			.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<Implementation<T>> implementationsOf(Class<T> c) {
		if (!SciJavaPlugin.class.isAssignableFrom(c)) {
			throw new UnsupportedOperationException(
				"Current discovery mechanism tied to SciJava Context; only able to search for SciJavaPlugins");
		}
		List<PluginInfo<SciJavaPlugin>> instances = p.getPluginsOfType(
			(Class<SciJavaPlugin>) c);
		return instances.stream() //
			.map(instance -> makeDiscoveryOrNull(c, instance)) //
			.filter(d -> d.implementation() != null) //
			.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private <T> Class<T> makeClassOrNull(@SuppressWarnings("unused") Class<T> type,
		PluginInfo<SciJavaPlugin> instance)
	{
		try {
			return (Class<T>) instance.loadClass();
		}
		catch (InstantiableException exc) {
			return null;
		}
	}

	private <T> Implementation<T> makeDiscoveryOrNull(Class<T> type,
		PluginInfo<SciJavaPlugin> instance)
	{
		return new Implementation<>(makeClassOrNull(type, instance), type, instance
			.getName());
	}

}
