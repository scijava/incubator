rootProject.name = "imagej"

includeBuild("../scijava")

file(".").list()?.forEach {
    if (it.startsWith("imagej-"))
        include(it)
}

rootProject.children.forEach {
    it.buildFileName = "${it.name.substringAfter("imagej-")}.gradle.kts"
}

//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

gradle.rootProject {
    group = "net.imagej"
}