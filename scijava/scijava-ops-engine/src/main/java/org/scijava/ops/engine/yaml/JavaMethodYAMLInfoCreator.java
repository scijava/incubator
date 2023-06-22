
package org.scijava.ops.engine.yaml;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.scijava.common3.Classes;
import org.scijava.ops.api.Hints;
import org.scijava.ops.api.OpInfo;
import org.scijava.ops.api.features.YAMLOpInfoCreator;
import org.scijava.ops.engine.hint.DefaultHints;
import org.scijava.ops.engine.matcher.impl.OpMethodInfo;

/**
 * A {@link YAMLOpInfoCreator} specialized for Java {@link Method}s.
 *
 * @author Gabriel Selzer
 */
public class JavaMethodYAMLInfoCreator extends AbstractYAMLOpInfoCreator {

	private static final Pattern genericsPattern = Pattern.compile("[<>]");

	@Override
	public boolean canCreateFrom(URI identifier) {
		return identifier.getScheme().startsWith("javaMethod");
	}

	@Override
	OpInfo create(String identifier, String[] names, double priority, Hints hints,
		String version, Map<String, Object> yaml) throws Exception
	{
		// first, remove generics
		String rawIdentifier = sanitizeGenerics(identifier);

		// parse class
		int clsIndex = rawIdentifier.lastIndexOf('.', rawIdentifier.indexOf('('));
		String clsString = rawIdentifier.substring(0, clsIndex);
		Class<?> src = Classes.load(clsString);
		// parse method
		String methodString = rawIdentifier.substring(clsIndex + 1, rawIdentifier.indexOf(
			'('));
		String[] paramStrings = rawIdentifier.substring(rawIdentifier.indexOf('(') + 1,
			rawIdentifier.indexOf(')')).split("\\s*,\\s*");
		Class<?>[] paramClasses = new Class<?>[paramStrings.length];
		for (int i = 0; i < paramStrings.length; i++) {
			paramClasses[i] = Classes.load(paramStrings[i]);
		}
		Method method = src.getMethod(methodString, paramClasses);
		// parse op type
		String typeString = (String) yaml.get("type");
		Class<?> opType = Classes.load(typeString);

		// create the OpInfo
		return new OpMethodInfo(method, opType, new DefaultHints(), priority, names);
	}

	private static String sanitizeGenerics(String method) {
		int nested = 0;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < method.length(); i++) {
			char c = method.charAt(i);
			if(c == '<') {
				nested++;
			}
			if (nested == 0) {
				sb.append(c);
			}
			if (c == '>' && nested > 0) {
				nested--;
			}
		}
		return sb.toString();
	}
}
