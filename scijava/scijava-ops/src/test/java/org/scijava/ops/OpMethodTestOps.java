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

import org.scijava.function.Computers;
import org.scijava.function.Functions;
import org.scijava.function.Inplaces;
import org.scijava.function.Producer;
import org.scijava.ops.OpCollection;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

@Plugin(type = OpCollection.class)
public class OpMethodTestOps {

	// -- Functions -- //
	@OpMethod(names = "test.multiplyNumericStrings", type = Producer.class)
	public static Integer multiplyNumericStringsProducer() {
		return Integer.valueOf(1);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Function.class)
	public static Integer multiplyNumericStringsFunction1(String in)
	{
		return multiplyNumericStringsFunction1(in, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = BiFunction.class)
	public static Integer multiplyNumericStringsFunction2(String in1, String in2)
	{
		return multiplyNumericStringsFunction2(in1, in2, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity3.class)
	public static Integer multiplyNumericStringsFunction3(String in1, String in2, String in3)
	{
		return multiplyNumericStringsFunction3(in1, in2, in3, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity4.class)
	public static Integer multiplyNumericStringsFunction4(String in1, String in2, String in3, String in4)
	{
		return multiplyNumericStringsFunction4(in1, in2, in3, in4, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity5.class)
	public static Integer multiplyNumericStringsFunction5(String in1, String in2, String in3, String in4, String in5)
	{
		return multiplyNumericStringsFunction5(in1, in2, in3, in4, in5, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity6.class)
	public static Integer multiplyNumericStringsFunction6(String in1, String in2, String in3, String in4, String in5, String in6)
	{
		return multiplyNumericStringsFunction6(in1, in2, in3, in4, in5, in6, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity7.class)
	public static Integer multiplyNumericStringsFunction7(String in1, String in2, String in3, String in4, String in5, String in6, String in7)
	{
		return multiplyNumericStringsFunction7(in1, in2, in3, in4, in5, in6, in7, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity8.class)
	public static Integer multiplyNumericStringsFunction8(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8)
	{
		return multiplyNumericStringsFunction8(in1, in2, in3, in4, in5, in6, in7, in8, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity9.class)
	public static Integer multiplyNumericStringsFunction9(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9)
	{
		return multiplyNumericStringsFunction9(in1, in2, in3, in4, in5, in6, in7, in8, in9, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity10.class)
	public static Integer multiplyNumericStringsFunction10(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10)
	{
		return multiplyNumericStringsFunction10(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity11.class)
	public static Integer multiplyNumericStringsFunction11(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11)
	{
		return multiplyNumericStringsFunction11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity12.class)
	public static Integer multiplyNumericStringsFunction12(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12)
	{
		return multiplyNumericStringsFunction12(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity13.class)
	public static Integer multiplyNumericStringsFunction13(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13)
	{
		return multiplyNumericStringsFunction13(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity14.class)
	public static Integer multiplyNumericStringsFunction14(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14)
	{
		return multiplyNumericStringsFunction14(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity15.class)
	public static Integer multiplyNumericStringsFunction15(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15)
	{
		return multiplyNumericStringsFunction15(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, parseInt);
	}

	@OpMethod(names = "test.multiplyNumericStrings", type = Functions.Arity16.class)
	public static Integer multiplyNumericStringsFunction16(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, String in16)
	{
		return multiplyNumericStringsFunction16(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, parseInt);
	}

	// -- Computers -- //
	
	@OpMethod(names = "test.doubleList", type = Computers.Arity0.class)
	public static void doublesToList0(List<Double> output) {
		output.clear();
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity1.class)
	public static void doublesToList1(String in, List<Double> output) {
		doublesToListWithOp1(in, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity2.class)
	public static void doublesToList2(String in1, String in2, List<Double> output) {
		doublesToListWithOp2(in1, in2, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity3.class)
	public static void doublesToList3(String in1, String in2, String in3, List<Double> output) {
		doublesToListWithOp3(in1, in2, in3, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity4.class)
	public static void doublesToList4(String in1, String in2, String in3, String in4, List<Double> output) {
		doublesToListWithOp4(in1, in2, in3, in4, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity5.class)
	public static void doublesToList5(String in1, String in2, String in3, String in4, String in5, List<Double> output) {
		doublesToListWithOp5(in1, in2, in3, in4, in5, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity6.class)
	public static void doublesToList6(String in1, String in2, String in3, String in4, String in5, String in6, List<Double> output) {
		doublesToListWithOp6(in1, in2, in3, in4, in5, in6, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity7.class)
	public static void doublesToList7(String in1, String in2, String in3, String in4, String in5, String in6, String in7, List<Double> output) {
		doublesToListWithOp7(in1, in2, in3, in4, in5, in6, in7, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity8.class)
	public static void doublesToList8(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, List<Double> output) {
		doublesToListWithOp8(in1, in2, in3, in4, in5, in6, in7, in8, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity9.class)
	public static void doublesToList9(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, List<Double> output) {
		doublesToListWithOp9(in1, in2, in3, in4, in5, in6, in7, in8, in9, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity10.class)
	public static void doublesToList10(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, List<Double> output) {
		doublesToListWithOp10(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity11.class)
	public static void doublesToList11(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, List<Double> output) {
		doublesToListWithOp11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity12.class)
	public static void doublesToList12(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, List<Double> output) {
		doublesToListWithOp12(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity13.class)
	public static void doublesToList13(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, List<Double> output) {
		doublesToListWithOp13(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity14.class)
	public static void doublesToList14(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, List<Double> output) {
		doublesToListWithOp14(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity15.class)
	public static void doublesToList15(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, List<Double> output) {
		doublesToListWithOp15(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, output, appendDouble);
	}

	@OpMethod(names = "test.doubleList", type = Computers.Arity16.class)
	public static void doublesToList16(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, String in16, List<Double> output) {
		doublesToListWithOp16(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, output, appendDouble);
	}

	// -- Inplaces -- //


	@OpMethod(names = "test.addDoubles1", type = Inplaces.Arity1.class)
	public static void addDoubles1(double[] io) {
		dependentAddDoubles1(io, addArrays);
	}

	@OpMethod(names = "test.addDoubles2_1", type = Inplaces.Arity2_1.class)
	public static void addDoubles2_1(double[] io, double[] in2) {
		dependentAddDoubles2_1(io, in2, addArrays);
	}

	@OpMethod(names = "test.addDoubles2_2", type = Inplaces.Arity2_2.class)
	public static void addDoubles2_2(double[] in1, double[] io) {
		dependentAddDoubles2_2(in1, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles3_1", type = Inplaces.Arity3_1.class)
	public static void addDoubles3_1(double[] io, double[] in2, double[] in3) {
		dependentAddDoubles3_1(io, in2, in3, addArrays);
	}

	@OpMethod(names = "test.addDoubles3_2", type = Inplaces.Arity3_2.class)
	public static void addDoubles3_2(double[] in1, double[] io, double[] in3) {
		dependentAddDoubles3_2(in1, io, in3, addArrays);
	}

	@OpMethod(names = "test.addDoubles3_3", type = Inplaces.Arity3_3.class)
	public static void addDoubles3_3(double[] in1, double[] in2, double[] io) {
		dependentAddDoubles3_3(in1, in2, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles4_1", type = Inplaces.Arity4_1.class)
	public static void addDoubles4_1(double[] io, double[] in2, double[] in3, double[] in4) {
		dependentAddDoubles4_1(io, in2, in3, in4, addArrays);
	}

	@OpMethod(names = "test.addDoubles4_2", type = Inplaces.Arity4_2.class)
	public static void addDoubles4_2(double[] in1, double[] io, double[] in3, double[] in4) {
		dependentAddDoubles4_2(in1, io, in3, in4, addArrays);
	}

	@OpMethod(names = "test.addDoubles4_3", type = Inplaces.Arity4_3.class)
	public static void addDoubles4_3(double[] in1, double[] in2, double[] io, double[] in4) {
		dependentAddDoubles4_3(in1, in2, io, in4, addArrays);
	}

	@OpMethod(names = "test.addDoubles4_4", type = Inplaces.Arity4_4.class)
	public static void addDoubles4_4(double[] in1, double[] in2, double[] in3, double[] io) {
		dependentAddDoubles4_4(in1, in2, in3, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles5_1", type = Inplaces.Arity5_1.class)
	public static void addDoubles5_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5) {
		dependentAddDoubles5_1(io, in2, in3, in4, in5, addArrays);
	}

	@OpMethod(names = "test.addDoubles5_2", type = Inplaces.Arity5_2.class)
	public static void addDoubles5_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5) {
		dependentAddDoubles5_2(in1, io, in3, in4, in5, addArrays);
	}

	@OpMethod(names = "test.addDoubles5_3", type = Inplaces.Arity5_3.class)
	public static void addDoubles5_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5) {
		dependentAddDoubles5_3(in1, in2, io, in4, in5, addArrays);
	}

	@OpMethod(names = "test.addDoubles5_4", type = Inplaces.Arity5_4.class)
	public static void addDoubles5_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5) {
		dependentAddDoubles5_4(in1, in2, in3, io, in5, addArrays);
	}

	@OpMethod(names = "test.addDoubles5_5", type = Inplaces.Arity5_5.class)
	public static void addDoubles5_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io) {
		dependentAddDoubles5_5(in1, in2, in3, in4, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles6_1", type = Inplaces.Arity6_1.class)
	public static void addDoubles6_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6) {
		dependentAddDoubles6_1(io, in2, in3, in4, in5, in6, addArrays);
	}

	@OpMethod(names = "test.addDoubles6_2", type = Inplaces.Arity6_2.class)
	public static void addDoubles6_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6) {
		dependentAddDoubles6_2(in1, io, in3, in4, in5, in6, addArrays);
	}

	@OpMethod(names = "test.addDoubles6_3", type = Inplaces.Arity6_3.class)
	public static void addDoubles6_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6) {
		dependentAddDoubles6_3(in1, in2, io, in4, in5, in6, addArrays);
	}

	@OpMethod(names = "test.addDoubles6_4", type = Inplaces.Arity6_4.class)
	public static void addDoubles6_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6) {
		dependentAddDoubles6_4(in1, in2, in3, io, in5, in6, addArrays);
	}

	@OpMethod(names = "test.addDoubles6_5", type = Inplaces.Arity6_5.class)
	public static void addDoubles6_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6) {
		dependentAddDoubles6_5(in1, in2, in3, in4, io, in6, addArrays);
	}

	@OpMethod(names = "test.addDoubles6_6", type = Inplaces.Arity6_6.class)
	public static void addDoubles6_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io) {
		dependentAddDoubles6_6(in1, in2, in3, in4, in5, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles7_1", type = Inplaces.Arity7_1.class)
	public static void addDoubles7_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7) {
		dependentAddDoubles7_1(io, in2, in3, in4, in5, in6, in7, addArrays);
	}

	@OpMethod(names = "test.addDoubles7_2", type = Inplaces.Arity7_2.class)
	public static void addDoubles7_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7) {
		dependentAddDoubles7_2(in1, io, in3, in4, in5, in6, in7, addArrays);
	}

	@OpMethod(names = "test.addDoubles7_3", type = Inplaces.Arity7_3.class)
	public static void addDoubles7_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7) {
		dependentAddDoubles7_3(in1, in2, io, in4, in5, in6, in7, addArrays);
	}

	@OpMethod(names = "test.addDoubles7_4", type = Inplaces.Arity7_4.class)
	public static void addDoubles7_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7) {
		dependentAddDoubles7_4(in1, in2, in3, io, in5, in6, in7, addArrays);
	}

	@OpMethod(names = "test.addDoubles7_5", type = Inplaces.Arity7_5.class)
	public static void addDoubles7_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7) {
		dependentAddDoubles7_5(in1, in2, in3, in4, io, in6, in7, addArrays);
	}

	@OpMethod(names = "test.addDoubles7_6", type = Inplaces.Arity7_6.class)
	public static void addDoubles7_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7) {
		dependentAddDoubles7_6(in1, in2, in3, in4, in5, io, in7, addArrays);
	}

	@OpMethod(names = "test.addDoubles7_7", type = Inplaces.Arity7_7.class)
	public static void addDoubles7_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io) {
		dependentAddDoubles7_7(in1, in2, in3, in4, in5, in6, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_1", type = Inplaces.Arity8_1.class)
	public static void addDoubles8_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8) {
		dependentAddDoubles8_1(io, in2, in3, in4, in5, in6, in7, in8, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_2", type = Inplaces.Arity8_2.class)
	public static void addDoubles8_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8) {
		dependentAddDoubles8_2(in1, io, in3, in4, in5, in6, in7, in8, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_3", type = Inplaces.Arity8_3.class)
	public static void addDoubles8_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8) {
		dependentAddDoubles8_3(in1, in2, io, in4, in5, in6, in7, in8, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_4", type = Inplaces.Arity8_4.class)
	public static void addDoubles8_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8) {
		dependentAddDoubles8_4(in1, in2, in3, io, in5, in6, in7, in8, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_5", type = Inplaces.Arity8_5.class)
	public static void addDoubles8_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8) {
		dependentAddDoubles8_5(in1, in2, in3, in4, io, in6, in7, in8, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_6", type = Inplaces.Arity8_6.class)
	public static void addDoubles8_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8) {
		dependentAddDoubles8_6(in1, in2, in3, in4, in5, io, in7, in8, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_7", type = Inplaces.Arity8_7.class)
	public static void addDoubles8_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8) {
		dependentAddDoubles8_7(in1, in2, in3, in4, in5, in6, io, in8, addArrays);
	}

	@OpMethod(names = "test.addDoubles8_8", type = Inplaces.Arity8_8.class)
	public static void addDoubles8_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io) {
		dependentAddDoubles8_8(in1, in2, in3, in4, in5, in6, in7, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_1", type = Inplaces.Arity9_1.class)
	public static void addDoubles9_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		dependentAddDoubles9_1(io, in2, in3, in4, in5, in6, in7, in8, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_2", type = Inplaces.Arity9_2.class)
	public static void addDoubles9_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		dependentAddDoubles9_2(in1, io, in3, in4, in5, in6, in7, in8, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_3", type = Inplaces.Arity9_3.class)
	public static void addDoubles9_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		dependentAddDoubles9_3(in1, in2, io, in4, in5, in6, in7, in8, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_4", type = Inplaces.Arity9_4.class)
	public static void addDoubles9_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9) {
		dependentAddDoubles9_4(in1, in2, in3, io, in5, in6, in7, in8, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_5", type = Inplaces.Arity9_5.class)
	public static void addDoubles9_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9) {
		dependentAddDoubles9_5(in1, in2, in3, in4, io, in6, in7, in8, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_6", type = Inplaces.Arity9_6.class)
	public static void addDoubles9_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9) {
		dependentAddDoubles9_6(in1, in2, in3, in4, in5, io, in7, in8, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_7", type = Inplaces.Arity9_7.class)
	public static void addDoubles9_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9) {
		dependentAddDoubles9_7(in1, in2, in3, in4, in5, in6, io, in8, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_8", type = Inplaces.Arity9_8.class)
	public static void addDoubles9_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9) {
		dependentAddDoubles9_8(in1, in2, in3, in4, in5, in6, in7, io, in9, addArrays);
	}

	@OpMethod(names = "test.addDoubles9_9", type = Inplaces.Arity9_9.class)
	public static void addDoubles9_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io) {
		dependentAddDoubles9_9(in1, in2, in3, in4, in5, in6, in7, in8, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_1", type = Inplaces.Arity10_1.class)
	public static void addDoubles10_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		dependentAddDoubles10_1(io, in2, in3, in4, in5, in6, in7, in8, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_2", type = Inplaces.Arity10_2.class)
	public static void addDoubles10_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		dependentAddDoubles10_2(in1, io, in3, in4, in5, in6, in7, in8, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_3", type = Inplaces.Arity10_3.class)
	public static void addDoubles10_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		dependentAddDoubles10_3(in1, in2, io, in4, in5, in6, in7, in8, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_4", type = Inplaces.Arity10_4.class)
	public static void addDoubles10_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		dependentAddDoubles10_4(in1, in2, in3, io, in5, in6, in7, in8, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_5", type = Inplaces.Arity10_5.class)
	public static void addDoubles10_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10) {
		dependentAddDoubles10_5(in1, in2, in3, in4, io, in6, in7, in8, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_6", type = Inplaces.Arity10_6.class)
	public static void addDoubles10_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10) {
		dependentAddDoubles10_6(in1, in2, in3, in4, in5, io, in7, in8, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_7", type = Inplaces.Arity10_7.class)
	public static void addDoubles10_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10) {
		dependentAddDoubles10_7(in1, in2, in3, in4, in5, in6, io, in8, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_8", type = Inplaces.Arity10_8.class)
	public static void addDoubles10_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10) {
		dependentAddDoubles10_8(in1, in2, in3, in4, in5, in6, in7, io, in9, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_9", type = Inplaces.Arity10_9.class)
	public static void addDoubles10_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10) {
		dependentAddDoubles10_9(in1, in2, in3, in4, in5, in6, in7, in8, io, in10, addArrays);
	}

	@OpMethod(names = "test.addDoubles10_10", type = Inplaces.Arity10_10.class)
	public static void addDoubles10_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io) {
		dependentAddDoubles10_10(in1, in2, in3, in4, in5, in6, in7, in8, in9, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_1", type = Inplaces.Arity11_1.class)
	public static void addDoubles11_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_1(io, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_2", type = Inplaces.Arity11_2.class)
	public static void addDoubles11_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_2(in1, io, in3, in4, in5, in6, in7, in8, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_3", type = Inplaces.Arity11_3.class)
	public static void addDoubles11_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_3(in1, in2, io, in4, in5, in6, in7, in8, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_4", type = Inplaces.Arity11_4.class)
	public static void addDoubles11_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_4(in1, in2, in3, io, in5, in6, in7, in8, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_5", type = Inplaces.Arity11_5.class)
	public static void addDoubles11_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_5(in1, in2, in3, in4, io, in6, in7, in8, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_6", type = Inplaces.Arity11_6.class)
	public static void addDoubles11_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_6(in1, in2, in3, in4, in5, io, in7, in8, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_7", type = Inplaces.Arity11_7.class)
	public static void addDoubles11_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_7(in1, in2, in3, in4, in5, in6, io, in8, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_8", type = Inplaces.Arity11_8.class)
	public static void addDoubles11_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11) {
		dependentAddDoubles11_8(in1, in2, in3, in4, in5, in6, in7, io, in9, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_9", type = Inplaces.Arity11_9.class)
	public static void addDoubles11_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11) {
		dependentAddDoubles11_9(in1, in2, in3, in4, in5, in6, in7, in8, io, in10, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_10", type = Inplaces.Arity11_10.class)
	public static void addDoubles11_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11) {
		dependentAddDoubles11_10(in1, in2, in3, in4, in5, in6, in7, in8, in9, io, in11, addArrays);
	}

	@OpMethod(names = "test.addDoubles11_11", type = Inplaces.Arity11_11.class)
	public static void addDoubles11_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io) {
		dependentAddDoubles11_11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_1", type = Inplaces.Arity12_1.class)
	public static void addDoubles12_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_1(io, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_2", type = Inplaces.Arity12_2.class)
	public static void addDoubles12_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_2(in1, io, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_3", type = Inplaces.Arity12_3.class)
	public static void addDoubles12_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_3(in1, in2, io, in4, in5, in6, in7, in8, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_4", type = Inplaces.Arity12_4.class)
	public static void addDoubles12_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_4(in1, in2, in3, io, in5, in6, in7, in8, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_5", type = Inplaces.Arity12_5.class)
	public static void addDoubles12_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_5(in1, in2, in3, in4, io, in6, in7, in8, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_6", type = Inplaces.Arity12_6.class)
	public static void addDoubles12_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_6(in1, in2, in3, in4, in5, io, in7, in8, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_7", type = Inplaces.Arity12_7.class)
	public static void addDoubles12_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_7(in1, in2, in3, in4, in5, in6, io, in8, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_8", type = Inplaces.Arity12_8.class)
	public static void addDoubles12_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_8(in1, in2, in3, in4, in5, in6, in7, io, in9, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_9", type = Inplaces.Arity12_9.class)
	public static void addDoubles12_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12) {
		dependentAddDoubles12_9(in1, in2, in3, in4, in5, in6, in7, in8, io, in10, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_10", type = Inplaces.Arity12_10.class)
	public static void addDoubles12_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12) {
		dependentAddDoubles12_10(in1, in2, in3, in4, in5, in6, in7, in8, in9, io, in11, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_11", type = Inplaces.Arity12_11.class)
	public static void addDoubles12_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12) {
		dependentAddDoubles12_11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, io, in12, addArrays);
	}

	@OpMethod(names = "test.addDoubles12_12", type = Inplaces.Arity12_12.class)
	public static void addDoubles12_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io) {
		dependentAddDoubles12_12(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_1", type = Inplaces.Arity13_1.class)
	public static void addDoubles13_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_1(io, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_2", type = Inplaces.Arity13_2.class)
	public static void addDoubles13_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_2(in1, io, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_3", type = Inplaces.Arity13_3.class)
	public static void addDoubles13_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_3(in1, in2, io, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_4", type = Inplaces.Arity13_4.class)
	public static void addDoubles13_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_4(in1, in2, in3, io, in5, in6, in7, in8, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_5", type = Inplaces.Arity13_5.class)
	public static void addDoubles13_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_5(in1, in2, in3, in4, io, in6, in7, in8, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_6", type = Inplaces.Arity13_6.class)
	public static void addDoubles13_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_6(in1, in2, in3, in4, in5, io, in7, in8, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_7", type = Inplaces.Arity13_7.class)
	public static void addDoubles13_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_7(in1, in2, in3, in4, in5, in6, io, in8, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_8", type = Inplaces.Arity13_8.class)
	public static void addDoubles13_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_8(in1, in2, in3, in4, in5, in6, in7, io, in9, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_9", type = Inplaces.Arity13_9.class)
	public static void addDoubles13_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_9(in1, in2, in3, in4, in5, in6, in7, in8, io, in10, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_10", type = Inplaces.Arity13_10.class)
	public static void addDoubles13_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13) {
		dependentAddDoubles13_10(in1, in2, in3, in4, in5, in6, in7, in8, in9, io, in11, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_11", type = Inplaces.Arity13_11.class)
	public static void addDoubles13_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13) {
		dependentAddDoubles13_11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, io, in12, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_12", type = Inplaces.Arity13_12.class)
	public static void addDoubles13_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13) {
		dependentAddDoubles13_12(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, io, in13, addArrays);
	}

	@OpMethod(names = "test.addDoubles13_13", type = Inplaces.Arity13_13.class)
	public static void addDoubles13_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io) {
		dependentAddDoubles13_13(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_1", type = Inplaces.Arity14_1.class)
	public static void addDoubles14_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_1(io, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_2", type = Inplaces.Arity14_2.class)
	public static void addDoubles14_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_2(in1, io, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_3", type = Inplaces.Arity14_3.class)
	public static void addDoubles14_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_3(in1, in2, io, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_4", type = Inplaces.Arity14_4.class)
	public static void addDoubles14_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_4(in1, in2, in3, io, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_5", type = Inplaces.Arity14_5.class)
	public static void addDoubles14_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_5(in1, in2, in3, in4, io, in6, in7, in8, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_6", type = Inplaces.Arity14_6.class)
	public static void addDoubles14_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_6(in1, in2, in3, in4, in5, io, in7, in8, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_7", type = Inplaces.Arity14_7.class)
	public static void addDoubles14_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_7(in1, in2, in3, in4, in5, in6, io, in8, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_8", type = Inplaces.Arity14_8.class)
	public static void addDoubles14_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_8(in1, in2, in3, in4, in5, in6, in7, io, in9, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_9", type = Inplaces.Arity14_9.class)
	public static void addDoubles14_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_9(in1, in2, in3, in4, in5, in6, in7, in8, io, in10, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_10", type = Inplaces.Arity14_10.class)
	public static void addDoubles14_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_10(in1, in2, in3, in4, in5, in6, in7, in8, in9, io, in11, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_11", type = Inplaces.Arity14_11.class)
	public static void addDoubles14_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14) {
		dependentAddDoubles14_11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, io, in12, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_12", type = Inplaces.Arity14_12.class)
	public static void addDoubles14_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14) {
		dependentAddDoubles14_12(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, io, in13, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_13", type = Inplaces.Arity14_13.class)
	public static void addDoubles14_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14) {
		dependentAddDoubles14_13(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, io, in14, addArrays);
	}

	@OpMethod(names = "test.addDoubles14_14", type = Inplaces.Arity14_14.class)
	public static void addDoubles14_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io) {
		dependentAddDoubles14_14(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_1", type = Inplaces.Arity15_1.class)
	public static void addDoubles15_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_1(io, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_2", type = Inplaces.Arity15_2.class)
	public static void addDoubles15_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_2(in1, io, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_3", type = Inplaces.Arity15_3.class)
	public static void addDoubles15_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_3(in1, in2, io, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_4", type = Inplaces.Arity15_4.class)
	public static void addDoubles15_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_4(in1, in2, in3, io, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_5", type = Inplaces.Arity15_5.class)
	public static void addDoubles15_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_5(in1, in2, in3, in4, io, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_6", type = Inplaces.Arity15_6.class)
	public static void addDoubles15_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_6(in1, in2, in3, in4, in5, io, in7, in8, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_7", type = Inplaces.Arity15_7.class)
	public static void addDoubles15_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_7(in1, in2, in3, in4, in5, in6, io, in8, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_8", type = Inplaces.Arity15_8.class)
	public static void addDoubles15_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_8(in1, in2, in3, in4, in5, in6, in7, io, in9, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_9", type = Inplaces.Arity15_9.class)
	public static void addDoubles15_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_9(in1, in2, in3, in4, in5, in6, in7, in8, io, in10, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_10", type = Inplaces.Arity15_10.class)
	public static void addDoubles15_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_10(in1, in2, in3, in4, in5, in6, in7, in8, in9, io, in11, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_11", type = Inplaces.Arity15_11.class)
	public static void addDoubles15_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, io, in12, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_12", type = Inplaces.Arity15_12.class)
	public static void addDoubles15_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14, double[] in15) {
		dependentAddDoubles15_12(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, io, in13, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_13", type = Inplaces.Arity15_13.class)
	public static void addDoubles15_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14, double[] in15) {
		dependentAddDoubles15_13(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, io, in14, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_14", type = Inplaces.Arity15_14.class)
	public static void addDoubles15_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io, double[] in15) {
		dependentAddDoubles15_14(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, io, in15, addArrays);
	}

	@OpMethod(names = "test.addDoubles15_15", type = Inplaces.Arity15_15.class)
	public static void addDoubles15_15(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] io) {
		dependentAddDoubles15_15(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, io, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_1", type = Inplaces.Arity16_1.class)
	public static void addDoubles16_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_1(io, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_2", type = Inplaces.Arity16_2.class)
	public static void addDoubles16_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_2(in1, io, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_3", type = Inplaces.Arity16_3.class)
	public static void addDoubles16_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_3(in1, in2, io, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_4", type = Inplaces.Arity16_4.class)
	public static void addDoubles16_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_4(in1, in2, in3, io, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_5", type = Inplaces.Arity16_5.class)
	public static void addDoubles16_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_5(in1, in2, in3, in4, io, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_6", type = Inplaces.Arity16_6.class)
	public static void addDoubles16_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_6(in1, in2, in3, in4, in5, io, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_7", type = Inplaces.Arity16_7.class)
	public static void addDoubles16_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_7(in1, in2, in3, in4, in5, in6, io, in8, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_8", type = Inplaces.Arity16_8.class)
	public static void addDoubles16_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_8(in1, in2, in3, in4, in5, in6, in7, io, in9, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_9", type = Inplaces.Arity16_9.class)
	public static void addDoubles16_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_9(in1, in2, in3, in4, in5, in6, in7, in8, io, in10, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_10", type = Inplaces.Arity16_10.class)
	public static void addDoubles16_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_10(in1, in2, in3, in4, in5, in6, in7, in8, in9, io, in11, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_11", type = Inplaces.Arity16_11.class)
	public static void addDoubles16_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_11(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, io, in12, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_12", type = Inplaces.Arity16_12.class)
	public static void addDoubles16_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_12(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, io, in13, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_13", type = Inplaces.Arity16_13.class)
	public static void addDoubles16_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14, double[] in15, double[] in16) {
		dependentAddDoubles16_13(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, io, in14, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_14", type = Inplaces.Arity16_14.class)
	public static void addDoubles16_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io, double[] in15, double[] in16) {
		dependentAddDoubles16_14(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, io, in15, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_15", type = Inplaces.Arity16_15.class)
	public static void addDoubles16_15(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] io, double[] in16) {
		dependentAddDoubles16_15(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, io, in16, addArrays);
	}

	@OpMethod(names = "test.addDoubles16_16", type = Inplaces.Arity16_16.class)
	public static void addDoubles16_16(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] io) {
		dependentAddDoubles16_16(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, io, addArrays);
	}

	// -- Helper Op -- //

	@OpField(names = "test.parseInt")
	public static final Function<String, Integer> parseInt = in -> Integer
		.parseInt(in);

	@OpField(names = "test.appendDouble")
	public static final Inplaces.Arity2_1<List<Double>, String> appendDouble = (
		list, element) -> list.add(Double.parseDouble(element));

	@OpField(names = "test.addArrays")
	public static final Inplaces.Arity2_1<double[], double[]> addArrays = (io,
		in2) -> {
		for (int i = 0; i < io.length; i++)
			io[i] += in2[i];
	};

	// -- Dependent Functions -- //

	@OpMethod(names = "test.dependentMultiplyStrings", type = Function.class)
	public static Integer multiplyNumericStringsFunction1(String in,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = BiFunction.class)
	public static Integer multiplyNumericStringsFunction2(String in1, String in2,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity3.class)
	public static Integer multiplyNumericStringsFunction3(String in1, String in2, String in3,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity4.class)
	public static Integer multiplyNumericStringsFunction4(String in1, String in2, String in3, String in4,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity5.class)
	public static Integer multiplyNumericStringsFunction5(String in1, String in2, String in3, String in4, String in5,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity6.class)
	public static Integer multiplyNumericStringsFunction6(String in1, String in2, String in3, String in4, String in5, String in6,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity7.class)
	public static Integer multiplyNumericStringsFunction7(String in1, String in2, String in3, String in4, String in5, String in6, String in7,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity8.class)
	public static Integer multiplyNumericStringsFunction8(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity9.class)
	public static Integer multiplyNumericStringsFunction9(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity10.class)
	public static Integer multiplyNumericStringsFunction10(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);
		out *= op.apply(in10);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity11.class)
	public static Integer multiplyNumericStringsFunction11(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);
		out *= op.apply(in10);
		out *= op.apply(in11);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity12.class)
	public static Integer multiplyNumericStringsFunction12(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);
		out *= op.apply(in10);
		out *= op.apply(in11);
		out *= op.apply(in12);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity13.class)
	public static Integer multiplyNumericStringsFunction13(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);
		out *= op.apply(in10);
		out *= op.apply(in11);
		out *= op.apply(in12);
		out *= op.apply(in13);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity14.class)
	public static Integer multiplyNumericStringsFunction14(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);
		out *= op.apply(in10);
		out *= op.apply(in11);
		out *= op.apply(in12);
		out *= op.apply(in13);
		out *= op.apply(in14);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity15.class)
	public static Integer multiplyNumericStringsFunction15(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);
		out *= op.apply(in10);
		out *= op.apply(in11);
		out *= op.apply(in12);
		out *= op.apply(in13);
		out *= op.apply(in14);
		out *= op.apply(in15);

		return out;
	}

	@OpMethod(names = "test.dependentMultiplyStrings", type = Functions.Arity16.class)
	public static Integer multiplyNumericStringsFunction16(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, String in16,
		@OpDependency(name = "test.parseInt") Function<String, Integer> op)
	{
		Integer out = Integer.valueOf(1);

		out *= op.apply(in1);
		out *= op.apply(in2);
		out *= op.apply(in3);
		out *= op.apply(in4);
		out *= op.apply(in5);
		out *= op.apply(in6);
		out *= op.apply(in7);
		out *= op.apply(in8);
		out *= op.apply(in9);
		out *= op.apply(in10);
		out *= op.apply(in11);
		out *= op.apply(in12);
		out *= op.apply(in13);
		out *= op.apply(in14);
		out *= op.apply(in15);
		out *= op.apply(in16);

		return out;
	}

	// -- Dependent Computers -- //

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity1.class)
	public static void doublesToListWithOp1(String in,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity2.class)
	public static void doublesToListWithOp2(String in1, String in2,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity3.class)
	public static void doublesToListWithOp3(String in1, String in2, String in3,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity4.class)
	public static void doublesToListWithOp4(String in1, String in2, String in3, String in4,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity5.class)
	public static void doublesToListWithOp5(String in1, String in2, String in3, String in4, String in5,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity6.class)
	public static void doublesToListWithOp6(String in1, String in2, String in3, String in4, String in5, String in6,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity7.class)
	public static void doublesToListWithOp7(String in1, String in2, String in3, String in4, String in5, String in6, String in7,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity8.class)
	public static void doublesToListWithOp8(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity9.class)
	public static void doublesToListWithOp9(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity10.class)
	public static void doublesToListWithOp10(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
		op.mutate(output, in10);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity11.class)
	public static void doublesToListWithOp11(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
		op.mutate(output, in10);
		op.mutate(output, in11);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity12.class)
	public static void doublesToListWithOp12(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
		op.mutate(output, in10);
		op.mutate(output, in11);
		op.mutate(output, in12);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity13.class)
	public static void doublesToListWithOp13(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
		op.mutate(output, in10);
		op.mutate(output, in11);
		op.mutate(output, in12);
		op.mutate(output, in13);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity14.class)
	public static void doublesToListWithOp14(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
		op.mutate(output, in10);
		op.mutate(output, in11);
		op.mutate(output, in12);
		op.mutate(output, in13);
		op.mutate(output, in14);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity15.class)
	public static void doublesToListWithOp15(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
		op.mutate(output, in10);
		op.mutate(output, in11);
		op.mutate(output, in12);
		op.mutate(output, in13);
		op.mutate(output, in14);
		op.mutate(output, in15);
	}

	@OpMethod(names = "test.dependentDoubleList", type = Computers.Arity16.class)
	public static void doublesToListWithOp16(String in1, String in2, String in3, String in4, String in5, String in6, String in7, String in8, String in9, String in10, String in11, String in12, String in13, String in14, String in15, String in16,
		List<Double> output,
		@OpDependency(name = "test.appendDouble") Inplaces.Arity2_1<List<Double>, String> op)
	{
		output.clear();
		op.mutate(output, in1);
		op.mutate(output, in2);
		op.mutate(output, in3);
		op.mutate(output, in4);
		op.mutate(output, in5);
		op.mutate(output, in6);
		op.mutate(output, in7);
		op.mutate(output, in8);
		op.mutate(output, in9);
		op.mutate(output, in10);
		op.mutate(output, in11);
		op.mutate(output, in12);
		op.mutate(output, in13);
		op.mutate(output, in14);
		op.mutate(output, in15);
		op.mutate(output, in16);
	}

	// -- Dependent Inplaces -- //


	@OpMethod(names = "test.dependentAddDoubles1", type = Inplaces.Arity1.class)
	public static void dependentAddDoubles1(double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
	}

	@OpMethod(names = "test.dependentAddDoubles2_1", type = Inplaces.Arity2_1.class)
	public static void dependentAddDoubles2_1(double[] io, double[] in2, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
	}

	@OpMethod(names = "test.dependentAddDoubles2_2", type = Inplaces.Arity2_2.class)
	public static void dependentAddDoubles2_2(double[] in1, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
	}

	@OpMethod(names = "test.dependentAddDoubles3_1", type = Inplaces.Arity3_1.class)
	public static void dependentAddDoubles3_1(double[] io, double[] in2, double[] in3, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
	}

	@OpMethod(names = "test.dependentAddDoubles3_2", type = Inplaces.Arity3_2.class)
	public static void dependentAddDoubles3_2(double[] in1, double[] io, double[] in3, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
	}

	@OpMethod(names = "test.dependentAddDoubles3_3", type = Inplaces.Arity3_3.class)
	public static void dependentAddDoubles3_3(double[] in1, double[] in2, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
	}

	@OpMethod(names = "test.dependentAddDoubles4_1", type = Inplaces.Arity4_1.class)
	public static void dependentAddDoubles4_1(double[] io, double[] in2, double[] in3, double[] in4, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
	}

	@OpMethod(names = "test.dependentAddDoubles4_2", type = Inplaces.Arity4_2.class)
	public static void dependentAddDoubles4_2(double[] in1, double[] io, double[] in3, double[] in4, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
	}

	@OpMethod(names = "test.dependentAddDoubles4_3", type = Inplaces.Arity4_3.class)
	public static void dependentAddDoubles4_3(double[] in1, double[] in2, double[] io, double[] in4, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
	}

	@OpMethod(names = "test.dependentAddDoubles4_4", type = Inplaces.Arity4_4.class)
	public static void dependentAddDoubles4_4(double[] in1, double[] in2, double[] in3, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
	}

	@OpMethod(names = "test.dependentAddDoubles5_1", type = Inplaces.Arity5_1.class)
	public static void dependentAddDoubles5_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
	}

	@OpMethod(names = "test.dependentAddDoubles5_2", type = Inplaces.Arity5_2.class)
	public static void dependentAddDoubles5_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
	}

	@OpMethod(names = "test.dependentAddDoubles5_3", type = Inplaces.Arity5_3.class)
	public static void dependentAddDoubles5_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
	}

	@OpMethod(names = "test.dependentAddDoubles5_4", type = Inplaces.Arity5_4.class)
	public static void dependentAddDoubles5_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
	}

	@OpMethod(names = "test.dependentAddDoubles5_5", type = Inplaces.Arity5_5.class)
	public static void dependentAddDoubles5_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
	}

	@OpMethod(names = "test.dependentAddDoubles6_1", type = Inplaces.Arity6_1.class)
	public static void dependentAddDoubles6_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
	}

	@OpMethod(names = "test.dependentAddDoubles6_2", type = Inplaces.Arity6_2.class)
	public static void dependentAddDoubles6_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
	}

	@OpMethod(names = "test.dependentAddDoubles6_3", type = Inplaces.Arity6_3.class)
	public static void dependentAddDoubles6_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
	}

	@OpMethod(names = "test.dependentAddDoubles6_4", type = Inplaces.Arity6_4.class)
	public static void dependentAddDoubles6_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
	}

	@OpMethod(names = "test.dependentAddDoubles6_5", type = Inplaces.Arity6_5.class)
	public static void dependentAddDoubles6_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
	}

	@OpMethod(names = "test.dependentAddDoubles6_6", type = Inplaces.Arity6_6.class)
	public static void dependentAddDoubles6_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
	}

	@OpMethod(names = "test.dependentAddDoubles7_1", type = Inplaces.Arity7_1.class)
	public static void dependentAddDoubles7_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
	}

	@OpMethod(names = "test.dependentAddDoubles7_2", type = Inplaces.Arity7_2.class)
	public static void dependentAddDoubles7_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
	}

	@OpMethod(names = "test.dependentAddDoubles7_3", type = Inplaces.Arity7_3.class)
	public static void dependentAddDoubles7_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
	}

	@OpMethod(names = "test.dependentAddDoubles7_4", type = Inplaces.Arity7_4.class)
	public static void dependentAddDoubles7_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
	}

	@OpMethod(names = "test.dependentAddDoubles7_5", type = Inplaces.Arity7_5.class)
	public static void dependentAddDoubles7_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
	}

	@OpMethod(names = "test.dependentAddDoubles7_6", type = Inplaces.Arity7_6.class)
	public static void dependentAddDoubles7_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
	}

	@OpMethod(names = "test.dependentAddDoubles7_7", type = Inplaces.Arity7_7.class)
	public static void dependentAddDoubles7_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
	}

	@OpMethod(names = "test.dependentAddDoubles8_1", type = Inplaces.Arity8_1.class)
	public static void dependentAddDoubles8_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles8_2", type = Inplaces.Arity8_2.class)
	public static void dependentAddDoubles8_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles8_3", type = Inplaces.Arity8_3.class)
	public static void dependentAddDoubles8_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles8_4", type = Inplaces.Arity8_4.class)
	public static void dependentAddDoubles8_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles8_5", type = Inplaces.Arity8_5.class)
	public static void dependentAddDoubles8_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles8_6", type = Inplaces.Arity8_6.class)
	public static void dependentAddDoubles8_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles8_7", type = Inplaces.Arity8_7.class)
	public static void dependentAddDoubles8_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles8_8", type = Inplaces.Arity8_8.class)
	public static void dependentAddDoubles8_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
	}

	@OpMethod(names = "test.dependentAddDoubles9_1", type = Inplaces.Arity9_1.class)
	public static void dependentAddDoubles9_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_2", type = Inplaces.Arity9_2.class)
	public static void dependentAddDoubles9_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_3", type = Inplaces.Arity9_3.class)
	public static void dependentAddDoubles9_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_4", type = Inplaces.Arity9_4.class)
	public static void dependentAddDoubles9_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_5", type = Inplaces.Arity9_5.class)
	public static void dependentAddDoubles9_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_6", type = Inplaces.Arity9_6.class)
	public static void dependentAddDoubles9_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_7", type = Inplaces.Arity9_7.class)
	public static void dependentAddDoubles9_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_8", type = Inplaces.Arity9_8.class)
	public static void dependentAddDoubles9_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles9_9", type = Inplaces.Arity9_9.class)
	public static void dependentAddDoubles9_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
	}

	@OpMethod(names = "test.dependentAddDoubles10_1", type = Inplaces.Arity10_1.class)
	public static void dependentAddDoubles10_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_2", type = Inplaces.Arity10_2.class)
	public static void dependentAddDoubles10_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_3", type = Inplaces.Arity10_3.class)
	public static void dependentAddDoubles10_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_4", type = Inplaces.Arity10_4.class)
	public static void dependentAddDoubles10_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_5", type = Inplaces.Arity10_5.class)
	public static void dependentAddDoubles10_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_6", type = Inplaces.Arity10_6.class)
	public static void dependentAddDoubles10_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_7", type = Inplaces.Arity10_7.class)
	public static void dependentAddDoubles10_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_8", type = Inplaces.Arity10_8.class)
	public static void dependentAddDoubles10_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_9", type = Inplaces.Arity10_9.class)
	public static void dependentAddDoubles10_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles10_10", type = Inplaces.Arity10_10.class)
	public static void dependentAddDoubles10_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
	}

	@OpMethod(names = "test.dependentAddDoubles11_1", type = Inplaces.Arity11_1.class)
	public static void dependentAddDoubles11_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_2", type = Inplaces.Arity11_2.class)
	public static void dependentAddDoubles11_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_3", type = Inplaces.Arity11_3.class)
	public static void dependentAddDoubles11_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_4", type = Inplaces.Arity11_4.class)
	public static void dependentAddDoubles11_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_5", type = Inplaces.Arity11_5.class)
	public static void dependentAddDoubles11_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_6", type = Inplaces.Arity11_6.class)
	public static void dependentAddDoubles11_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_7", type = Inplaces.Arity11_7.class)
	public static void dependentAddDoubles11_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_8", type = Inplaces.Arity11_8.class)
	public static void dependentAddDoubles11_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_9", type = Inplaces.Arity11_9.class)
	public static void dependentAddDoubles11_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_10", type = Inplaces.Arity11_10.class)
	public static void dependentAddDoubles11_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles11_11", type = Inplaces.Arity11_11.class)
	public static void dependentAddDoubles11_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
	}

	@OpMethod(names = "test.dependentAddDoubles12_1", type = Inplaces.Arity12_1.class)
	public static void dependentAddDoubles12_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_2", type = Inplaces.Arity12_2.class)
	public static void dependentAddDoubles12_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_3", type = Inplaces.Arity12_3.class)
	public static void dependentAddDoubles12_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_4", type = Inplaces.Arity12_4.class)
	public static void dependentAddDoubles12_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_5", type = Inplaces.Arity12_5.class)
	public static void dependentAddDoubles12_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_6", type = Inplaces.Arity12_6.class)
	public static void dependentAddDoubles12_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_7", type = Inplaces.Arity12_7.class)
	public static void dependentAddDoubles12_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_8", type = Inplaces.Arity12_8.class)
	public static void dependentAddDoubles12_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_9", type = Inplaces.Arity12_9.class)
	public static void dependentAddDoubles12_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_10", type = Inplaces.Arity12_10.class)
	public static void dependentAddDoubles12_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_11", type = Inplaces.Arity12_11.class)
	public static void dependentAddDoubles12_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles12_12", type = Inplaces.Arity12_12.class)
	public static void dependentAddDoubles12_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
	}

	@OpMethod(names = "test.dependentAddDoubles13_1", type = Inplaces.Arity13_1.class)
	public static void dependentAddDoubles13_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_2", type = Inplaces.Arity13_2.class)
	public static void dependentAddDoubles13_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_3", type = Inplaces.Arity13_3.class)
	public static void dependentAddDoubles13_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_4", type = Inplaces.Arity13_4.class)
	public static void dependentAddDoubles13_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_5", type = Inplaces.Arity13_5.class)
	public static void dependentAddDoubles13_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_6", type = Inplaces.Arity13_6.class)
	public static void dependentAddDoubles13_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_7", type = Inplaces.Arity13_7.class)
	public static void dependentAddDoubles13_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_8", type = Inplaces.Arity13_8.class)
	public static void dependentAddDoubles13_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_9", type = Inplaces.Arity13_9.class)
	public static void dependentAddDoubles13_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_10", type = Inplaces.Arity13_10.class)
	public static void dependentAddDoubles13_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_11", type = Inplaces.Arity13_11.class)
	public static void dependentAddDoubles13_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_12", type = Inplaces.Arity13_12.class)
	public static void dependentAddDoubles13_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles13_13", type = Inplaces.Arity13_13.class)
	public static void dependentAddDoubles13_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
	}

	@OpMethod(names = "test.dependentAddDoubles14_1", type = Inplaces.Arity14_1.class)
	public static void dependentAddDoubles14_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_2", type = Inplaces.Arity14_2.class)
	public static void dependentAddDoubles14_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_3", type = Inplaces.Arity14_3.class)
	public static void dependentAddDoubles14_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_4", type = Inplaces.Arity14_4.class)
	public static void dependentAddDoubles14_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_5", type = Inplaces.Arity14_5.class)
	public static void dependentAddDoubles14_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_6", type = Inplaces.Arity14_6.class)
	public static void dependentAddDoubles14_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_7", type = Inplaces.Arity14_7.class)
	public static void dependentAddDoubles14_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_8", type = Inplaces.Arity14_8.class)
	public static void dependentAddDoubles14_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_9", type = Inplaces.Arity14_9.class)
	public static void dependentAddDoubles14_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_10", type = Inplaces.Arity14_10.class)
	public static void dependentAddDoubles14_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_11", type = Inplaces.Arity14_11.class)
	public static void dependentAddDoubles14_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_12", type = Inplaces.Arity14_12.class)
	public static void dependentAddDoubles14_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_13", type = Inplaces.Arity14_13.class)
	public static void dependentAddDoubles14_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles14_14", type = Inplaces.Arity14_14.class)
	public static void dependentAddDoubles14_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
	}

	@OpMethod(names = "test.dependentAddDoubles15_1", type = Inplaces.Arity15_1.class)
	public static void dependentAddDoubles15_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_2", type = Inplaces.Arity15_2.class)
	public static void dependentAddDoubles15_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_3", type = Inplaces.Arity15_3.class)
	public static void dependentAddDoubles15_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_4", type = Inplaces.Arity15_4.class)
	public static void dependentAddDoubles15_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_5", type = Inplaces.Arity15_5.class)
	public static void dependentAddDoubles15_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_6", type = Inplaces.Arity15_6.class)
	public static void dependentAddDoubles15_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_7", type = Inplaces.Arity15_7.class)
	public static void dependentAddDoubles15_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_8", type = Inplaces.Arity15_8.class)
	public static void dependentAddDoubles15_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_9", type = Inplaces.Arity15_9.class)
	public static void dependentAddDoubles15_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_10", type = Inplaces.Arity15_10.class)
	public static void dependentAddDoubles15_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_11", type = Inplaces.Arity15_11.class)
	public static void dependentAddDoubles15_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_12", type = Inplaces.Arity15_12.class)
	public static void dependentAddDoubles15_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_13", type = Inplaces.Arity15_13.class)
	public static void dependentAddDoubles15_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_14", type = Inplaces.Arity15_14.class)
	public static void dependentAddDoubles15_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io, double[] in15, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in15);
	}

	@OpMethod(names = "test.dependentAddDoubles15_15", type = Inplaces.Arity15_15.class)
	public static void dependentAddDoubles15_15(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
	}

	@OpMethod(names = "test.dependentAddDoubles16_1", type = Inplaces.Arity16_1.class)
	public static void dependentAddDoubles16_1(double[] io, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_2", type = Inplaces.Arity16_2.class)
	public static void dependentAddDoubles16_2(double[] in1, double[] io, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_3", type = Inplaces.Arity16_3.class)
	public static void dependentAddDoubles16_3(double[] in1, double[] in2, double[] io, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_4", type = Inplaces.Arity16_4.class)
	public static void dependentAddDoubles16_4(double[] in1, double[] in2, double[] in3, double[] io, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_5", type = Inplaces.Arity16_5.class)
	public static void dependentAddDoubles16_5(double[] in1, double[] in2, double[] in3, double[] in4, double[] io, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_6", type = Inplaces.Arity16_6.class)
	public static void dependentAddDoubles16_6(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] io, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_7", type = Inplaces.Arity16_7.class)
	public static void dependentAddDoubles16_7(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] io, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_8", type = Inplaces.Arity16_8.class)
	public static void dependentAddDoubles16_8(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] io, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_9", type = Inplaces.Arity16_9.class)
	public static void dependentAddDoubles16_9(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] io, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_10", type = Inplaces.Arity16_10.class)
	public static void dependentAddDoubles16_10(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] io, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_11", type = Inplaces.Arity16_11.class)
	public static void dependentAddDoubles16_11(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] io, double[] in12, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_12", type = Inplaces.Arity16_12.class)
	public static void dependentAddDoubles16_12(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] io, double[] in13, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_13", type = Inplaces.Arity16_13.class)
	public static void dependentAddDoubles16_13(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] io, double[] in14, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in14);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_14", type = Inplaces.Arity16_14.class)
	public static void dependentAddDoubles16_14(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] io, double[] in15, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in15);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_15", type = Inplaces.Arity16_15.class)
	public static void dependentAddDoubles16_15(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] io, double[] in16, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in16);
	}

	@OpMethod(names = "test.dependentAddDoubles16_16", type = Inplaces.Arity16_16.class)
	public static void dependentAddDoubles16_16(double[] in1, double[] in2, double[] in3, double[] in4, double[] in5, double[] in6, double[] in7, double[] in8, double[] in9, double[] in10, double[] in11, double[] in12, double[] in13, double[] in14, double[] in15, double[] io, @OpDependency(name = "test.addArrays") Inplaces.Arity2_1<double[], double[]> op) {
			op.mutate(io, in1);
			op.mutate(io, in2);
			op.mutate(io, in3);
			op.mutate(io, in4);
			op.mutate(io, in5);
			op.mutate(io, in6);
			op.mutate(io, in7);
			op.mutate(io, in8);
			op.mutate(io, in9);
			op.mutate(io, in10);
			op.mutate(io, in11);
			op.mutate(io, in12);
			op.mutate(io, in13);
			op.mutate(io, in14);
			op.mutate(io, in15);
	}
}
