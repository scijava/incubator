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

import static java.nio.charset.StandardCharsets.UTF_8;

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
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.yaml.snakeyaml.Yaml;

public class JavadocAnnotationProcessor extends AbstractProcessor {

    private static final String PARSE_OPS = "parse.ops";

    private YamlJavadocBuilder yamlJavadocBuilder;

    private final Yaml yaml = new Yaml();

    private final List<Map<String, Object>> opData = new ArrayList<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing!!!!!");
        this.yamlJavadocBuilder = new YamlJavadocBuilder(processingEnv);

        final Map<String, String> options = processingEnv.getOptions();
        if ("true".equals(options.get(PARSE_OPS))) {
            // Make sure each element only gets processed once.
            final Set<Element> alreadyProcessed = new HashSet<>();

            // If retaining Javadoc for all packages, the @RetainJavadoc annotation is redundant.
            // Otherwise, make sure annotated classes have their Javadoc retained regardless of package.
            for (TypeElement annotation : annotations) {
                if (isRetainJavadocAnnotation()) {
                    for (Element e : roundEnvironment.getElementsAnnotatedWith(annotation)) {
                        generateJavadoc(e, alreadyProcessed);
                    }
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
        return false;
    }

    private static boolean isRetainJavadocAnnotation() {
        return true;
    }

    // TODO: Consider adding record
    private static final EnumSet<ElementKind> elementKindsToInspect = EnumSet.of(
            ElementKind.CLASS,
            ElementKind.INTERFACE,
            ElementKind.ENUM
    );

    private void generateJavadoc(Element element, Set<Element> alreadyProcessed) {
        ElementKind kind = element.getKind();
        if (elementKindsToInspect.contains(kind)) {
            try {
                generateJavadocForClass(element, alreadyProcessed);
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Javadoc retention failed; " + sw, element);
                throw new RuntimeException("Javadoc retention failed for " + element, ex);
            }
        }

        for (Element enclosed : element.getEnclosedElements()) {
            generateJavadoc(enclosed, alreadyProcessed);
        }
    }

    private void generateJavadocForClass(Element element, Set<Element> alreadyProcessed) {
        if (!alreadyProcessed.add(element)) {
            return;
        }
        TypeElement classElement = (TypeElement) element;
        yamlJavadocBuilder.getClassJavadocAsYamlOrNull(classElement) //
            .forEach(impl -> opData.add(impl.dumpData()));
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
}
