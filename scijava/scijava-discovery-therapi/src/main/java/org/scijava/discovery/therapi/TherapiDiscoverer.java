
package org.scijava.discovery.therapi;

import com.github.therapi.runtimejavadoc.BaseJavadoc;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.MethodJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scijava.discovery.Discoverer;
import org.scijava.discovery.Discovery;
import org.scijava.parse2.ParseService;

public class TherapiDiscoverer implements Discoverer {

	private ParseService parser;

	public TherapiDiscoverer(ParseService parser) {
		this.parser = parser;
	}

	@Override
	public List<Discovery<AnnotatedElement>> elementsTaggedWith(String tagType) {
		// combine class and module path resources into a single list
		List<String> paths = classAndModulePathResources();

		// for each path element, find the list of files whose javadoc has been
		// retained.
		Map<ClassJavadoc, String> javadocData = getJavadocs(paths);

		List<Discovery<AnnotatedElement>> taggedClasses = discoverTaggedClasses(
			tagType, javadocData);
		List<Discovery<AnnotatedElement>> taggedMethods = discoverTaggedMethods(
			tagType, javadocData);
		List<Discovery<AnnotatedElement>> taggedFields = discoverTaggedFields(
			tagType, javadocData);

		// return concatenation of classes, methods, and fields.
		return Stream.of(taggedClasses, taggedMethods, taggedFields) //
			.flatMap(Collection::stream) //
			.collect(Collectors.toList());
	}

	private List<String> classAndModulePathResources() {
		// get classpath resources
		List<String> paths = new ArrayList<>(Arrays.asList(System.getProperty(
			"java.class.path").split(File.pathSeparator)));
		// add modulepath resources
		paths.addAll(Arrays.asList(System.getProperty("jdk.module.path").split(
			File.pathSeparator)));
		return paths;
	}

	private Map<ClassJavadoc, String> getJavadocs(List<String> paths) {
		// for each path, find the list of classes whose javadoc has been retained
		Map<String, List<String>> javadocedFiles = new HashMap<>();
		paths.parallelStream().forEach(p -> {
			List<String> files = getJavadocedFiles(p).parallelStream().map(
				field -> getFullyQualifiedName(field, p)).collect(Collectors.toList());
			if (!files.isEmpty()) javadocedFiles.put(p, files);
		});

		// for each javadoc'd class, find its javadoc.
		Map<ClassJavadoc, String> javadocData = new HashMap<>();
		javadocedFiles.entrySet().parallelStream().forEach(e -> {
			Class<?> c;
			try {
				c = getClass(e.getValue().get(0));
				e.getValue().parallelStream().forEach(s -> javadocData.put(
					RuntimeJavadoc.getJavadoc(s, c), s));
			}
			catch (ClassNotFoundException exc) {
				return;
			}
		});

		return javadocData;
	}

	/**
	 * list files in the given directory and subdirs (with recursion)
	 * 
	 * @param path the directory to recurse through
	 * @return a list of all files contained within the root directory
	 *         {@code path}
	 */
	private static List<File> getJavadocedFiles(String path) {
		List<File> filesList = new ArrayList<>();
		final File file = new File(path);
		if (file.isDirectory()) {
			recurse(filesList, file);
		}
		else if (file.getPath().endsWith(".jar")) {
			try {
				for (String s : getJarContent(path))
					if (s.endsWith("__Javadoc.json")) {
						filesList.add(new File(s));
					}
			}
			catch (IOException exc) {
				// TODO Auto-generated catch block
				exc.printStackTrace();
			}
		}
		else {
			if (path.endsWith("__Javadoc.json")) filesList.add(file);
		}
		return filesList;
	}

	private static void recurse(List<File> filesList, File f) {
		File list[] = f.listFiles();
		for (File file : list) {
			if (file.isDirectory()) {
				recurse(filesList, file);
			}
			else {
				if (file.getPath().endsWith("__Javadoc.json")) filesList.add(file);
			}
		}
	}

	private static Class<?> getClass(String name) throws ClassNotFoundException {
		return getClassLoader().loadClass(name);
	}

	/**
	 * Gets the class loader to use. This will be the current thread's context
	 * class loader if non-null; otherwise it will be the system class loader.
	 * <p>
	 * Forked from SciJava Common's Context class.
	 *
	 * @see Thread#getContextClassLoader()
	 * @see ClassLoader#getSystemClassLoader()
	 */
	private static ClassLoader getClassLoader() {
		final ClassLoader contextCL = Thread.currentThread()
			.getContextClassLoader();
		return contextCL != null ? contextCL : ClassLoader.getSystemClassLoader();
	}

	private static String getFullyQualifiedName(File f, String path) {
		if (f.getPath().contains(path)) {
			return f.getPath().substring(path.length() + 1, f.getPath().indexOf(
				"__Javadoc.json")).replace(System.getProperty("file.separator"), ".");
		}
		return f.getPath().substring(0, f.getPath().indexOf("__Javadoc.json"))
			.replace(System.getProperty("file.separator"), ".");
	}

	/**
	 * List the content of the given jar
	 * 
	 * @param jarPath the path to a jar
	 * @return the contents of the jar
	 * @throws IOException
	 */
	private static List<String> getJarContent(String jarPath) throws IOException {
		List<String> content = new ArrayList<>();
		try (JarFile jarFile = new JarFile(jarPath)) {
			Enumeration<JarEntry> e = jarFile.entries();
			while (e.hasMoreElements()) {
				JarEntry entry = e.nextElement();
				String name = entry.getName();
				content.add(name);
			}
		}
		return content;
	}

	private BiFunction<BaseJavadoc, String, Optional<String>> getTag = (javadoc,
		tagType) -> {
		return javadoc.getOther().stream() //
			.filter(m -> m.getName().equals("implNote") && m.getComment().toString()
				.startsWith(tagType)).map(m -> m.getComment().toString()).findFirst();
	};

	private Map<String, ?> itemsFromTag(String tagType, String tag) {
		String tagBody = tag.substring(tag.indexOf(tagType) + tagType.length()).trim();
		System.out.println("Parser: " + parser);
		System.out.println("Tag Body: " + tagBody);
		return parser.parse(tagBody.replaceAll("\\s+",""), true).asMap();
	}

	private Discovery<AnnotatedElement> mapFieldToDiscovery(FieldJavadoc javadoc,
		String tagType, Field[] fields)
	{
		Optional<String> tag = getTag.apply(javadoc, tagType);
		if (tag.isEmpty()) return null;

		Optional<Field> taggedField = Arrays.stream(fields) //
			.filter(field -> javadoc.getName().equals(field.getName())) //
			.findAny();
		if (taggedField.isEmpty()) return null;
		Supplier<Map<String, ?>> tagOptions = () -> itemsFromTag(tagType, tag
			.get());
		return new Discovery<>(taggedField.get(), tagType, tagOptions);
	}

	private List<Discovery<AnnotatedElement>> fieldsToDiscoveries
		(Map.Entry<ClassJavadoc, String> entry, String tagType) {
			Field[] fields = extractDeclaredFields(entry);
			// stream FieldJavadocs of the given ClassJavadoc
			return entry.getKey().getFields().parallelStream() //
				.map(j -> mapFieldToDiscovery(j, tagType, fields)) //
				.filter(Objects::nonNull) //
				.collect(Collectors.toList());
		}

	private Discovery<AnnotatedElement> mapMethodToDiscovery(
		MethodJavadoc javadoc, String tagType, Method[] methods)
	{
		Optional<String> tag = getTag.apply(javadoc, tagType);
		if (tag.isEmpty()) return null;

		Optional<Method> taggedMethod = Arrays.stream(methods).filter(m -> javadoc
			.matches(m)).findAny(); //
		if (taggedMethod.isEmpty()) return null;
		Supplier<Map<String, ?>> tagOptions = () -> itemsFromTag(tagType, tag
			.get());
		return new Discovery<>(taggedMethod.get(), tagType, tagOptions);
	}

	/**
	 * Using a string {@code className}, finds a list of tagged methods
	 */
	private List<Discovery<AnnotatedElement>> methodsToDiscoveries(Map.Entry<ClassJavadoc, String> entry, String tagType ) {
			Method[] methods = extractDeclaredMethods(entry);

			// stream MethodJavadocs of the given ClassJavadoc
			return entry.getKey().getMethods().parallelStream() //
				.map(j -> mapMethodToDiscovery(j, tagType, methods)) //
				.filter(Objects::nonNull) //
				.collect(Collectors.toList());
		}

	private Method[] extractDeclaredMethods(Entry<ClassJavadoc, String> entry) {
		try {
			return getClass(entry.getValue()).getDeclaredMethods();
		}
		catch (ClassNotFoundException exc) {
			return new Method[0];
		}
	}

	private Field[] extractDeclaredFields(Entry<ClassJavadoc, String> entry) {
		try {
			return getClass(entry.getValue()).getDeclaredFields();
		}
		catch (ClassNotFoundException exc) {
			return new Field[0];
		}
	}

	private List<Discovery<AnnotatedElement>> discoverTaggedClasses(
		String tagType, Map<ClassJavadoc, String> javadocData)
	{
		return javadocData.entrySet().parallelStream() //
			.map(e -> classFinder.apply(e, tagType)) //
			.filter(c -> c != null) //
			.collect(Collectors.toList());
	}

	private final BiFunction<Map.Entry<ClassJavadoc, String>, String, Discovery<AnnotatedElement>> classFinder =
		(e, tagType) -> {
			try {
				Class<?> taggedClass = getClass(e.getValue());
				Optional<String> tag = getTag.apply(e.getKey(), tagType);
				if (tag.isEmpty()) return null;
			Supplier<Map<String, ?>> tagOptions = () -> itemsFromTag(tagType, e.getValue());
				return new Discovery<>(taggedClass, tagType, tagOptions);
			}
			catch (ClassNotFoundException exc) {
				return null;
			}

		};

	private List<Discovery<AnnotatedElement>> discoverTaggedFields(String tagType,
		Map<ClassJavadoc, String> javadocData)
	{
		return javadocData.entrySet().parallelStream() //
			.map(e -> fieldsToDiscoveries(e, tagType)) //
			.flatMap(list -> list.parallelStream()) //
			.collect(Collectors.toList());
	}

	private List<Discovery<AnnotatedElement>> discoverTaggedMethods(
		String tagType, Map<ClassJavadoc, String> javadocData)
	{
		return javadocData.entrySet().parallelStream() //
			.map(e -> methodsToDiscoveries(e, tagType)) //
			.flatMap(list -> list.parallelStream()) //
			.collect(Collectors.toList());
	}

}
