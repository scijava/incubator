package templates

import java.io.File

abstract class Globals(val sourceDir: File, val className: String) {

    val maxArity = 16
    val numConsumers = maxArity + 1

    fun inplaceSuffix(arity: Int, num: Int) = if(arity == 1) "1" else "${arity}_$num"

    fun simplifiedInplace(arity: Int, num: Int) = if(arity == 1) "Arity1" else "Arity${inplaceSuffix(arity, num)}"

    fun inplaceType(arity: Int, num: Int) = "Inplaces.${simplifiedInplace(arity, num)}"

    open fun computerArity(arity: Int) = "Computers.Arity$arity"

    open fun consumerArity(arity: Int) = when (arity) {
        0 -> "Consumer"
        1 -> "BiConsumer"
        else -> "Consumers.Arity${arity + 1}"
    }

    open fun functionArity(arity: Int) = when (arity) {
        0 -> "Producer"
        1 -> "Function"
        2 -> "BiFunction"
        else -> "Functions.Arity${arity}"
    }

    open fun genericParamTypes(arity: Int): List<String> = when (arity) {
        0 -> emptyList()
        1 -> listOf("I")
        else -> (1..arity).map { "I$it" }
    } + "O"

    open fun generics(arity: Int) = "<${genericParamTypes(arity).joinToString()}>"

    open fun genericsNamesList(arity: Int): List<String> =
        genericParamTypes(arity).map {
            when (it) {
                "O" -> "out"
                "I" -> "in"
                else -> "in${it.drop(1)}"
            }
        }

    open fun nilNames(arity: Int): List<String> = genericsNamesList(arity).map { "${it}Type" }

    open fun typeArgs(arity: Int) = nilNames(arity).joinToString { "$it.getType()" }

    open fun typeParamsList(arity: Int): List<String> {
        val gpt = genericParamTypes(arity)
        val names = genericsNamesList(arity)
        return (0..arity).map { "${gpt[it]} ${names[it]}" }
    }

    open fun typeParamsListWithoutOutput(arity: Int): List<String> {
        val gpt = genericParamTypes(arity)
        val names = genericsNamesList(arity)
        return (0 until arity).map { "${gpt[it]} ${names[it]}" }
    }

    open fun applyParams(arity: Int) = typeParamsList(arity).dropLast(1).joinToString()

    open fun applyArgs(arity: Int) = genericsNamesList(arity).dropLast(1).joinToString()

    open fun computeParams(arity: Int): String {
        val typeParams = typeParamsList(arity).toMutableList()
//        val (a, b) = typeParams[arity].split(':').map { it.trim() }
//        typeParams[arity] = "$a: @Container $b"
        typeParams[arity] = "@Container ${typeParams[arity]}"
        return typeParams.joinToString()
    }

    open fun acceptParams(arity: Int) = typeParamsList(arity).joinToString()

    open fun computeArgs(arity: Int) = genericsNamesList(arity).joinToString()

    open fun acceptArgs(arity: Int) = computeArgs(arity)

    fun build() {
        val dir = File(sourceDir, "org/scijava/function")
        dir.mkdirs()
        val file = File(dir, "$className.java")
        file.createNewFile()

        val text = StringBuilder()
        generate(text)

        file.writeText(text.toString())
    }

    abstract fun generate(text: StringBuilder)
}