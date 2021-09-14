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

package org.scijava.ops.impl;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.scijava.Context;
import org.scijava.function.Producer;
import org.scijava.ops.AbstractTestEnvironment;
import org.scijava.ops.Op;
import org.scijava.ops.OpCollection;
import org.scijava.ops.OpDependency;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpHistoryService;
import org.scijava.ops.OpInstance;
import org.scijava.ops.OpService;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

@Plugin(type = OpCollection.class)
public class OpCachingTest extends AbstractTestEnvironment {

	/**
	 * Obtains a new {@link OpService} for each test (we also need to create a new
	 * {@link Context} to ensure that we start with a fresh cache).
	 */
	@Before
	public void setUpEach() {
		context = new Context(OpService.class, OpHistoryService.class, PluginService.class,
			TypeService.class);
		ops = context.getService(OpService.class);
	}

	/**
	 * Basic Op used to test cache functionality. NB we use an {@link OpField}
	 * here because we KNOW that we will only create ONE instance of this Op under
	 * the hood.
	 */
	@OpField(names = "test.basicOp")
	public final Producer<String> basicOp = () -> "This Op should be cached";

	private DefaultOpEnvironment getDefaultOpEnv() {
		OpEnvironment opEnv = ops.env();
		if (!(opEnv instanceof DefaultOpEnvironment)) fail(
			"OpCachingTest expects a DefaultOpEnvironment (since it is testing the caching behavior of that class).");
		return (DefaultOpEnvironment) opEnv;
	}

	@SuppressWarnings("unchecked")
	private Map<MatchingConditions, OpInstance> getOpCache(DefaultOpEnvironment opEnv)
		throws NoSuchFieldException, SecurityException, IllegalArgumentException,
		IllegalAccessException
	{
		// use reflection to grab a hold of the opCache
		Field cacheField = opEnv.getClass().getDeclaredField("opCache");
		cacheField.setAccessible(true);
		return (Map<MatchingConditions, OpInstance>) cacheField.get(opEnv);
	}

	@Test
	public void cacheOp() throws SecurityException, IllegalArgumentException,
		NoSuchFieldException, IllegalAccessException
	{
		// put the Op in the cache
		DefaultOpEnvironment defOpEnv = getDefaultOpEnv();
		Producer<String> op = defOpEnv.op("test.basicOp").input().outType(
			String.class).producer();

		Map<MatchingConditions, OpInstance> opCache = getOpCache(defOpEnv);

		// assert there is exactly one Op in the cache
		OpInstance cachedInstance = opCache.values().iterator().next();
		Assertions.assertEquals(opCache.size(), 1, 0);
		Assertions.assertEquals(basicOp, cachedInstance.op(),
			"Object in cache was not the same Object that was returned!");

		// assert that the same call to the matcher returns our Object
		MatchingConditions cachedConditions = opCache.keySet().iterator().next();
		String newString = "This Op invaded the cache!";
		Producer<String> newProducer = () -> newString;
		OpInstance invaderInstance = OpInstance.of(newProducer, cachedInstance
			.info(), cachedInstance.typeVarAssigns());
		opCache.replace(cachedConditions, invaderInstance);

		Producer<String> invadedOp = defOpEnv.op("test.basicOp").input().outType(
			String.class).producer();
		Assertions.assertEquals(newProducer.create(), invadedOp.create(),
			"Op returned did not match the Op inserted into the cache!");

	}

	@Test
	public void cacheOpAndDependencies() throws NoSuchFieldException,
		SecurityException, IllegalArgumentException, IllegalAccessException
	{
		// put the Op in the cache
		DefaultOpEnvironment defOpEnv = getDefaultOpEnv();
		Producer<String> op = defOpEnv.op("test.complicatedOp").input().outType(
			String.class).producer();

		Map<MatchingConditions, OpInstance> opCache = getOpCache(defOpEnv);

		// assert there are exactly two Ops in the cache
		Assertions.assertEquals(opCache.size(), 2, 0);

		// assert that complicatedOp is in the cache (
		Optional<MatchingConditions> complicatedOptional = opCache.keySet().stream().filter(
			condition -> condition.ref().getName().equals("test.complicatedOp")).findFirst();
		Assertions.assertFalse(complicatedOptional.isEmpty(),
			"test.complicatedOp not in cache!");
		Assertions.assertTrue(opCache.get(complicatedOptional.get()).op() instanceof ComplicatedOp,
			"Object in cache was not an instance of ComplicatedOp!");

		// assert that basic Op is also in the cache
		Optional<MatchingConditions> basicOptional = opCache.keySet().stream().filter(condition -> condition
			.ref().getName().equals("test.basicOp")).findFirst();
		Assertions.assertFalse(basicOptional.isEmpty(),
			"test.basicOp not in cache despite being an OpDependency of test.complicatedOp");
		Assertions.assertEquals(opCache.get(basicOptional.get()).op(), basicOp,
			"Object in cache was not the same Object that was returned!");
	}

}

@Plugin(type = Op.class, name = "test.complicatedOp")
class ComplicatedOp implements Producer<String> {

	@OpDependency(name = "test.basicOp")
	private Producer<String> basicOp;

	@Override
	public String create() {
		return basicOp.create();
	}
}
