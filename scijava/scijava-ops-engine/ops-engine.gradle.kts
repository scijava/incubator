plugins {
    `java-library`
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.5.31-1.0.0"
}

group = "org.scijava"

repositories {
    mavenCentral()
    maven("https://maven.scijava.org/content/repositories/releases")
    maven("https://artifacts.unidata.ucar.edu/content/repositories/unidata-releases")
}

dependencies {
    implementation("net.imagej:imagej-common:0.34.0")
    implementation(project(":scijava-ops-api"))
    implementation(project(":scijava-ops-spi"))
    implementation(project(":scijava-discovery"))
    implementation(project(":scijava-discovery-therapi"))
    implementation(project(":scijava-log2"))
    implementation(project(":scijava-parse2"))
    implementation(project(":scijava-progress"))
    implementation(project(":scijava-struct"))
    implementation(project(":scijava-types"))
    implementation(project(":scijava-function"))
    implementation("com.google.guava:guava:28.2-jre")
    implementation("org.javassist:javassist:3.27.0-GA")

    // Annotation processor (prior to Gradle 4.6, use `compileOnly` instead)
    annotationProcessor("com.github.therapi:therapi-runtime-javadoc-scribe:0.12.0")

    // Runtime library
    implementation("com.github.therapi:therapi-runtime-javadoc:0.12.0")

    testImplementation("junit:junit:4.13")

//    implementation(project(":scijava-ops-engine-processor"))
    ksp(project(":scijava-ops-engine-processor"))
}

//kotlin.sourceSets {
//    main {
//        kotlin.srcDir("build/generated/ksp/main/kotlin")
//    }
//    test {
//        kotlin.srcDir("build/generated/ksp/test/kotlin")
//    }
//}
sourceSets.main {
    java.srcDir("build/generated/ksp/main/java")
}

//tasks {
//    withType<JavaCompile> {
//        sourceCompatibility = "11"
//        targetCompatibility = "11"
//    }
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions {
//            jvmTarget = "11"
//        }
//    }
//}

tasks {
    compileJava {
        options.compilerArgs.addAll(listOf("--module-path", classpath.asPath))
    }
}
