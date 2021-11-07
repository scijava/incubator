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
    implementation("org.scijava:scijava-common:2.85.0")
    implementation(project(":scijava-function"))
    implementation(project(":scijava-struct"))
    implementation(project(":scijava-types"))

    implementation(project(":scijava-ops-api-processor"))
    ksp(project(":scijava-ops-api-processor"))
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