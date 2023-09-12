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
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.yaml.snakeyaml.Yaml;

public class JavadocAnnotationProcessor extends AbstractProcessor {

    private static final String PACKAGES_OPTION = "javadoc.parse";

    private YamlJavadocBuilder yamlJavadocBuilder;

    private final Yaml yaml = new Yaml();

    private final Map<String, List<Map<String, Object>>> yamlMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing!!!!!");
        this.yamlJavadocBuilder = new YamlJavadocBuilder(processingEnv);

        final Map<String, String> options = processingEnv.getOptions();
        final String packagesOption = options.get(PACKAGES_OPTION);
        if ("true".equals(packagesOption)) {
            // Make sure each element only gets processed once.
            final Set<Element> alreadyProcessed = new HashSet<>();

            // If retaining Javadoc for all packages, the @RetainJavadoc annotation is redundant.
            // Otherwise, make sure annotated classes have their Javadoc retained regardless of package.
            for (TypeElement annotation : annotations) {
                if (isRetainJavadocAnnotation(annotation)) {
                    for (Element e : roundEnvironment.getElementsAnnotatedWith(annotation)) {
                        generateJavadoc(e, alreadyProcessed);
                    }
                }
            }

            for (Element e : roundEnvironment.getRootElements()) {
                generateJavadoc(e, alreadyProcessed);
            }

            if (!roundEnvironment.getRootElements().isEmpty()) {
                Element placeHolder =
                    roundEnvironment.getRootElements().iterator().next();
                for (String key : yamlMap.keySet()) {
                    try {
                        outputYamlDoc(placeHolder, yaml.dump(yamlMap.get(key)),
                            key);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        return false;
    }

    private static boolean isRetainJavadocAnnotation(TypeElement annotation) {
        return true;
    }

    private static <E extends Enum<E>> Optional<E> findValueOf(Class<E> enumClass, String valueName) {
        try {
            return Optional.of(Enum.valueOf(enumClass, valueName));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    // ElementKind.RECORD was added in Java 16. We want to process records, but also
    // remain compatible with earlier Java versions.
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static final Optional<ElementKind> RECORD = findValueOf(ElementKind.class, "RECORD");

    private static final EnumSet<ElementKind> elementKindsToInspect = EnumSet.of(
            ElementKind.CLASS,
            ElementKind.INTERFACE,
            ElementKind.ENUM
            // and RECORD, but only if the Java version supports it
    );

    static {
        RECORD.ifPresent(elementKindsToInspect::add);
    }

    private void generateJavadoc(Element element, Set<Element> alreadyProcessed) {
        ElementKind kind = element.getKind();
        if (elementKindsToInspect.contains(kind)) {
            try {
                generateJavadocForClass(element, alreadyProcessed);
            } catch (Exception ex) {
                processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Javadoc retention failed; " + ex, element);
                throw new RuntimeException("Javadoc retention failed for " + element, ex);
            }
        }

        for (Element enclosed : element.getEnclosedElements()) {
            generateJavadoc(enclosed, alreadyProcessed);
        }
    }

    private void generateJavadocForClass(Element element, Set<Element> alreadyProcessed) throws IOException {
        if (!alreadyProcessed.add(element)) {
            return;
        }
        TypeElement classElement = (TypeElement) element;
        yamlJavadocBuilder.getClassJavadocAsYamlOrNull(classElement) //
            .forEach(impl -> {
                yamlMap.computeIfAbsent(impl.type(), i -> new ArrayList<>()).add(Collections.singletonMap(impl.type(), impl.dumpData()));
            });
    }

    private void outputYamlDoc(Element classElement, String doc, String type) throws IOException {
        FileObject resource = createYamlResourceFile(classElement, type);

        try (OutputStream os = resource.openOutputStream()) {
            os.write(doc.getBytes(UTF_8));
        }
    }

    private FileObject createYamlResourceFile(Element classElement, String type) throws IOException {
        String packageName = processingEnv.getElementUtils().getModuleOf(classElement).getQualifiedName().toString();
        String relativeName = type + ".yaml";
        return processingEnv.getFiler()
            .createResource(StandardLocation.CLASS_OUTPUT, "", relativeName);
    }
    private static PackageElement getPackageElement(Element element) {
        if (element instanceof PackageElement) {
            return (PackageElement) element;
        }
        return getPackageElement(element.getEnclosingElement());
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
        return Collections.singleton(PACKAGES_OPTION);
    }
}
