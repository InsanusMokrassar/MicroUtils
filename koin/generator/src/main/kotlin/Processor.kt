package dev.inmo.micro_utils.koin.generator

import com.google.devtools.ksp.KSTypeNotPresentException
import com.google.devtools.ksp.KSTypesNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import dev.inmo.micro_ksp.generator.safeClassName
import dev.inmo.micro_utils.koin.annotations.GenerateGenericKoinDefinition
import dev.inmo.micro_utils.koin.annotations.GenerateKoinDefinition
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.scope.Scope
import java.io.File
import kotlin.reflect.KClass

class Processor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    private val definitionClassName = ClassName("org.koin.core.definition", "Definition")
    private val koinDefinitionClassName = ClassName("org.koin.core.definition", "KoinDefinition")

    private fun FileSpec.Builder.addCodeForType(
        targetType: TypeName,
        name: String,
        nullable: Boolean,
        generateSingle: Boolean,
        generateFactory: Boolean,
    ) {
        val targetTypeAsGenericType = (targetType as? TypeVariableName) ?.copy(reified = true)

        fun addGetterProperty(
            receiver: KClass<*>
        ) {
            addProperty(
                PropertySpec.builder(
                    name,
                    targetType,
                ).apply {
                    addKdoc(
                        """
                            @return Definition by key "${name}"
                        """.trimIndent()
                    )
                    getter(
                        FunSpec.getterBuilder().apply {
                            targetTypeAsGenericType ?.let {
                                addModifiers(KModifier.INLINE)
                            }
                            addCode(
                                "return " + (if (nullable) {
                                    "getOrNull"
                                } else {
                                    "get"
                                }) + "(named(\"${name}\"))"
                            )
                        }.build()
                    )
                    targetTypeAsGenericType ?.let {
                        addTypeVariable(it)
                    }
                    receiver(receiver)
                }.build()
            )
        }

        if (targetTypeAsGenericType == null) {
            addGetterProperty(Scope::class)
            addGetterProperty(Koin::class)
        }

        val parametersDefinitionClassName = ClassName(
            "org.koin.core.parameter",
            "ParametersDefinition"
        )
        fun addGetterMethod(
            receiver: KClass<*>
        ) {
            addFunction(
                FunSpec.builder(
                    name
                ).apply {
                    addKdoc(
                        """
                            @return Definition by key "${name}" with [parameters]
                        """.trimIndent()
                    )
                    receiver(receiver)
                    addParameter(
                        ParameterSpec(
                            "parameters",
                            parametersDefinitionClassName.let {
                                if (targetTypeAsGenericType != null) {
                                    it.copy(nullable = true)
                                } else {
                                    it
                                }
                            },
                            KModifier.NOINLINE
                        ).toBuilder().apply {
                            if (targetTypeAsGenericType != null) {
                                defaultValue("null")
                            }
                        }.build()
                    )
                    addModifiers(KModifier.INLINE)
                    targetTypeAsGenericType ?.let {
                        addTypeVariable(it)
                        returns(it.copy(nullable = nullable))
                    } ?: returns(targetType)
                    addCode(
                        "return " + (if (nullable) {
                            "getOrNull"
                        } else {
                            "get"
                        }) + "(named(\"${name}\"), parameters)"
                    )
                }.build()
            )
        }

        addGetterMethod(Scope::class)
        addGetterMethod(Koin::class)

        fun FunSpec.Builder.addDefinitionParameter() {
            val definitionModifiers = if (targetTypeAsGenericType == null) {
                arrayOf()
            } else {
                arrayOf(KModifier.NOINLINE)
            }
            addParameter(
                ParameterSpec.builder(
                    "definition",
                    definitionClassName.parameterizedBy(targetType.copy(nullable = false)),
                    *definitionModifiers
                ).build()
            )
        }

        if (generateSingle) {
            fun FunSpec.Builder.configure() {
                addKdoc(
                    """
                        Will register [definition] with [org.koin.core.module.Module.single] and key "${name}"
                    """.trimIndent()
                )
                receiver(Module::class)
                addParameter(
                    ParameterSpec.builder(
                        "createdAtStart",
                        Boolean::class
                    ).apply {
                        defaultValue("false")
                    }.build()
                )
                addDefinitionParameter()
                returns(koinDefinitionClassName.parameterizedBy(targetType.copy(nullable = false)))
                addCode(
                    "return single(named(\"${name}\"), createdAtStart = createdAtStart, definition = definition)"
                )
                targetTypeAsGenericType ?.let {
                    addTypeVariable(it)
                    addModifiers(KModifier.INLINE)
                }
            }

            val actualSingleName = "single${name.replaceFirstChar { it.uppercase() }}"

            addFunction(
                FunSpec.builder(actualSingleName).apply { configure() }.build()
            )
        }

        if (generateFactory) {
            fun FunSpec.Builder.configure() {
                addKdoc(
                    """
                        Will register [definition] with [org.koin.core.module.Module.factory] and key "${name}"
                    """.trimIndent()
                )
                receiver(Module::class)
                addDefinitionParameter()
                returns(koinDefinitionClassName.parameterizedBy(targetType.copy(nullable = false)))
                addCode(
                    "return factory(named(\"${name}\"), definition = definition)"
                )
                targetTypeAsGenericType ?.let {
                    addTypeVariable(it)
                    addModifiers(KModifier.INLINE)
                }
            }
            val actualFactoryName = "factory${name.replaceFirstChar { it.uppercase() }}"
            addFunction(
                FunSpec.builder(actualFactoryName).apply { configure() }.build()
            )
        }
        addImport("org.koin.core.qualifier", "named")
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        (resolver.getSymbolsWithAnnotation(
            GenerateKoinDefinition::class.qualifiedName!!
        ) + resolver.getSymbolsWithAnnotation(
            GenerateGenericKoinDefinition::class.qualifiedName!!
        )).filterIsInstance<KSFile>().forEach { ksFile ->
            FileSpec.builder(
                ksFile.packageName.asString(),
                "GeneratedDefinitions${ksFile.fileName.removeSuffix(".kt")}"
            ).apply {
                addFileComment(
                    """
                        THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
                        TO REGENERATE IT JUST DELETE FILE
                        ORIGINAL FILE: ${ksFile.fileName}
                    """.trimIndent()
                )
                ksFile.getAnnotationsByType(GenerateKoinDefinition::class).forEach {
                    val type = safeClassName { it.type }
                    val targetType = runCatching {
                        type.parameterizedBy(*(it.typeArgs.takeIf { it.isNotEmpty() } ?.map { it.asTypeName() } ?.toTypedArray() ?: return@runCatching type))
                    }.getOrElse { e ->
                        when (e) {
                            is KSTypeNotPresentException -> e.ksType.toClassName()
                        }
                        if (e is KSTypesNotPresentException) {
                            type.parameterizedBy(*e.ksTypes.map { it.toTypeName() }.toTypedArray())
                        } else {
                            throw e
                        }
                    }.copy(
                        nullable = it.nullable
                    )

                    addCodeForType(targetType, it.name, it.nullable, it.generateSingle, it.generateFactory)
                }
                ksFile.getAnnotationsByType(GenerateGenericKoinDefinition::class).forEach {
                    val targetType = TypeVariableName("T", Any::class)
                    addCodeForType(targetType, it.name, it.nullable, it.generateSingle, it.generateFactory)
                }
            }.build().let {
                File(
                    File(ksFile.filePath).parent,
                    "GeneratedDefinitions${ksFile.fileName}"
                ).takeIf { !it.exists() } ?.apply {
                    parentFile.mkdirs()

                    writer().use { writer ->
                        it.writeTo(writer)
                    }
                }
            }
        }

        return emptyList()
    }
}
