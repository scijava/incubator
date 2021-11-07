plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven("https://maven.scijava.org/content/groups/public")
}

dependencies {
    implementation("org.scijava:scijava-common:2.85.0")
    implementation("net.imglib2:imglib2:5.11.1")
}