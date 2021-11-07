plugins {
    `java-library`
}

group = "org.scijava"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scijava:scijava-common:2.85.0")
    implementation("org.scijava:parsington:2.0.0")
    testImplementation("junit:junit:4.13")
}