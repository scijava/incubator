package main

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import templates.OpBuilder
import java.io.File
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

var processed = false

class FunctionProcessor(private val codeGenerator: CodeGenerator,
                        private val logger: KSPLogger,
                        private val options: Map<String, String>) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {

        if (!processed) {

            processed = true
            //        val project = codeGenerator::class.memberProperties.first { it.name == "projectBase" }.apply { isAccessible = true }
            val javaDir = codeGenerator::class.memberProperties.first { it.name == "javaDir" }
                .apply { isAccessible = true }.getter.call(codeGenerator) as File
            //        val file = project.getter.call(codeGenerator) as File
            OpBuilder(javaDir).build()
        }
        return emptyList()
    }
}