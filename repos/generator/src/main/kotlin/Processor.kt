package dev.inmo.micro_utils.repos.generator

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSClassifierReference
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSReferenceElement
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.symbol.Nullability
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import dev.inmo.micro_utils.repos.annotations.GenerateCRUDModel
import dev.inmo.micro_utils.repos.annotations.GenerateCRUDModelExcludeOverride
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

private fun KSClassifierReference.quilifiedName(): String = "${qualifier ?.let { "${it.quilifiedName()}." } ?: ""}${referencedName()}"

class Processor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    private val KSPropertyDeclaration.typeName: TypeName
        get() {
            return runCatching {
                type.toTypeName()
            }.getOrElse {
                val element = type.element as KSClassifierReference
                (type.element as KSClassifierReference).let {
                    ClassName(
                        element.qualifier ?.quilifiedName() ?: "",
                        element.referencedName()
                    )
                }
            }
        }
    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val toRetry = resolver.getSymbolsWithAnnotation(
            GenerateCRUDModel::class.qualifiedName!!
        ).filterIsInstance<KSClassDeclaration>().filterNot { ksClassDeclaration ->
            val ksFile = ksClassDeclaration.containingFile ?: return@filterNot false
            runCatching {
                FileSpec.builder(
                    ksClassDeclaration.packageName.asString(),
                    "GeneratedModels${ksFile.fileName.removeSuffix(".kt")}"
                ).apply {
                    val annotation = ksClassDeclaration.getAnnotationsByType(GenerateCRUDModel::class).first()
                    addFileComment(
                        """
                        THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
                        TO REGENERATE IT JUST DELETE FILE
                        ORIGINAL FILE: ${ksFile.fileName}
                    """.trimIndent()
                    )
                    val newName = "New${ksClassDeclaration.simpleName.getShortName()}"
                    val registeredName = "Registered${ksClassDeclaration.simpleName.getShortName()}"

                    val allKSClassProperties = ksClassDeclaration.getAllProperties()
                    val excludedKSClassProperties = allKSClassProperties.filter {
                        it.isAnnotationPresent(GenerateCRUDModelExcludeOverride::class)
                    }
                    val excludedKSClassPropertiesNames = excludedKSClassProperties.map { it.simpleName.asString() }
                    val ksClassProperties = allKSClassProperties.filter {
                        it !in excludedKSClassProperties
                    }
                    val ksClassPropertiesNames = ksClassProperties.map { it.simpleName.asString() }
                    val newNewType = TypeSpec.classBuilder(newName).apply {
                        val typeBuilder = this
                        addSuperinterface(ksClassDeclaration.toClassName())
                        addModifiers(KModifier.DATA)
                        if (annotation.serializable) {
                            addAnnotation(Serializable::class)
                            if (annotation.generateSerialName) {
                                addAnnotation(AnnotationSpec.get(SerialName(newName)))
                            }
                        }
                        primaryConstructor(
                            FunSpec.constructorBuilder().apply {
                                ksClassProperties.forEach {
                                    addParameter(
                                        ParameterSpec.builder(it.simpleName.getShortName(), it.typeName).apply {
                                            annotations += it.annotations.map { it.toAnnotationSpec() }
                                        }.build()
                                    )
                                    typeBuilder.addProperty(
                                        PropertySpec.builder(it.simpleName.getShortName(), it.typeName, KModifier.OVERRIDE).apply {
                                            initializer(it.simpleName.getShortName())
                                        }.build()
                                    )
                                }
                            }.build()
                        )
                    }.build()
                    addType(
                        newNewType
                    )

                    val registeredSupertypes = ksClassDeclaration.annotations.filter {
                        it.shortName.asString() == GenerateCRUDModel::class.simpleName &&
                            it.annotationType.resolve().declaration.qualifiedName ?.asString() == GenerateCRUDModel::class.qualifiedName
                    }.flatMap {
                        (it.arguments.first().value as List<KSType>).map { it.declaration as KSClassDeclaration }
                    }.toList()


                    val registeredTypesProperties: List<KSPropertyDeclaration> = registeredSupertypes.flatMap { registeredType ->
                        registeredType.getAllProperties()
                    }.filter {
                        it.simpleName.asString() !in excludedKSClassPropertiesNames && it.getAnnotationsByType(GenerateCRUDModelExcludeOverride::class).none()
                    }
                    val allProperties: List<KSPropertyDeclaration> = ksClassProperties.toList() + registeredTypesProperties
                    val propertiesToOverrideInRegistered = allProperties.distinctBy { it.simpleName.asString() }.sortedBy { property ->
                        val name = property.simpleName.asString()

                        ksClassPropertiesNames.indexOf(name).takeIf { it > -1 } ?.let {
                            it + allProperties.size
                        } ?: allProperties.indexOfFirst { it.simpleName.asString() == name }
                    }

                    val newRegisteredType = TypeSpec.classBuilder(registeredName).apply {
                        val typeBuilder = this
                        addSuperinterface(ksClassDeclaration.toClassName())
                        if (annotation.serializable) {
                            addAnnotation(Serializable::class)

                            if (annotation.generateSerialName) {
                                addAnnotation(
                                    AnnotationSpec.get(SerialName(registeredName))
                                )
                            }
                        }
                        addSuperinterfaces(registeredSupertypes.map { it.toClassName() })
                        addModifiers(KModifier.DATA)
                        primaryConstructor(
                            FunSpec.constructorBuilder().apply {
                                propertiesToOverrideInRegistered.forEach {
                                    addParameter(
                                        ParameterSpec.builder(it.simpleName.getShortName(), it.typeName).apply {
                                            annotations += it.annotations.map { it.toAnnotationSpec() }
                                        }.build()
                                    )
                                    typeBuilder.addProperty(
                                        PropertySpec.builder(it.simpleName.getShortName(), it.typeName, KModifier.OVERRIDE).apply {
                                            initializer(it.simpleName.getShortName())
                                        }.build()
                                    )
                                }
                            }.build()
                        )
                    }.build()
                    addType(
                        newRegisteredType
                    )

                    addFunction(
                        FunSpec.builder("asNew").apply {
                            receiver(ksClassDeclaration.toClassName())
                            addCode(
                                CodeBlock.of(
                                    "return ${newNewType.name}(${newNewType.propertySpecs.joinToString { it.name }})"
                                )
                            )
                            returns(ClassName(packageName, newNewType.name!!))
                        }.build()
                    )

                    addFunction(
                        FunSpec.builder("asRegistered").apply {
                            receiver(ksClassDeclaration.toClassName())
                            (registeredTypesProperties.filter { it.simpleName.asString() !in ksClassPropertiesNames }).forEach {
                                addParameter(it.simpleName.asString(), it.typeName)
                            }
                            addCode(
                                CodeBlock.of(
                                    "return ${newRegisteredType.name}(${newRegisteredType.propertySpecs.joinToString { it.name }})"
                                )
                            )
                            returns(ClassName(packageName, newRegisteredType.name!!))
                        }.build()
                    )
                }.build().let {
                    File(
                        File(ksFile.filePath).parent,
                        "GeneratedModels${ksFile.fileName}"
                    ).takeIf { !it.exists() } ?.apply {
                        parentFile.mkdirs()

                        writer().use { writer ->
                            it.writeTo(writer)
                        }
                    }
                }
            }.isSuccess
        }.toList()

        return toRetry
    }
}
