plugins {
    `java-library`
}

group = "org.scijava"

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13")
    implementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
}