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

package org.scijava.ops.indexer;

import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.METHOD;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

class YamlJavadocBuilder {

	private ProcessingEnvironment processingEnv;

	YamlJavadocBuilder(ProcessingEnvironment processingEnv) {
		this.processingEnv = processingEnv;
	}

	List<OpImplData> getClassJavadocAsYamlOrNull(TypeElement classElement) {
		// Check all enclosed elements
		List<OpImplData> implList = classElement.getEnclosedElements().stream() //
			// Only check methods and fields, as inner classes will be called
			// separately
			.filter(e -> e.getKind() == METHOD || e.getKind() == FIELD) //
			// Convert each method/field into an ImplData
			.map(elementToImplData) //
			// Remove the nulls (i.e. methods/fields w/o an implNote tag)
			.filter(Objects::nonNull) //
			.collect(Collectors.toList());

		// Finally, check the class itself to see if it is an implNote
		OpImplData clsData = elementToImplData.apply(classElement);
		if (clsData != null) implList.add(clsData);

		return implList;
	}

	private final Function<Element, OpImplData> elementToImplData = (element) -> {
		String javadoc = processingEnv.getElementUtils().getDocComment(element);
		if (javadoc != null && javadoc.contains("implNote op")) {
			try {
				switch (element.getKind()) {
					case CLASS:
						var fMethod = findFunctionalMethod(processingEnv,
							(TypeElement) element);
						var fMethodDoc = processingEnv.getElementUtils().getDocComment(
							fMethod);
						return new OpClassImplData(element, fMethod, javadoc, fMethodDoc,
							processingEnv);
					case METHOD:
						return new OpMethodImplData(processingEnv,
							(ExecutableElement) element, javadoc);
					case FIELD:
						return new OpFieldImplData(element, javadoc, processingEnv);
					default:
						return null;
				}
			}
			catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, sw
					.toString());
			}
		}
		return null;
	};

	private ExecutableElement findFunctionalMethod(ProcessingEnvironment env,
		TypeElement source)
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

	private ExecutableElement findAbstractFunctionalMethod( //
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
				ExecutableElement fMethod = findAbstractFunctionalMethod(env,
					(TypeElement) superCls);
				return fMethod;
			}
			return null;
		}
	}

}
