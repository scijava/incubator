import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
//    implementation(kotlin("jvm"))
    implementation(kotlin("reflect"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.5.31-1.0.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}