plugins {
    `java-library`
}

group = "org.scijava"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scijava:scijava-common:2.85.0")
    implementation(project(":scijava-discovery"))
    implementation(project(":scijava-ops-spi"))
    testImplementation("junit:junit:4.13")
}