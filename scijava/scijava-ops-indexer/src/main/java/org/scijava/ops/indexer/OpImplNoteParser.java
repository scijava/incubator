/*
 * Copyright 2015 David Nault and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// This class was adapted from the
// com.github.therapi.runtimejavadoc.scribe.JavadocAnnotationProcessor class
// of Therapi Runtime Javadoc 0.13.0, which is distributed
// under the Apache 2 license.

package org.scijava.ops.indexer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.METHOD;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.yaml.snakeyaml.Yaml;

/**
 * {@link javax.annotation.processing.Processor} used to find code blocks
 * annotated as Ops, using the implNote syntax.
 * 
 * @author Gabriel Selzer
 */
public class OpImplNoteParser extends AbstractProcessor {

	public static final String OP_VERSION = "op.version";
	private static final String PARSE_OPS = "parse.ops";

	private final Yaml yaml = new Yaml();

	private final List<Map<String, Object>> opData = new ArrayList<>();

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
		RoundEnvironment roundEnvironment)
	{
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
			"Processing Ops written using the implNote syntax...");
		final Map<String, String> options = processingEnv.getOptions();
		if ("true".equals(options.get(PARSE_OPS))) {
			// Make sure each element only gets processed once.
			final Set<Element> alreadyProcessed = new HashSet<>();

			// If retaining Javadoc for all packages, the @RetainJavadoc annotation is
			// redundant.
			// Otherwise, make sure annotated classes have their Javadoc retained
			// regardless of package.
			for (TypeElement annotation : annotations) {
				for (Element e : roundEnvironment.getElementsAnnotatedWith(
					annotation))
				{
					generateJavadoc(e, alreadyProcessed);
				}
			}

			for (Element e : roundEnvironment.getRootElements()) {
				generateJavadoc(e, alreadyProcessed);
			}

			if (!roundEnvironment.getRootElements().isEmpty() && !opData.isEmpty()) {
				try {
					outputYamlDoc(yaml.dump(opData));
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		}
		// This annotation processor only looks at Javadoc - it doesn't look at, and
		// thus doesn't claim any annotations
		return false;
	}

	// TODO: Consider adding record
	private static final EnumSet<ElementKind> elementKindsToInspect = EnumSet.of(
		ElementKind.CLASS, ElementKind.INTERFACE, ElementKind.ENUM);

	private void generateJavadoc(Element element, Set<Element> alreadyProcessed) {
		if (!alreadyProcessed.add(element)) {
			return;
		}
		if (elementKindsToInspect.contains(element.getKind())) {
			TypeElement classElement = (TypeElement) element;
			getClassJavadocAsYamlOrNull(classElement) //
				.forEach(impl -> opData.add(impl.dumpData()));
		}
	}

	private void outputYamlDoc(String doc) throws IOException {
		FileObject resource = processingEnv.getFiler().createResource( //
			StandardLocation.CLASS_OUTPUT, //
			"", //
			"op.yaml" //
		);
		try (OutputStream os = resource.openOutputStream()) {
			os.write(doc.getBytes(UTF_8));
		}
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Collections.singleton("*");
	}

	@Override
	public Set<String> getSupportedOptions() {
		return Collections.singleton(PARSE_OPS);
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

	public static ExecutableElement findAbstractFunctionalMethod( //
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


	// TODO: Consider adding record
	private static final EnumSet<ElementKind> javadocKindsToInspect = EnumSet.of(
			ElementKind.CLASS, ElementKind.METHOD, ElementKind.FIELD);

	private List<OpImplData> getClassJavadocAsYamlOrNull(
		TypeElement classElement)
	{
		List<OpImplData> implList = new ArrayList<>();
		// Check all enclosed elements
		for (Element e : classElement.getEnclosedElements()) {
			// Only check fields and methods - inner classes will get checked
			// on their own
			if (javadocKindsToInspect.contains(e.getKind())) {
				var optionalImpl = elementToImplData.apply(e);
				optionalImpl.ifPresent(implList::add);
			}
		}

		// Finally, check the class itself to see if it is an implNote
		Optional<OpImplData> clsData = elementToImplData.apply(classElement);
		clsData.ifPresent(implList::add);

		return implList;
	}

	private final Function<Element, Optional<OpImplData>> elementToImplData = (
		element) -> {
		String javadoc = processingEnv.getElementUtils().getDocComment(element);
		if (javadoc != null && javadoc.contains("implNote op")) {
			try {
				if (element.getKind() == CLASS) {
					TypeElement typeElement = (TypeElement) element;
					var fMethod = findFunctionalMethod(processingEnv, typeElement);
					var fMethodDoc = processingEnv.getElementUtils().getDocComment(
						fMethod);
					return Optional.of(new OpClassImplData(typeElement, fMethod, javadoc,
						fMethodDoc, processingEnv));
				}
				else if (element.getKind() == METHOD) {
					return Optional.of(new OpMethodImplData((ExecutableElement) element,
						javadoc, processingEnv));
				}
				else if (element.getKind() == FIELD) {
					return Optional.of(new OpFieldImplData(element, javadoc,
						processingEnv));
				}
			}
			catch (Exception e) {
				printProcessingException(element, e, processingEnv);
			}
		}
		return Optional.empty();
	};

}
