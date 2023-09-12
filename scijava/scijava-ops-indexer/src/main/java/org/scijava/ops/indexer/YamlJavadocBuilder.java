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
import static org.scijava.ops.indexer.RuntimeJavadocHelper.isBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

class YamlJavadocBuilder {

    private ProcessingEnvironment processingEnv;

    YamlJavadocBuilder(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    List<ImplData> getClassJavadocAsYamlOrNull(TypeElement classElement) {
        Map<ElementKind, List<Element>> children = new HashMap<>();
        for (Element enclosedElement : classElement.getEnclosedElements()) {
            children.computeIfAbsent(enclosedElement.getKind(), k -> new ArrayList<>())
                    .add(enclosedElement);
        }

        final List<Element> emptyList = Collections.emptyList();
        List<Element> enclosedFields = defaultIfNull(children.get(FIELD), emptyList);
        List<Element> enclosedMethods = defaultIfNull(children.get(METHOD), emptyList);


        List<ImplData> implList = new ArrayList<>();
        implList.addAll(getJavadocsAsJson(Collections.singletonList(classElement), classJavadocAsJson));
        implList.addAll(getJavadocsAsJson(enclosedFields, fieldJavadocAsJson));
        implList.addAll(getJavadocsAsJson(enclosedMethods, methodJavadocAsJson));
        return implList;
    }

    private static List<ImplData> getJavadocsAsJson(List<Element> elements, Function<Element, ImplData> createDoc) {
        return elements.stream().map(createDoc).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    private final Function<Element, ImplData> fieldJavadocAsJson = (field) -> {
        String javadoc = processingEnv.getElementUtils().getDocComment(field);
        if (isBlank(javadoc)) {
            return null;
        }
        if (javadoc.contains("@implNote")) {
            return new ImplFieldData(field, javadoc);
        }
        return null;
    };

    private final Function<Element, ImplData> methodJavadocAsJson = (method) -> {
        assert method instanceof ExecutableElement;

        String javadoc = processingEnv.getElementUtils().getDocComment(method);
        if (isBlank(javadoc)) {
            return null;
        }
        if (javadoc.contains("@implNote")) {
            return new ImplMethodData(processingEnv, (ExecutableElement) method, javadoc);
        }
        return null;
    };

    private final Function<Element, ImplData> classJavadocAsJson = (cls) -> {
        String javadoc = processingEnv.getElementUtils().getDocComment(cls);
        if (isBlank(javadoc)) {
            return null;
        }
        if (javadoc.contains("@implNote")) {
            return new ImplClassData(cls, javadoc);
        }
        return null;
    };

    private static <T> T defaultIfNull(T actualValue, T defaultValue) {
        return actualValue != null ? actualValue : defaultValue;
    }
}
