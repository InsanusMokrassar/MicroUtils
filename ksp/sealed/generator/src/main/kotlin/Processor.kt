package dev.inmo.micro_utils.ksp.sealed.generator

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import dev.inmo.micro_ksp.generator.writeFile
import dev.inmo.microutils.kps.sealed.GenerateSealedWorkaround

class Processor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    private fun KSClassDeclaration.resolveSubclasses(): List<KSClassDeclaration> {
        return (getSealedSubclasses().flatMap {
            it.resolveSubclasses()
        }.ifEmpty {
            sequenceOf(this)
        }).toList()
    }

    @OptIn(KspExperimental::class)
    private fun FileSpec.Builder.generateSealedWorkaround(
        ksClassDeclaration: KSClassDeclaration,
        resolver: Resolver
    ) {
        val subClasses = ksClassDeclaration.resolveSubclasses().distinct()
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
        }
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
                receiver(ClassName(className.packageName, *className.simpleNames.toTypedArray(), "Companion"))
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
            val prefix = it.getAnnotationsByType(GenerateSealedWorkaround::class).first().prefix
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
