plugins {
    `java-library`
}

group = "org.scijava"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
}