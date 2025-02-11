package dev.inmo.micro_utils.ksp.variations.generator

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toKModifier
import com.squareup.kotlinpoet.ksp.toTypeName
import dev.inmo.micro_ksp.generator.companion
import dev.inmo.micro_ksp.generator.findSubClasses
import dev.inmo.micro_ksp.generator.writeFile
import dev.inmo.micro_utils.ksp.variations.GenerateVariations
import dev.inmo.micro_utils.ksp.variations.GenerationVariant

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
            val variationAnnotations = it.getAnnotationsByType(GenerationVariant::class).toList().ifEmpty { return@mapNotNull null }
            variationAnnotations to it
        }
        val accumulatedGenerations = mutableSetOf<FunSpec>()
        variations.forEach { (variations, parameter) ->
            if (accumulatedGenerations.isEmpty()) {
                variations.forEach { variation ->
                    accumulatedGenerations.add(
                        FunSpec.builder(ksFunctionDeclaration.simpleName.asString()).apply {
                            modifiers.addAll(ksFunctionDeclaration.modifiers.mapNotNull { it.toKModifier() })
                            ksFunctionDeclaration.parameters.forEach {
                                parameters.add(
                                    (if (it == parameter) {
                                        ParameterSpec
                                            .builder(
                                                variation.argName,
                                                if (variation.varargTypes.isEmpty()) {
                                                    variation.type.asTypeName()
                                                } else {
                                                    variation.type.parameterizedBy(*variation.varargTypes)
                                                }
                                            )
                                            .apply {
                                                val name = it.name ?.asString() ?: "this"
                                                if (it.isVararg) {
                                                    defaultValue(
                                                        """
                                                            *$name.map { it.${variation.conversion} }.toTypedArray()
                                                        """.trimIndent()
                                                    )
                                                } else {
                                                    defaultValue("$name.${variation.conversion}")
                                                }
                                            }
                                    } else {
                                        ParameterSpec
                                            .builder(
                                                it.name ?.asString() ?: return@forEach,
                                                it.type.toTypeName(),
                                            )
                                    })
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
                                        .build()
                                )
                            }
                        }.build()
                    )
                }
            } else {

            }
        }
        accumulatedGenerations.forEach {
            addFunction(it)
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
