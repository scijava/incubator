/*
 * #%L
 * SciJava Operations: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2016 - 2019 SciJava Ops developers.
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

package org.scijava.ops;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.ops.function.Functions;
import org.scijava.ops.function.Inplaces;
import org.scijava.ops.function.Producer;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

@Plugin(type = OpCollection.class)
public class OpMethodTestOps {

	// -- Functions -- //
	@OpMethod(names = "test.multiplyNumericStrings", type = Producer.class)
	@Parameter(key = "multipliedNumericStrings", itemIO = ItemIO.OUTPUT)
	public static Integer multiplyNumericStringsProducer() {
		return Integer.valueOf(1);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Function.class)
	public static Integer multiplyNumericStringsFunction1(String in1)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = BiFunction.class)
	public static Integer multiplyNumericStringsFunction2(String in1, String in2)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity3.class)
	public static Integer multiplyNumericStringsFunction3(String in1, String in2, String in3)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity4.class)
	public static Integer multiplyNumericStringsFunction4(String in1, String in2, String in3, String in4)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity5.class)
	public static Integer multiplyNumericStringsFunction5(String in1, String in2, String in3, String in4, String in5)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity6.class)
	public static Integer multiplyNumericStringsFunction6(String in1, String in2, String in3, String in4, String in5, String in6)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity7.class)
	public static Integer multiplyNumericStringsFunction7(String in1, String in2, String in3, String in4, String in5, String in6, String in7)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity8.class)
	public static Integer multiplyNumericStringsFunction8(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity9.class)
	public static Integer multiplyNumericStringsFunction9(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity10.class)
	public static Integer multiplyNumericStringsFunction10(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);
		out *= Integer.parseInt(in10);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity11.class)
	public static Integer multiplyNumericStringsFunction11(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);
		out *= Integer.parseInt(in10);
		out *= Integer.parseInt(in11);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity12.class)
	public static Integer multiplyNumericStringsFunction12(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);
		out *= Integer.parseInt(in10);
		out *= Integer.parseInt(in11);
		out *= Integer.parseInt(in12);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity13.class)
	public static Integer multiplyNumericStringsFunction13(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);
		out *= Integer.parseInt(in10);
		out *= Integer.parseInt(in11);
		out *= Integer.parseInt(in12);
		out *= Integer.parseInt(in13);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity14.class)
	public static Integer multiplyNumericStringsFunction14(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);
		out *= Integer.parseInt(in10);
		out *= Integer.parseInt(in11);
		out *= Integer.parseInt(in12);
		out *= Integer.parseInt(in13);
		out *= Integer.parseInt(in14);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity15.class)
	public static Integer multiplyNumericStringsFunction15(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);
		out *= Integer.parseInt(in10);
		out *= Integer.parseInt(in11);
		out *= Integer.parseInt(in12);
		out *= Integer.parseInt(in13);
		out *= Integer.parseInt(in14);
		out *= Integer.parseInt(in15);

		return out;
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity16.class)
	public static Integer multiplyNumericStringsFunction16(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, String in16)
	{
		Integer out = Integer.valueOf(1);
		
		out *= Integer.parseInt(in1);
		out *= Integer.parseInt(in2);
		out *= Integer.parseInt(in3);
		out *= Integer.parseInt(in4);
		out *= Integer.parseInt(in5);
		out *= Integer.parseInt(in6);
		out *= Integer.parseInt(in7);
		out *= Integer.parseInt(in8);
		out *= Integer.parseInt(in9);
		out *= Integer.parseInt(in10);
		out *= Integer.parseInt(in11);
		out *= Integer.parseInt(in12);
		out *= Integer.parseInt(in13);
		out *= Integer.parseInt(in14);
		out *= Integer.parseInt(in15);
		out *= Integer.parseInt(in16);

		return out;
	}

	// -- Computers -- //
	
	@OpMethod(names = "test.doubleList", type = Computers.Arity0.class)
	public static void doublesToListArity1(List<Double> output) {
		output.clear();
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity1.class)
	public static void doublesToList1(String in1, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity2.class)
	public static void doublesToList2(String in1, String in2, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity3.class)
	public static void doublesToList3(String in1, String in2, String in3, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity4.class)
	public static void doublesToList4(String in1, String in2, String in3, String in4, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity5.class)
	public static void doublesToList5(String in1, String in2, String in3, String in4, String in5, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity6.class)
	public static void doublesToList6(String in1, String in2, String in3, String in4, String in5, String in6, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity7.class)
	public static void doublesToList7(String in1, String in2, String in3, String in4, String in5, String in6, String in7, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity8.class)
	public static void doublesToList8(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity9.class)
	public static void doublesToList9(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity10.class)
	public static void doublesToList10(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
		output.add(Double.parseDouble(in10));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity11.class)
	public static void doublesToList11(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
		output.add(Double.parseDouble(in10));
		output.add(Double.parseDouble(in11));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity12.class)
	public static void doublesToList12(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
		output.add(Double.parseDouble(in10));
		output.add(Double.parseDouble(in11));
		output.add(Double.parseDouble(in12));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity13.class)
	public static void doublesToList13(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
		output.add(Double.parseDouble(in10));
		output.add(Double.parseDouble(in11));
		output.add(Double.parseDouble(in12));
		output.add(Double.parseDouble(in13));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity14.class)
	public static void doublesToList14(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
		output.add(Double.parseDouble(in10));
		output.add(Double.parseDouble(in11));
		output.add(Double.parseDouble(in12));
		output.add(Double.parseDouble(in13));
		output.add(Double.parseDouble(in14));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity15.class)
	public static void doublesToList15(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
		output.add(Double.parseDouble(in10));
		output.add(Double.parseDouble(in11));
		output.add(Double.parseDouble(in12));
		output.add(Double.parseDouble(in13));
		output.add(Double.parseDouble(in14));
		output.add(Double.parseDouble(in15));
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity16.class)
	public static void doublesToList16(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, String in16, List<Double> output) {
		output.clear();
		
		output.add(Double.parseDouble(in1));
		output.add(Double.parseDouble(in2));
		output.add(Double.parseDouble(in3));
		output.add(Double.parseDouble(in4));
		output.add(Double.parseDouble(in5));
		output.add(Double.parseDouble(in6));
		output.add(Double.parseDouble(in7));
		output.add(Double.parseDouble(in8));
		output.add(Double.parseDouble(in9));
		output.add(Double.parseDouble(in10));
		output.add(Double.parseDouble(in11));
		output.add(Double.parseDouble(in12));
		output.add(Double.parseDouble(in13));
		output.add(Double.parseDouble(in14));
		output.add(Double.parseDouble(in15));
		output.add(Double.parseDouble(in16));
	}

	// -- Inplaces -- //


	@OpMethod(names = "test.addDoubles1", type = Inplaces.Arity1.class)
	public static void addDoubles1(double[] io) {
		for (int i = 0; i < io.length; i++) {
		}
	}

	@OpMethod(names = "test.addDoubles2_1", type = Inplaces.Arity2_1.class)
	public static void addDoubles2_1(double[] io, double[] in2) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
		}
	}

	@OpMethod(names = "test.addDoubles2_2", type = Inplaces.Arity2_2.class)
	public static void addDoubles2_2(double[] in1, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
		}
	}

	@OpMethod(names = "test.addDoubles3_1", type = Inplaces.Arity3_1.class)
	public static void addDoubles3_1(double[] io, double[] in2, double[] in3) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
		}
	}

	@OpMethod(names = "test.addDoubles3_2", type = Inplaces.Arity3_2.class)
	public static void addDoubles3_2(double[] in1, double[] io, double[] in3) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
		}
	}

	@OpMethod(names = "test.addDoubles3_3", type = Inplaces.Arity3_3.class)
	public static void addDoubles3_3(double[] in1, double[] in2, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
		}
	}

	@OpMethod(names = "test.addDoubles4_1", type = Inplaces.Arity4_1.class)
	public static void addDoubles4_1(double[] io, double[] in2, double[] in3, double[] in4) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
		}
	}

	@OpMethod(names = "test.addDoubles4_2", type = Inplaces.Arity4_2.class)
	public static void addDoubles4_2(double[] in1, double[] io, double[] in3, double[] in4) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
		}
	}

	@OpMethod(names = "test.addDoubles4_3", type = Inplaces.Arity4_3.class)
	public static void addDoubles4_3(double[] in1, double[] in2, double[] io, double[] in4) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
		}
	}

	@OpMethod(names = "test.addDoubles4_4", type = Inplaces.Arity4_4.class)
	public static void addDoubles4_4(double[] in1, double[] in2, double[] in3, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
		}
	}

	@OpMethod(names = "test.addDoubles5_1", type = Inplaces.Arity5_1.class)
	public static void addDoubles5_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
		}
	}

	@OpMethod(names = "test.addDoubles5_2", type = Inplaces.Arity5_2.class)
	public static void addDoubles5_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
		}
	}

	@OpMethod(names = "test.addDoubles5_3", type = Inplaces.Arity5_3.class)
	public static void addDoubles5_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
		}
	}

	@OpMethod(names = "test.addDoubles5_4", type = Inplaces.Arity5_4.class)
	public static void addDoubles5_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
		}
	}

	@OpMethod(names = "test.addDoubles5_5", type = Inplaces.Arity5_5.class)
	public static void addDoubles5_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
		}
	}

	@OpMethod(names = "test.addDoubles6_1", type = Inplaces.Arity6_1.class)
	public static void addDoubles6_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
		}
	}

	@OpMethod(names = "test.addDoubles6_2", type = Inplaces.Arity6_2.class)
	public static void addDoubles6_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
		}
	}

	@OpMethod(names = "test.addDoubles6_3", type = Inplaces.Arity6_3.class)
	public static void addDoubles6_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
		}
	}

	@OpMethod(names = "test.addDoubles6_4", type = Inplaces.Arity6_4.class)
	public static void addDoubles6_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
		}
	}

	@OpMethod(names = "test.addDoubles6_5", type = Inplaces.Arity6_5.class)
	public static void addDoubles6_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
		}
	}

	@OpMethod(names = "test.addDoubles6_6", type = Inplaces.Arity6_6.class)
	public static void addDoubles6_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
		}
	}

	@OpMethod(names = "test.addDoubles7_1", type = Inplaces.Arity7_1.class)
	public static void addDoubles7_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
		}
	}

	@OpMethod(names = "test.addDoubles7_2", type = Inplaces.Arity7_2.class)
	public static void addDoubles7_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
		}
	}

	@OpMethod(names = "test.addDoubles7_3", type = Inplaces.Arity7_3.class)
	public static void addDoubles7_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
		}
	}

	@OpMethod(names = "test.addDoubles7_4", type = Inplaces.Arity7_4.class)
	public static void addDoubles7_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
		}
	}

	@OpMethod(names = "test.addDoubles7_5", type = Inplaces.Arity7_5.class)
	public static void addDoubles7_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
		}
	}

	@OpMethod(names = "test.addDoubles7_6", type = Inplaces.Arity7_6.class)
	public static void addDoubles7_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
		}
	}

	@OpMethod(names = "test.addDoubles7_7", type = Inplaces.Arity7_7.class)
	public static void addDoubles7_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_1", type = Inplaces.Arity8_1.class)
	public static void addDoubles8_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_2", type = Inplaces.Arity8_2.class)
	public static void addDoubles8_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_3", type = Inplaces.Arity8_3.class)
	public static void addDoubles8_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_4", type = Inplaces.Arity8_4.class)
	public static void addDoubles8_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_5", type = Inplaces.Arity8_5.class)
	public static void addDoubles8_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_6", type = Inplaces.Arity8_6.class)
	public static void addDoubles8_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_7", type = Inplaces.Arity8_7.class)
	public static void addDoubles8_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles8_8", type = Inplaces.Arity8_8.class)
	public static void addDoubles8_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_1", type = Inplaces.Arity9_1.class)
	public static void addDoubles9_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_2", type = Inplaces.Arity9_2.class)
	public static void addDoubles9_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_3", type = Inplaces.Arity9_3.class)
	public static void addDoubles9_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_4", type = Inplaces.Arity9_4.class)
	public static void addDoubles9_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_5", type = Inplaces.Arity9_5.class)
	public static void addDoubles9_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_6", type = Inplaces.Arity9_6.class)
	public static void addDoubles9_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_7", type = Inplaces.Arity9_7.class)
	public static void addDoubles9_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_8", type = Inplaces.Arity9_8.class)
	public static void addDoubles9_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles9_9", type = Inplaces.Arity9_9.class)
	public static void addDoubles9_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_1", type = Inplaces.Arity10_1.class)
	public static void addDoubles10_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_2", type = Inplaces.Arity10_2.class)
	public static void addDoubles10_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_3", type = Inplaces.Arity10_3.class)
	public static void addDoubles10_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_4", type = Inplaces.Arity10_4.class)
	public static void addDoubles10_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_5", type = Inplaces.Arity10_5.class)
	public static void addDoubles10_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_6", type = Inplaces.Arity10_6.class)
	public static void addDoubles10_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_7", type = Inplaces.Arity10_7.class)
	public static void addDoubles10_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_8", type = Inplaces.Arity10_8.class)
	public static void addDoubles10_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_9", type = Inplaces.Arity10_9.class)
	public static void addDoubles10_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles10_10", type = Inplaces.Arity10_10.class)
	public static void addDoubles10_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_1", type = Inplaces.Arity11_1.class)
	public static void addDoubles11_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_2", type = Inplaces.Arity11_2.class)
	public static void addDoubles11_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_3", type = Inplaces.Arity11_3.class)
	public static void addDoubles11_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_4", type = Inplaces.Arity11_4.class)
	public static void addDoubles11_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_5", type = Inplaces.Arity11_5.class)
	public static void addDoubles11_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_6", type = Inplaces.Arity11_6.class)
	public static void addDoubles11_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_7", type = Inplaces.Arity11_7.class)
	public static void addDoubles11_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_8", type = Inplaces.Arity11_8.class)
	public static void addDoubles11_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_9", type = Inplaces.Arity11_9.class)
	public static void addDoubles11_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_10", type = Inplaces.Arity11_10.class)
	public static void addDoubles11_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles11_11", type = Inplaces.Arity11_11.class)
	public static void addDoubles11_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_1", type = Inplaces.Arity12_1.class)
	public static void addDoubles12_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_2", type = Inplaces.Arity12_2.class)
	public static void addDoubles12_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_3", type = Inplaces.Arity12_3.class)
	public static void addDoubles12_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_4", type = Inplaces.Arity12_4.class)
	public static void addDoubles12_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_5", type = Inplaces.Arity12_5.class)
	public static void addDoubles12_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_6", type = Inplaces.Arity12_6.class)
	public static void addDoubles12_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_7", type = Inplaces.Arity12_7.class)
	public static void addDoubles12_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_8", type = Inplaces.Arity12_8.class)
	public static void addDoubles12_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_9", type = Inplaces.Arity12_9.class)
	public static void addDoubles12_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_10", type = Inplaces.Arity12_10.class)
	public static void addDoubles12_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_11", type = Inplaces.Arity12_11.class)
	public static void addDoubles12_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles12_12", type = Inplaces.Arity12_12.class)
	public static void addDoubles12_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_1", type = Inplaces.Arity13_1.class)
	public static void addDoubles13_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_2", type = Inplaces.Arity13_2.class)
	public static void addDoubles13_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_3", type = Inplaces.Arity13_3.class)
	public static void addDoubles13_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_4", type = Inplaces.Arity13_4.class)
	public static void addDoubles13_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_5", type = Inplaces.Arity13_5.class)
	public static void addDoubles13_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_6", type = Inplaces.Arity13_6.class)
	public static void addDoubles13_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_7", type = Inplaces.Arity13_7.class)
	public static void addDoubles13_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_8", type = Inplaces.Arity13_8.class)
	public static void addDoubles13_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_9", type = Inplaces.Arity13_9.class)
	public static void addDoubles13_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_10", type = Inplaces.Arity13_10.class)
	public static void addDoubles13_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_11", type = Inplaces.Arity13_11.class)
	public static void addDoubles13_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_12", type = Inplaces.Arity13_12.class)
	public static void addDoubles13_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles13_13", type = Inplaces.Arity13_13.class)
	public static void addDoubles13_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_1", type = Inplaces.Arity14_1.class)
	public static void addDoubles14_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_2", type = Inplaces.Arity14_2.class)
	public static void addDoubles14_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_3", type = Inplaces.Arity14_3.class)
	public static void addDoubles14_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_4", type = Inplaces.Arity14_4.class)
	public static void addDoubles14_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_5", type = Inplaces.Arity14_5.class)
	public static void addDoubles14_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_6", type = Inplaces.Arity14_6.class)
	public static void addDoubles14_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_7", type = Inplaces.Arity14_7.class)
	public static void addDoubles14_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_8", type = Inplaces.Arity14_8.class)
	public static void addDoubles14_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_9", type = Inplaces.Arity14_9.class)
	public static void addDoubles14_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_10", type = Inplaces.Arity14_10.class)
	public static void addDoubles14_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_11", type = Inplaces.Arity14_11.class)
	public static void addDoubles14_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_12", type = Inplaces.Arity14_12.class)
	public static void addDoubles14_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_13", type = Inplaces.Arity14_13.class)
	public static void addDoubles14_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles14_14", type = Inplaces.Arity14_14.class)
	public static void addDoubles14_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_1", type = Inplaces.Arity15_1.class)
	public static void addDoubles15_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_2", type = Inplaces.Arity15_2.class)
	public static void addDoubles15_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_3", type = Inplaces.Arity15_3.class)
	public static void addDoubles15_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_4", type = Inplaces.Arity15_4.class)
	public static void addDoubles15_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_5", type = Inplaces.Arity15_5.class)
	public static void addDoubles15_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_6", type = Inplaces.Arity15_6.class)
	public static void addDoubles15_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_7", type = Inplaces.Arity15_7.class)
	public static void addDoubles15_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_8", type = Inplaces.Arity15_8.class)
	public static void addDoubles15_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_9", type = Inplaces.Arity15_9.class)
	public static void addDoubles15_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_10", type = Inplaces.Arity15_10.class)
	public static void addDoubles15_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_11", type = Inplaces.Arity15_11.class)
	public static void addDoubles15_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_12", type = Inplaces.Arity15_12.class)
	public static void addDoubles15_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_13", type = Inplaces.Arity15_13.class)
	public static void addDoubles15_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_14", type = Inplaces.Arity15_14.class)
	public static void addDoubles15_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io, double[] in15) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in15[i];
		}
	}

	@OpMethod(names = "test.addDoubles15_15", type = Inplaces.Arity15_15.class)
	public static void addDoubles15_15(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_1", type = Inplaces.Arity16_1.class)
	public static void addDoubles16_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_2", type = Inplaces.Arity16_2.class)
	public static void addDoubles16_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_3", type = Inplaces.Arity16_3.class)
	public static void addDoubles16_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_4", type = Inplaces.Arity16_4.class)
	public static void addDoubles16_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_5", type = Inplaces.Arity16_5.class)
	public static void addDoubles16_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_6", type = Inplaces.Arity16_6.class)
	public static void addDoubles16_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_7", type = Inplaces.Arity16_7.class)
	public static void addDoubles16_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_8", type = Inplaces.Arity16_8.class)
	public static void addDoubles16_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_9", type = Inplaces.Arity16_9.class)
	public static void addDoubles16_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_10", type = Inplaces.Arity16_10.class)
	public static void addDoubles16_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_11", type = Inplaces.Arity16_11.class)
	public static void addDoubles16_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_12", type = Inplaces.Arity16_12.class)
	public static void addDoubles16_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_13", type = Inplaces.Arity16_13.class)
	public static void addDoubles16_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in14[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_14", type = Inplaces.Arity16_14.class)
	public static void addDoubles16_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io, double[] in15, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in15[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_15", type = Inplaces.Arity16_15.class)
	public static void addDoubles16_15(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] io, double[] in16) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in16[i];
		}
	}

	@OpMethod(names = "test.addDoubles16_16", type = Inplaces.Arity16_16.class)
	public static void addDoubles16_16(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] io) {
		for (int i = 0; i < io.length; i++) {
			io[i] += in1[i];
			io[i] += in2[i];
			io[i] += in3[i];
			io[i] += in4[i];
			io[i] += in5[i];
			io[i] += in6[i];
			io[i] += in7[i];
			io[i] += in8[i];
			io[i] += in9[i];
			io[i] += in10[i];
			io[i] += in11[i];
			io[i] += in12[i];
			io[i] += in13[i];
			io[i] += in14[i];
			io[i] += in15[i];
		}
	}
}
