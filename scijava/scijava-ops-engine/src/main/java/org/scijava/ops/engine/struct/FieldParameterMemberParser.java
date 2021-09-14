
package org.scijava.ops.engine.struct;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.scijava.ValidityProblem;
import org.scijava.struct.MemberParser;
import org.scijava.struct.ValidityException;
import org.scijava.types.Types;

public class FieldParameterMemberParser implements
	MemberParser<Field, SynthesizedParameterMember<?>>
{

	@Override
	public List<SynthesizedParameterMember<?>> parse(Field source)
		throws ValidityException
	{
		if (source == null) return null;
		Class<?> c = source.getDeclaringClass();
		// obtain a parameterData (preferably one that scrapes the javadoc)
		ParameterData paramData;
		try {
			paramData = new JavadocParameterData(source);
		}
		catch (IllegalArgumentException e) {
			paramData = new SynthesizedParameterData();
		}
		source.setAccessible(true);

		final ArrayList<SynthesizedParameterMember<?>> items = new ArrayList<>();
		final ArrayList<ValidityProblem> problems = new ArrayList<>();
		final Set<String> names = new HashSet<>();
		final Type fieldType = Types.fieldType(source, c);

		org.scijava.struct.Structs.checkModifiers(source.toString() + ": ", problems, source
			.getModifiers(), false, Modifier.FINAL);
		FunctionalParameters.parseFunctionalParameters(items, names, problems, fieldType,
			paramData);
		// Fail if there were any problems.
		if (!problems.isEmpty()) {
			throw new ValidityException(problems);
		}
		return items;
	}

}
