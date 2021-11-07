package templates

import java.io.File

abstract class Globals(val sourceDir: File, val className: String) {

    val maxArity = 16
    val numConsumers = maxArity+1

    fun inplaceSuffix(arity: Int, num: Int) = if(arity == 1) "1" else "${arity}_$num"
    
    fun simplifiedInplace(arity: Int, num: Int) = if(arity == 1) "Arity1" else "Arity${inplaceSuffix(arity, num)}"
    
    fun inplaceType(arity: Int, num: Int) = "Inplaces.${simplifiedInplace(arity, num)}"
    
    fun computerArity(arity: Int) = "Computers.Arity$arity"
    
    fun consumerArity(arity: Int) = when(arity) {
        0 -> "Consumer"
        1 -> "BiConsumer"
        else -> "Consumers.Arity${arity+1}"
    }
    
    fun functionArity(arity: Int) = when(arity) {
        0 -> "Producer"
        1 -> "Function"
        2 -> "BiFunction"
        else -> "Functions.Arity${arity}"
    }
    
    fun genericParamTypes(arity: Int) = when(arity) {
        0 -> listOf("O")
        1 -> listOf("I", "O")
        else -> (1..arity).map{"I$it"} + "O"
    }
    
    open fun generics(arity: Int) = "<${genericParamTypes(arity).joinToString()}>"
    
    fun genericsNamesList(arity: Int): List<String> = 
        genericParamTypes(arity).map {
            when (it) {
                "O" -> "out"
                "I" -> "in"
                else -> "in${it.drop(1)}"
            }
        }
            
    fun nilNames(arity: Int) = genericsNamesList(arity).map{ "${it}Type"}
            
    fun typeArgs(arity: Int) = nilNames(arity).joinToString{ "$it.getType()"}
    
    fun typeParamsList(arity: Int): List<String> {
        val gpt = genericParamTypes(arity)
        val names = genericsNamesList(arity)
        return (0..arity).map{"${gpt[it]} ${names[it]}"}
    }
    
    fun typeParamsListWithoutOutput(arity: Int) {
        val gpt = genericParamTypes(arity)
        val names = genericsNamesList(arity)
        (0 until arity).map{ "${gpt[it]} ${names[it]}"}
    }
    
    fun applyParams(arity: Int) = typeParamsList(arity).dropLast(1).joinToString()
    
    fun applyArgs(arity: Int) = genericsNamesList(arity).dropLast(1).joinToString()
    
    fun computeParams(arity: Int): String {
        val typeParams = typeParamsList(arity).toMutableList()
        typeParams[arity] = "@Container ${typeParams[arity]}"
        return typeParams.joinToString()
    }
    
    fun acceptParams(arity: Int) = typeParamsList(arity).joinToString{ "final $it" }
    
    fun computeArgs(arity: Int) = genericsNamesList(arity).joinToString()
    
    fun acceptArgs(arity: Int) = computeArgs(arity)

    fun build() {
        val dir = File(sourceDir, "org/scijava/ops/api")
        dir.mkdirs()
        val file = File(dir, "$className.java")
        file.createNewFile()

        val text = StringBuilder()
        generate(text)

        file.writeText(text.toString())
    }

    abstract fun generate(text: StringBuilder)
}