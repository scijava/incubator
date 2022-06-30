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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

import org.scijava.discovery.Discoverer;
import org.scijava.discovery.plugin.PluginBasedDiscoverer;
import org.scijava.discovery.therapi.TherapiDiscoverer;
import org.scijava.log2.Logger;
import org.scijava.log2.StderrLoggerFactory;
import org.scijava.ops.api.OpBuilder;
import org.scijava.ops.api.OpEnvironment;
import org.scijava.ops.api.OpHistory;
import org.scijava.ops.api.OpInfoGenerator;
import org.scijava.ops.engine.OpService;
import org.scijava.parse2.Parser;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;
import org.scijava.types.DefaultTypeReifier;
import org.scijava.types.TypeReifier;

/**
 * Service to provide a list of available ops structured in a prefix tree and to
 * search for ops matching specified types.
 *
 * @author David Kolb
 */
@Plugin(type = Service.class)
public class DefaultOpService extends AbstractService implements OpService {

	private OpEnvironment env;

	private OpHistory history;

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

	@Override
	public OpHistory history() {
		if (history == null) initHistory();
		return history;
	}

	// -- Helper methods - lazy initialization --

	private synchronized void initEnv() {
		if (env != null) return;
		Logger log = new StderrLoggerFactory().create();
		Parser parser = ServiceLoader.load(Parser.class).findFirst().get();
		OpHistory history = history();
		List<Discoverer> ds = Discoverer.all(ServiceLoader::load);
		TypeReifier types = new DefaultTypeReifier(log, ds);
		env = new DefaultOpEnvironment(types, log, history, ds);
	}

	private synchronized void initHistory() {
		if (history != null) return;
		history = new DefaultOpHistory();
	}
	
}
