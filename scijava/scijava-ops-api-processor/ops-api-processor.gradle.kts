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