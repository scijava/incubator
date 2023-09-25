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

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.METHOD;
import static org.scijava.ops.indexer.OpImplNoteParser.findFunctionalMethod;
import static org.scijava.ops.indexer.OpImplNoteParser.printProcessingException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

class YamlJavadocBuilder {


	// TODO: Consider adding record
	private static final EnumSet<ElementKind> elementKindsToInspect = EnumSet.of(
			ElementKind.CLASS,
			ElementKind.FIELD,
			ElementKind.METHOD
	);

	private ProcessingEnvironment processingEnv;

	YamlJavadocBuilder(ProcessingEnvironment processingEnv) {
		this.processingEnv = processingEnv;
	}

	List<OpImplData> getClassJavadocAsYamlOrNull(TypeElement classElement) {
		List<OpImplData> implList = new ArrayList<>();
		// Check all enclosed elements
		for (Element e : classElement.getEnclosedElements()) {
			// Only check fields and methods - inner classes will get checked
			// on their own
			if (elementKindsToInspect.contains(e.getKind())) {
				var optionalImpl = elementToImplData.apply(e);
				optionalImpl.ifPresent(implList::add);
			}
		}

		// Finally, check the class itself to see if it is an implNote
		Optional<OpImplData> clsData = elementToImplData.apply(classElement);
		clsData.ifPresent(implList::add);

		return implList;
	}

	private final Function<Element, Optional<OpImplData>> elementToImplData = (element) -> {
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
			catch (InvalidOpJavadocException e) {
				throw e;
			}
			catch (Exception e) {
				printProcessingException(element, e, processingEnv);
			}
		}
		return Optional.empty();
	};
	
}
