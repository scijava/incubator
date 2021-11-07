plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven("https://maven.scijava.org/content/groups/public")
}

dependencies {
    implementation("net.imagej:imagej-common:0.34.0")
    implementation("net.imagej:imagej-mesh:0.8.0")
    implementation("net.imglib2:imglib2:5.11.1")
    implementation("net.imglib2:imglib2-algorithm:0.11.2")
    implementation("net.imglib2:imglib2-algorithm-fft:0.2.0")
    implementation("net.imglib2:imglib2-roi:0.10.4")
    implementation("org.scijava:parsington:2.0.0")
    implementation("org.scijava:scijava-common:2.85.0")
    implementation("org.scijava:scijava-ops-api")
    implementation("org.scijava:scijava-ops-spi")
    implementation("org.scijava:scijava-ops-engine")
    implementation("org.scijava:scijava-function")
    implementation("org.scijava:scijava-testutil")
    implementation("org.scijava:scijava-types")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("org.joml:joml:1.9.25")
    implementation("org.ojalgo:ojalgo:45.1.1")
    implementation("gov.nist.math:jama:1.0.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.2")
    testImplementation("org.scijava:scijava-testutil")
    testImplementation("io.scif:scifio:0.41.1")
}