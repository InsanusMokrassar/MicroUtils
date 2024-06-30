package dev.inmo.micro_utils.ksp.classcasts.generator

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import dev.inmo.micro_ksp.generator.writeFile
import dev.inmo.micro_utils.ksp.classcasts.ClassCastsExcluded
import dev.inmo.micro_utils.ksp.classcasts.ClassCastsIncluded

class Processor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    private val classCastsIncludedClassName = ClassCastsIncluded::class.asClassName()

    @OptIn(KspExperimental::class)
    private fun FileSpec.Builder.generateClassCasts(
        ksClassDeclaration: KSClassDeclaration,
        resolver: Resolver
    ) {
        val classes = resolver.getSymbolsWithAnnotation(classCastsIncludedClassName.canonicalName).filterIsInstance<KSClassDeclaration>()
        val classesRegexes: Map<KSClassDeclaration, Pair<Regex?, Regex?>> = classes.mapNotNull {
            it to (it.getAnnotationsByType(ClassCastsIncluded::class).firstNotNullOfOrNull {
                it.typesRegex.takeIf { it.isNotEmpty() } ?.let(::Regex) to it.excludeRegex.takeIf { it.isNotEmpty() } ?.let(::Regex)
            } ?: return@mapNotNull null)
        }.toMap()
        val classesSubtypes = mutableMapOf<KSClassDeclaration, MutableSet<KSClassDeclaration>>()

        resolver.getAllFiles().forEach {
            it.declarations.forEach { potentialSubtype ->
                if (
                    potentialSubtype is KSClassDeclaration
                    && potentialSubtype.isAnnotationPresent(ClassCastsExcluded::class).not()
                ) {
                    val allSupertypes = potentialSubtype.getAllSuperTypes().map { it.declaration }

                    for (currentClass in classes) {
                        val regexes = classesRegexes[currentClass]
                        val simpleName = potentialSubtype.simpleName.getShortName()
                        when {
                            currentClass !in allSupertypes
                                    || regexes ?.first ?.matches(simpleName) == false
                                    || regexes ?.second ?.matches(simpleName) == true -> continue
                            else -> {
                                classesSubtypes.getOrPut(currentClass) { mutableSetOf() }.add(potentialSubtype)
                            }
                        }
                    }
                }
            }
        }
        fun fillWithSealeds(source: KSClassDeclaration, current: KSClassDeclaration = source) {
            val regexes = classesRegexes[source]
            current.getSealedSubclasses().forEach {
                val simpleName = it.simpleName.getShortName()
                if (
                    regexes ?.first ?.matches(simpleName) == false
                    || regexes ?.second ?.matches(simpleName) == true
                    || it.isAnnotationPresent(ClassCastsExcluded::class)
                ) {
                    return@forEach
                }
                classesSubtypes.getOrPut(source) { mutableSetOf() }.add(it)
                fillWithSealeds(source, it)
            }
        }
        classes.forEach { fillWithSealeds(it) }

        addAnnotation(
            AnnotationSpec.builder(Suppress::class).apply {
                addMember("\"unused\"")
                addMember("\"RemoveRedundantQualifierName\"")
                addMember("\"RedundantVisibilityModifier\"")
                addMember("\"NOTHING_TO_INLINE\"")
                addMember("\"UNCHECKED_CAST\"")
                addMember("\"OPT_IN_USAGE\"")
                useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
            }.build()
        )
        classes.forEach {
            fill(
                it,
                classesSubtypes.toMap()
            )
        }
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        (resolver.getSymbolsWithAnnotation(ClassCastsIncluded::class.qualifiedName!!)).filterIsInstance<KSClassDeclaration>().forEach {
            val prefix = it.getAnnotationsByType(ClassCastsIncluded::class).first().outputFilePrefix
            it.writeFile(prefix = prefix, suffix = "ClassCasts") {
                FileSpec.builder(
                    it.packageName.asString(),
                    "${it.simpleName.getShortName()}ClassCasts"
                ).apply {
                    addFileComment(
                        """
                        THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
                        TO REGENERATE IT JUST DELETE FILE
                        ORIGINAL FILE: ${it.containingFile ?.fileName}
                        """.trimIndent()
                    )
                    generateClassCasts(it, resolver)
                }.build()
            }
        }

        return emptyList()
    }
}
