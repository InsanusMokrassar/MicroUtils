package dev.inmo.micro_utils.ksp.sealed.generator

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import dev.inmo.micro_ksp.generator.buildSubFileName
import dev.inmo.micro_ksp.generator.companion
import dev.inmo.micro_ksp.generator.findSubClasses
import dev.inmo.micro_ksp.generator.writeFile
import dev.inmo.microutils.kps.sealed.GenerateSealedWorkaround
import java.io.File

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
        val annotation = ksClassDeclaration.getAnnotationsByType(GenerateSealedWorkaround::class).first()
        val subClasses = ksClassDeclaration.resolveSubclasses(
            searchIn = resolver.getAllFiles(),
            allowNonSealed = annotation.includeNonSealedSubTypes
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
    override fun process(resolver: Resolver): List<KSAnnotated> {
        (resolver.getSymbolsWithAnnotation(GenerateSealedWorkaround::class.qualifiedName!!)).filterIsInstance<KSClassDeclaration>().forEach {
            val prefix = it.getAnnotationsByType(GenerateSealedWorkaround::class).first().prefix.takeIf {
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

        return emptyList()
    }
}
