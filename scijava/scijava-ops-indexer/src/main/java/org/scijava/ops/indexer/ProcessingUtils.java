
package org.scijava.ops.indexer;

import static javax.lang.model.element.ElementKind.METHOD;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * A set of static utilties useful for processing Ops
 *
 * @author Gabriel Selzer
 */
public class ProcessingUtils {

	/**
	 * Given a Javadoc comment, separates the comment by the Javadoc tags
	 * (e.g. @author, @param, etc.). Therefore, each string returned by
	 * blockSeparator.split(String) will be:
	 * <ul>
	 * <li>A string beginning with and including a javadoc tag
	 * (e.g. @author, @param, etc.)</li>
	 * <li>The description of the code block - this happens when there's a
	 * description before the tags start</li>
	 * </ul>
	 */
	public static final Pattern blockSeparator = Pattern.compile("^\\s*(?=@\\S)",
		Pattern.MULTILINE);
	/**
	 * Given a string, splits the String by whitespace UNLESS the whitespace is
	 * inside a set of single quotes. Useful for parsing tags, especially implNote
	 * tags.
	 */
	public static final Pattern tagElementSeparator = Pattern.compile(
		"\\s*,*\\s+(?=(?:[^']*'[^']*')*[^']*$)");

	private ProcessingUtils() {
		throw new AssertionError("not instantiable");
	}

	public static void printProcessingException(Element source, Throwable t,
		ProcessingEnvironment env)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		env.getMessager().printMessage(Diagnostic.Kind.ERROR,
			"Exception parsing source + " + source + ": " + sw);
	}

	public static ExecutableElement findFunctionalMethod(
		ProcessingEnvironment env, TypeElement source)
	{
		// Step 1: Find abstract interface method
		ExecutableElement fMethod = findAbstractFunctionalMethod(env, source);
		if (fMethod != null) {
			for (Element e : env.getElementUtils().getAllMembers(source)) {
				if (e.getSimpleName().equals(fMethod.getSimpleName())) {
					return (ExecutableElement) e;
				}
			}
		}
		throw new IllegalArgumentException("Op " + source +
			" does not declare a functional method!");
	}

	private static ExecutableElement findAbstractFunctionalMethod( //
		ProcessingEnvironment env, //
		TypeElement source //
	) {
		int abstractMethodCount = 0;
		ExecutableElement firstAbstractMethod = null;
		for (Element e : source.getEnclosedElements()) {
			if (e.getKind() == METHOD && e.getModifiers().contains(
				Modifier.ABSTRACT))
			{
				firstAbstractMethod = (ExecutableElement) e;
				abstractMethodCount++;

			}
		}
		if (abstractMethodCount == 1) {
			return firstAbstractMethod;
		}
		else {
			// First, check the interfaces
			for (TypeMirror e : source.getInterfaces()) {
				Element iFace = env.getTypeUtils().asElement(e);
				if (iFace instanceof TypeElement) {
					ExecutableElement fMethod = findAbstractFunctionalMethod(env,
						(TypeElement) iFace);
					if (fMethod != null) return fMethod;
				}
			}
			// Then, check the superclass
			Element superCls = env.getTypeUtils().asElement(source.getSuperclass());
			if (superCls instanceof TypeElement) {
				return findAbstractFunctionalMethod(env, (TypeElement) superCls);
			}
			return null;
		}
	}
}
