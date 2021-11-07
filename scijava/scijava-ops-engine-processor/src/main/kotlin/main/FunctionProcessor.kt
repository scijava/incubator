package main

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import templates.adapt.complexLift.ComputersToFunctionsAndLift
import templates.adapt.complexLift.FunctionsToComputersAndLift
import templates.adapt.functional.ComputersToFunctionsViaFunction
import templates.adapt.functional.ComputersToFunctionsViaSource
import templates.adapt.functional.FunctionsToComputers
import templates.adapt.functional.InplacesToFunctions
import templates.adapt.lift.ComputerToArrays
import templates.adapt.lift.ComputerToIterables
import templates.adapt.lift.FunctionToArrays
import templates.matcher.impl.OpWrappers
import templates.util.FunctionUtils
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

            // adapt.complexLift
            ComputersToFunctionsAndLift(javaDir).build()
            FunctionsToComputersAndLift(javaDir).build()

            // adapt.functional
            ComputersToFunctionsViaFunction(javaDir).build()
            ComputersToFunctionsViaSource(javaDir).build()
            FunctionsToComputers(javaDir).build()
            InplacesToFunctions(javaDir).build()

            // adapt.lift
            ComputerToArrays(javaDir).build()
            ComputerToIterables(javaDir).build()
            FunctionToArrays(javaDir).build()

            // matcher.impl
            OpWrappers(javaDir).build()

            // util
            FunctionUtils(javaDir).build()
        }
        return emptyList()
    }
}