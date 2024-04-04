package dev.inmo.micro_utils.repos.generator

import com.google.devtools.ksp.*
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
import kotlin.reflect.jvm.jvmName

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
                    val excludedKSClassProperties = allKSClassProperties.filter { property ->
                        property.isAnnotationPresent(GenerateCRUDModelExcludeOverride::class) || (property.findOverridee() ?.isAnnotationPresent(GenerateCRUDModelExcludeOverride::class) == true)
                    }
                    val excludedKSClassPropertiesNames = excludedKSClassProperties.map { it.simpleName.asString() }
                    val ksClassProperties = allKSClassProperties.filter {
                        it !in excludedKSClassProperties
                    }.groupBy { it.simpleName.asString() }.map {
                        var current = it.value.first()
                        var currentType = current.type.resolve()
                        it.value.forEach {
                            val type = it.type.resolve()

                            if (currentType.isAssignableFrom(type) && !type.isAssignableFrom(currentType)) {
                                current = it
                                currentType = type
                            }
                        }
                        current
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
                                val withoutDefaults = mutableListOf<Pair<ParameterSpec.Builder, PropertySpec.Builder>>()
                                ksClassProperties.forEach {
                                    val property = PropertySpec.builder(it.simpleName.getShortName(), it.type.toTypeName(), KModifier.OVERRIDE).apply {
                                        initializer(it.simpleName.getShortName())
                                    }
                                    ParameterSpec.builder(it.simpleName.getShortName(), it.type.toTypeName()).apply {
                                        withoutDefaults.add(this to property)
                                        annotations += it.annotations.map { it.toAnnotationSpec() }
                                    }
                                }

                                withoutDefaults.forEach {
                                    addParameter(it.first.build())
                                    addProperty(it.second.build())
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
                        it.simpleName.asString() !in excludedKSClassPropertiesNames && !it.isAnnotationPresent(GenerateCRUDModelExcludeOverride::class)
                    }
                    val allProperties: List<KSPropertyDeclaration> = registeredTypesProperties + ksClassProperties.toList()
                    val propertiesToOverrideInRegistered = allProperties.groupBy { it.simpleName.asString() }.map {
                        var current = it.value.first()
                        var currentType = current.type.resolve()
                        it.value.forEach {
                            val type = it.type.resolve()

                            if (currentType.isAssignableFrom(type) && !type.isAssignableFrom(currentType)) {
                                current = it
                                currentType = type
                            }
                        }
                        current
                    }.sortedBy { property ->
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
                                val withoutDefaults = mutableListOf<Pair<ParameterSpec.Builder, PropertySpec.Builder>>()
                                propertiesToOverrideInRegistered.forEach {
                                    val property = PropertySpec.builder(it.simpleName.getShortName(), it.type.toTypeName(), KModifier.OVERRIDE).apply {
                                        initializer(it.simpleName.getShortName())
                                    }
                                    ParameterSpec.builder(it.simpleName.getShortName(), it.type.toTypeName()).apply {
                                        withoutDefaults.add(this to property)
                                        annotations += it.annotations.map { it.toAnnotationSpec() }
                                    }
                                }

                                withoutDefaults.forEach {
                                    addParameter(it.first.build())
                                    addProperty(it.second.build())
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
                                addParameter(it.simpleName.asString(), it.type.toTypeName())
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
            }.onFailure {
                File("/home/aleksey/projects/own/MicroUtils/repos/generator/test/build/output.txt").writeText(it.stackTraceToString())
            }.isSuccess
        }.toList()

        return toRetry
    }
}
