package dev.inmo.micro_utils.ksp.sealed.generator

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import dev.inmo.micro_ksp.generator.buildSubFileName
import dev.inmo.micro_ksp.generator.companion
import dev.inmo.micro_ksp.generator.findSubClasses
import dev.inmo.micro_ksp.generator.writeFile
import dev.inmo.micro_utils.ksp.sealed.GenerateSealedTypesWorkaround
import dev.inmo.micro_utils.ksp.sealed.GenerateSealedWorkaround
import java.io.File
import kotlin.reflect.KClass

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
    private fun FileSpec.Builder.generateSealedWorkaround(
        ksClassDeclaration: KSClassDeclaration,
        resolver: Resolver
    ) {
        val annotation = ksClassDeclaration.getGenerateSealedWorkaroundAnnotation
        val subClasses = ksClassDeclaration.resolveSubclasses(
            searchIn = resolver.getAllFiles(),
            allowNonSealed = annotation ?.includeNonSealedSubTypes ?: false
        ).distinct()
        val subClassesNames = subClasses.filter {
            when (it.classKind) {
                ClassKind.ENUM_ENTRY,
                ClassKind.OBJECT -> true
                ClassKind.INTERFACE,
                ClassKind.CLASS,
                ClassKind.ENUM_CLASS,
                ClassKind.ANNOTATION_CLASS -> false
            }
        }.filter {
            it.getAnnotationsByType(GenerateSealedWorkaround.Exclude::class).count() == 0
        }.sortedBy {
            (it.getAnnotationsByType(GenerateSealedWorkaround.Order::class).firstOrNull()) ?.order ?: 0
        }.map {
            it.toClassName()
        }.toList()
        val className = ksClassDeclaration.toClassName()
        val setType = Set::class.asTypeName().parameterizedBy(
            ksClassDeclaration.toClassName()
        )
        addProperty(
            PropertySpec.builder(
                "values",
                setType
            ).apply {
                modifiers.add(
                    KModifier.PRIVATE
                )
                initializer(
                    CodeBlock.of(
                        """setOf(${subClassesNames.joinToString(",\n") {it.simpleNames.joinToString(".")}})"""
                    )
                )
            }.build()
        )
        addFunction(
            FunSpec.builder("values").apply {
                val companion = ksClassDeclaration.takeIf { it.isCompanionObject } ?.toClassName()
                    ?: ksClassDeclaration.companion ?.toClassName()
                    ?: ClassName(className.packageName, *className.simpleNames.toTypedArray(), "Companion")
                receiver(companion)
                returns(setType)
                addCode(
                    CodeBlock.of(
                        """return values"""
                    )
                )
            }.build()
        )
    }

    @OptIn(KspExperimental::class)
    private fun FileSpec.Builder.generateSealedTypesWorkaround(
        ksClassDeclaration: KSClassDeclaration,
        resolver: Resolver
    ) {
        val annotation = ksClassDeclaration.getGenerateSealedTypesWorkaroundAnnotation
        val subClasses = ksClassDeclaration.resolveSubclasses(
            searchIn = resolver.getAllFiles(),
            allowNonSealed = annotation ?.includeNonSealedSubTypes ?: false
        ).distinct()
        val subClassesNames = subClasses.filter {
            it.getAnnotationsByType(GenerateSealedWorkaround.Exclude::class).count() == 0
        }.sortedBy {
            (it.getAnnotationsByType(GenerateSealedWorkaround.Order::class).firstOrNull()) ?.order ?: 0
        }.map {
            it.toClassName()
        }.toList()
        val className = ksClassDeclaration.toClassName()
        val setType = Set::class.asTypeName().parameterizedBy(
            KClass::class.asTypeName().parameterizedBy(
                TypeVariableName(
                    "out ${ksClassDeclaration.asStarProjectedType().toClassName().simpleNames.joinToString(".")}",
                )
            )
        )
        addProperty(
            PropertySpec.builder(
                "subtypes",
                setType
            ).apply {
                modifiers.add(
                    KModifier.PRIVATE
                )
                initializer(
                    CodeBlock.of(
                        """setOf(${subClassesNames.joinToString(",\n") { it.simpleNames.joinToString(".") + "::class" }})"""
                    )
                )
            }.build()
        )
        addFunction(
            FunSpec.builder("subtypes").apply {
                val companion = ksClassDeclaration.takeIf { it.isCompanionObject } ?.toClassName()
                    ?: ksClassDeclaration.companion ?.toClassName()
                    ?: ClassName(className.packageName, *className.simpleNames.toTypedArray(), "Companion")
                receiver(companion)
                returns(setType)
                addCode(
                    CodeBlock.of(
                        """return subtypes"""
                    )
                )
            }.build()
        )
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        (resolver.getSymbolsWithAnnotation(GenerateSealedWorkaround::class.qualifiedName!!)).filterIsInstance<KSClassDeclaration>().forEach {
            val prefix = (it.getGenerateSealedWorkaroundAnnotation) ?.prefix ?.takeIf {
                it.isNotEmpty()
            } ?: it.buildSubFileName.replaceFirst(it.simpleName.asString(), "")
            it.writeFile(prefix = prefix, suffix = "SealedWorkaround") {
                FileSpec.builder(
                    it.packageName.asString(),
                    "${it.simpleName.getShortName()}SealedWorkaround"
                ).apply {
                    addFileComment(
                        """
                        THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
                        TO REGENERATE IT JUST DELETE FILE
                        ORIGINAL FILE: ${it.containingFile ?.fileName}
                        """.trimIndent()
                    )
                    generateSealedWorkaround(it, resolver)
                }.build()
            }
        }
        (resolver.getSymbolsWithAnnotation(GenerateSealedTypesWorkaround::class.qualifiedName!!)).filterIsInstance<KSClassDeclaration>().forEach {
            val prefix = (it.getGenerateSealedTypesWorkaroundAnnotation) ?.prefix ?.takeIf {
                it.isNotEmpty()
            } ?: it.buildSubFileName.replaceFirst(it.simpleName.asString(), "")
            it.writeFile(prefix = prefix, suffix = "SealedTypesWorkaround") {
                FileSpec.builder(
                    it.packageName.asString(),
                    "${it.simpleName.getShortName()}SealedTypesWorkaround"
                ).apply {
                    addFileComment(
                        """
                        THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
                        TO REGENERATE IT JUST DELETE FILE
                        ORIGINAL FILE: ${it.containingFile ?.fileName}
                        """.trimIndent()
                    )
                    generateSealedTypesWorkaround(it, resolver)
                }.build()
            }
        }

        return emptyList()
    }
}
