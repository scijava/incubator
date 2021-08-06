
package org.scijava.ops.engine.struct;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.scijava.ValidityProblem;
import org.scijava.ops.spi.OpProgressReporter;
import org.scijava.struct.MemberParser;
import org.scijava.struct.ValidityException;
import org.scijava.util.ClassUtils;

public class ClassProgressTrackerMemberParser implements
	MemberParser<Class<?>, FieldProgressTrackerMember<?>>
{

	@Override
	public List<FieldProgressTrackerMember<?>> parse(Class<?> source)
		throws ValidityException
	{
				if (source == null) return null;

				final ArrayList<FieldProgressTrackerMember<?>> items = new ArrayList<>();
				final ArrayList<ValidityProblem> problems = new ArrayList<>();

				// NB: Reject abstract classes.
				org.scijava.struct.Structs.checkModifiers(source.getName() + ": ", problems, source.getModifiers(), true, Modifier.ABSTRACT);

				// Parse field level @OpDependency annotations.
				parseFieldProgressTrackers(items, problems, source);

				// Fail if there were any problems.
				if (!problems.isEmpty()) throw new ValidityException(problems);

				return items;
	}

	private static void parseFieldProgressTrackers(final List<FieldProgressTrackerMember<?>> items,
		final List<ValidityProblem> problems, Class<?> annotatedClass)
	{
		final List<Field> fields = ClassUtils.getAnnotatedFields(annotatedClass,
			OpProgressReporter.class);
		for (final Field f : fields) {
			f.setAccessible(true);
			final boolean isFinal = Modifier.isFinal(f.getModifiers());
			if (isFinal) {
				final String name = f.getName();
				// Final fields are bad because they cannot be modified.
				final String error = "Invalid final ProgressTracker field: " + name;
				problems.add(new ValidityProblem(error));
				// Skip invalid ProgressTrackers
				continue;
			}
			if (items.size() > 0) {
				final String name = f.getName();
				// Multiple ProgressTrackers introduce ambiguity
				final String error =
					"Only one Progress Tracker allowed per Op! Field " + name +
						" should be removed";
				problems.add(new ValidityProblem(error));
				// Skip invalid ProgressTrackers
				continue;
			}
			final FieldProgressTrackerMember<?> item = new FieldProgressTrackerMember<>(f);
			items.add(item);
		}
	}

}
