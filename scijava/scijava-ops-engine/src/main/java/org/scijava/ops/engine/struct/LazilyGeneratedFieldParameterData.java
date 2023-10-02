
package org.scijava.ops.engine.struct;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scijava.common3.validity.ValidityProblem;
import org.scijava.function.Producer;
import org.scijava.ops.engine.OpUtils;
import org.scijava.struct.FunctionalMethodType;
import org.scijava.types.inference.InterfaceInference;

/**
 * Lazily generates the parameter data for a {@link List} of
 * {@link FunctionalMethodType}s. <b>If</b> there exists a <b>full</b> set of
 * {@code @param} and {@code @return} tags, the javadoc will be used to create
 * the parameter names and descriptions. Otherwise, reasonable defaults will be
 * used.
 *
 * @author Gabriel Selzer
 */
public class LazilyGeneratedFieldParameterData implements ParameterData {

	private static final Map<FieldInstance, MethodParamInfo> paramDataMap =
		new HashMap<>();

	private final FieldInstance fieldInstance;

	public LazilyGeneratedFieldParameterData(FieldInstance fieldInstance) {
		this.fieldInstance = fieldInstance;
	}


	public static MethodParamInfo getInfo(List<FunctionalMethodType> fmts,
		FieldInstance fieldInstance)
	{
		if (!paramDataMap.containsKey(fieldInstance)) generateFieldParamInfo(fmts, fieldInstance);
		return paramDataMap.get(fieldInstance);
	}

	public static synchronized void generateFieldParamInfo(
		List<FunctionalMethodType> fmts, FieldInstance fieldInstance)
	{
		if (paramDataMap.containsKey(fieldInstance)) return;

		Method sam = InterfaceInference.singularAbstractMethod(fieldInstance.field().getType());
		long numIns = sam.getParameterCount();
		long numOuts = 1; // There is always one output

		// determine the Op inputs/outputs
		Boolean[] paramOptionality = getParameterOptionality(fieldInstance.instance(),
				fieldInstance.field(), (int) numIns, new ArrayList<>());

		paramDataMap.put(fieldInstance, synthesizedMethodParamInfo(fmts, paramOptionality));
	}


	/**
	 * Determines if {@code tagType} is one of the tags that we are interested in
	 * scraping.
	 *
	 * @param tagType the tag we might need to scrape
	 * @return true iff it is interesting to us
	 */
	private static boolean validParameterTag(String tagType) {
		if (tagType.equals("input")) return true;
		if (tagType.equals("mutable")) return true;
		if (tagType.equals("container")) return true;
		if (tagType.equals("output")) return true;
		return false;
	}

	private static MethodParamInfo synthesizedMethodParamInfo(
		List<FunctionalMethodType> fmts, Boolean[] paramOptionality)
	{
		Map<FunctionalMethodType, String> fmtNames = new HashMap<>(fmts.size());
		Map<FunctionalMethodType, String> fmtDescriptions = new HashMap<>(fmts.size());
		Map<FunctionalMethodType, Boolean> fmtOptionality = new HashMap<>(fmts.size());

		int ins, outs, containers, mutables;
		ins = outs = containers = mutables = 1;
		int optionalIndex = 0;
		for (FunctionalMethodType fmt : fmts) {
			fmtDescriptions.put(fmt, "");
			switch (fmt.itemIO()) {
				case INPUT:
					fmtNames.put(fmt, "input" + ins++);
					fmtOptionality.put(fmt, paramOptionality[optionalIndex++]);
					break;
				case OUTPUT:
					fmtNames.put(fmt, "output" + outs++);
					break;
				case CONTAINER:
					fmtNames.put(fmt, "container" + containers++);
					break;
				case MUTABLE:
					fmtNames.put(fmt, "mutable" + mutables++);
					break;
				default:
					throw new RuntimeException("Unexpected ItemIO type encountered!");
			}
		}
		return new MethodParamInfo(fmtNames, fmtDescriptions, fmtOptionality);
	}

	@Override
	public List<SynthesizedParameterMember<?>> synthesizeMembers(
		List<FunctionalMethodType> fmts)
	{
		Producer<MethodParamInfo> p = //
			() -> LazilyGeneratedFieldParameterData.getInfo(fmts, fieldInstance);

		return fmts.stream() //
			.map(fmt -> new SynthesizedParameterMember<>(fmt, p)) //
			.collect(Collectors.toList());
	}

	// Helper methods
	private static Boolean[] getParameterOptionality(Object instance, Field field,
			int opParams, List<ValidityProblem> problems)
	{

		Class<?> fieldClass;
		try {
			fieldClass = field.get(instance).getClass();
		}
		catch (IllegalArgumentException | IllegalAccessException exc) {
			// TODO Auto-generated catch block
			problems.add(new ValidityProblem(exc));
			return FunctionalParameters.generateAllRequiredArray(opParams);
		}
		List<Method> fMethodsWithOptionals = FunctionalParameters.fMethodsWithOptional(fieldClass);
		Class<?> fIface = OpUtils.findFunctionalInterface(fieldClass);
		List<Method> fIfaceMethodsWithOptionals = FunctionalParameters.fMethodsWithOptional(fIface);

		if (fMethodsWithOptionals.isEmpty() && fIfaceMethodsWithOptionals.isEmpty()) {
			return FunctionalParameters.generateAllRequiredArray(opParams);
		}
		if (!fMethodsWithOptionals.isEmpty() && !fIfaceMethodsWithOptionals.isEmpty()) {
			problems.add(new ValidityProblem(
					"Multiple methods from the op type have optional parameters!"));
			return FunctionalParameters.generateAllRequiredArray(opParams);
		}
		if (fMethodsWithOptionals.isEmpty()) {
			return FunctionalParameters.findParameterOptionality(fIfaceMethodsWithOptionals.get(0));
		}
		if (fIfaceMethodsWithOptionals.isEmpty()) {
			return FunctionalParameters.findParameterOptionality(fMethodsWithOptionals.get(0));
		}
		return FunctionalParameters.generateAllRequiredArray(opParams);
	}


}
