package dev.inmo.micro_utils.ksp.variations.generator

import com.google.devtools.ksp.KSTypeNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toKModifier
import com.squareup.kotlinpoet.ksp.toTypeName
import dev.inmo.micro_ksp.generator.convertToClassName
import dev.inmo.micro_ksp.generator.convertToClassNames
import dev.inmo.micro_ksp.generator.findSubClasses
import dev.inmo.micro_ksp.generator.writeFile
import dev.inmo.micro_utils.ksp.variations.GenerateVariations
import dev.inmo.micro_utils.ksp.variations.GenerationVariant
import kotlin.math.pow

class Processor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    private fun KSClassDeclaration.findSealedConnection(potentialSealedParent: KSClassDeclaration): Boolean {
        val targetClassname = potentialSealedParent.qualifiedName ?.asString()
        return superTypes.any {
            val itAsDeclaration = it.resolve().declaration as? KSClassDeclaration ?: return@any false
            targetClassname == (itAsDeclaration.qualifiedName ?.asString()) || (itAsDeclaration.getSealedSubclasses().any() && itAsDeclaration.findSealedConnection(potentialSealedParent))
        }
    }

    private fun KSClassDeclaration.resolveSubclasses(
        searchIn: Sequence<KSAnnotated>,
        allowNonSealed: Boolean
    ): Sequence<KSClassDeclaration> {
        return findSubClasses(searchIn).let {
            if (allowNonSealed) {
                it
            } else {
                it.filter {
                    it.findSealedConnection(this)
                }
            }
        }
    }

    @OptIn(KspExperimental::class)
    private fun FileSpec.Builder.generateVariations(
        ksFunctionDeclaration: KSFunctionDeclaration,
        resolver: Resolver
    ) {
        val annotation = ksFunctionDeclaration.getAnnotationsByType(GenerateVariations::class).first()
        val variations: List<Pair<List<GenerationVariant>, KSValueParameter>> = ksFunctionDeclaration.parameters.mapNotNull {
            val variationAnnotations = it.getAnnotationsByType(GenerationVariant::class).toList()
            variationAnnotations to it
        }
        val accumulatedGenerations = mutableSetOf<Pair<FunSpec, Map<String, String>>>()
        val baseFunctionParameters = ksFunctionDeclaration.parameters.mapNotNull {
            ParameterSpec
                .builder(
                    it.name ?.asString() ?: return@mapNotNull null,
                    it.type.toTypeName(),
                )
                .apply {
                    if (it.isCrossInline) {
                        addModifiers(KModifier.CROSSINLINE)
                    }
                    if (it.isVal) {
                        addModifiers(KModifier.VALUE)
                    }
                    if (it.isNoInline) {
                        addModifiers(KModifier.NOINLINE)
                    }
                    if (it.isVararg) {
                        addModifiers(KModifier.VARARG)
                    }
                }
                .build() to it.hasDefault
        }
        val baseFunctionFunSpecs = mutableListOf<Pair<FunSpec, Map<String, String>>>()
        let {
            var defaultParametersIndicator = 0u
            val maxIndicator = baseFunctionParameters.filter { it.second }.foldIndexed(0u) { index, acc, _ ->
                2.0.pow(index).toUInt() + acc
            }
            while (defaultParametersIndicator <= maxIndicator) {
                var currentDefaultParameterIndex = 0u
                val baseFunctionDefaults = mutableMapOf<String, String>()
                val funSpec = FunSpec.builder(ksFunctionDeclaration.simpleName.asString()).apply {
                    modifiers.addAll(ksFunctionDeclaration.modifiers.mapNotNull { it.toKModifier() })
                    ksFunctionDeclaration.annotations.forEach {
                        addAnnotation(it.toAnnotationSpec(omitDefaultValues = false))
                    }
                    ksFunctionDeclaration.extensionReceiver ?.let {
                        receiver(it.toTypeName())
                    }
                }
                baseFunctionParameters.forEach { (parameter, hasDefault) ->
                    if (hasDefault) {
                        val shouldBeIncluded = (2.0.pow(currentDefaultParameterIndex.toInt()).toUInt()).and(defaultParametersIndicator) > 0u
                        currentDefaultParameterIndex++

                        if (!shouldBeIncluded) {
                            return@forEach
                        }
                    }
                    funSpec.addParameter(parameter)
                    val name = parameter.name
                    val defaultValueString = if (parameter.modifiers.contains(KModifier.VARARG)) {
                        "*$name"
                    } else {
                        "$name"
                    }
                    baseFunctionDefaults[parameter.name] = defaultValueString
                }
                baseFunctionFunSpecs.add(
                    funSpec.build() to baseFunctionDefaults.toMap()
                )
                defaultParametersIndicator++
            }
        }
        variations.forEach { (variations, parameter) ->
            (baseFunctionFunSpecs + accumulatedGenerations).forEach { (accumulatedGeneration, baseDefaults) ->
                if ((parameter.name ?.asString() ?: "this") !in baseDefaults.keys) {
                    return@forEach
                }
                variations.forEach { variation ->
                    val defaults = mutableMapOf<String, String>()
                    accumulatedGenerations.add(
                        FunSpec.builder(accumulatedGeneration.name).apply {
                            modifiers.addAll(accumulatedGeneration.modifiers)
                            accumulatedGeneration.annotations.forEach {
                                addAnnotation(it)
                            }
                            accumulatedGeneration.receiverType ?.let {
                                receiver(it)
                            }
                            accumulatedGeneration.parameters.forEach {
                                val actualName = if (variation.argName.isEmpty()) it.name else variation.argName
                                parameters.add(
                                    (if (it.name == (parameter.name ?.asString() ?: "this")) {
                                        val type = convertToClassName { variation.type }
                                        val genericTypes = convertToClassNames { variation.genericTypes.toList()  }
                                        ParameterSpec
                                            .builder(
                                                actualName,
                                                if (genericTypes.isEmpty()) {
                                                    type
                                                } else {
                                                    type.parameterizedBy(
                                                        *genericTypes.toTypedArray()
                                                    )
                                                }
                                            )
                                            .apply {
                                                addModifiers(it.modifiers)
                                                val defaultValueString = """
                                                    with(${actualName}) {${
                                                            if (it.modifiers.contains(KModifier.VARARG)) {
                                                                "map { it.${variation.conversion} }.toTypedArray()"
                                                            } else {
                                                                "${variation.conversion}"
                                                            }
                                                    }}
                                                """.trimIndent()
                                                defaults[it.name] = defaultValueString
                                            }
                                    } else {
                                        it.toBuilder()
                                    })
                                        .build()
                                )
                            }
                            val parameters = accumulatedGeneration.parameters.joinToString(", ") {
                                val itName = it.name
                                """
                                    $itName = ${defaults[itName] ?: baseDefaults[itName] ?: itName}
                                """.trimIndent()
                            }
                            addCode(
                                """
                                    return ${ksFunctionDeclaration.simpleName.asString()}(
                                        $parameters
                                    )
                                """.trimIndent()
                            )
                        }.build() to defaults.toMap()
                    )
                }
            }
        }
        accumulatedGenerations.forEach {
            addFunction(it.first)
        }
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        (resolver.getSymbolsWithAnnotation(GenerateVariations::class.qualifiedName!!)).filterIsInstance<KSFunctionDeclaration>().forEach {
            val prefix = (it.getAnnotationsByType(GenerateVariations::class)).firstOrNull() ?.prefix ?.takeIf {
                it.isNotEmpty()
            } ?: it.simpleName.asString().replaceFirst(it.simpleName.asString(), "")
            it.writeFile(prefix = prefix, suffix = "GeneratedVariation") {
                FileSpec.builder(
                    it.packageName.asString(),
                    "${it.simpleName.getShortName()}GeneratedVariation"
                ).apply {
                    addFileComment(
                        """
                        THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
                        TO REGENERATE IT JUST DELETE FILE
                        ORIGINAL FILE: ${it.containingFile ?.fileName}
                        """.trimIndent()
                    )
                    generateVariations(it, resolver)
                }.build()
            }
        }

        return emptyList()
    }
}
