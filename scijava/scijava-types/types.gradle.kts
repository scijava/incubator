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
    implementation(project(":scijava-log2"))
    implementation("com.google.guava:guava:28.2-jre")
    testImplementation("junit:junit:4.13")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
}
