rootProject.name = "scijava"

file(".").list()?.forEach {
    if (it.startsWith("scijava-"))
        include(it)
}

rootProject.children.forEach {
    val name = it.name.substringAfter("scijava-")
//    it.name =  name
    it.buildFileName = "$name.gradle.kts"
}

//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

gradle.rootProject {
    group = "org.scijava"
}

//includeBuild("../../therapi-runtime-javadoc")