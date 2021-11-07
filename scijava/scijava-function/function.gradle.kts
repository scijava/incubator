import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.5.31-1.0.0"
}

group = "org.scijava"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":scijava-function-processor"))
    ksp(project(":scijava-function-processor"))
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

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}