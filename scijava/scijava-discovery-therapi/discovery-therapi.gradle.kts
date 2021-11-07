plugins {
    `java-library`
}

group = "org.scijava"

repositories {
    mavenCentral()
}

dependencies {
    // Annotation processor (prior to Gradle 4.6, use `compileOnly` instead)
    annotationProcessor("com.github.therapi:therapi-runtime-javadoc-scribe:0.12.0")

    // Runtime library
    implementation("com.github.therapi:therapi-runtime-javadoc:0.12.0")

    implementation(project(":scijava-discovery"))
    implementation(project(":scijava-parse2"))
    implementation("org.scijava:scijava-common:2.85.0")
    testImplementation("junit:junit:4.13")
}

tasks {
    compileJava {
        options.compilerArgs.addAll(listOf("--module-path", classpath.asPath))
    }
}