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
import com.squareup.kotlinpoet.ksp.toClassName
import dev.inmo.micro_ksp.generator.writeFile
import dev.inmo.micro_utils.ksp.classcasts.ClassCastsExcluded
import dev.inmo.micro_utils.ksp.classcasts.ClassCastsIncluded
import java.io.File

class Processor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    @OptIn(KspExperimental::class)
    private fun FileSpec.Builder.generateClassCasts(
        ksClassDeclaration: KSClassDeclaration,
        resolver: Resolver
    ) {
        val rootAnnotation = ksClassDeclaration.getAnnotationsByType(ClassCastsIncluded::class).first()
        val (includeRegex: Regex?, excludeRegex: Regex?) = rootAnnotation.let {
            it.typesRegex.takeIf { it.isNotEmpty() } ?.let(::Regex) to it.excludeRegex.takeIf { it.isNotEmpty() } ?.let(::Regex)
        }
        val classesSubtypes = mutableMapOf<KSClassDeclaration, MutableSet<KSClassDeclaration>>()

        fun KSClassDeclaration.checkSupertypeLevel(levelsAllowed: Int?): Boolean {
            val supertypes by lazy {
                superTypes.map { it.resolve().declaration }
            }
            return when {
                levelsAllowed == null -> true
                levelsAllowed <= 0 -> false
                supertypes.any { it == ksClassDeclaration } -> true
                else -> supertypes.any {
                    (it as? KSClassDeclaration) ?.checkSupertypeLevel(levelsAllowed - 1) == true
                }
            }
        }

        fun handleDeclaration(ksDeclarationContainer: KSDeclarationContainer) {
            ksDeclarationContainer.declarations.forEach { potentialSubtype ->
                val simpleName = potentialSubtype.simpleName.getShortName()
                when {
                    potentialSubtype === ksClassDeclaration -> {}
                    potentialSubtype.isAnnotationPresent(ClassCastsExcluded::class) -> return@forEach
                    potentialSubtype !is KSClassDeclaration || !potentialSubtype.checkSupertypeLevel(rootAnnotation.levelsToInclude.takeIf { it >= 0 }) -> return@forEach
                    excludeRegex ?.matches(simpleName) == true -> return@forEach
                    includeRegex ?.matches(simpleName) == false -> {}
                    else -> classesSubtypes.getOrPut(ksClassDeclaration) { mutableSetOf() }.add(potentialSubtype)
                }
                handleDeclaration(potentialSubtype as? KSDeclarationContainer ?: return@forEach)
            }
        }
        resolver.getAllFiles().forEach {
            handleDeclaration(it)
        }
        fun fillWithSealeds(current: KSClassDeclaration) {
            current.getSealedSubclasses().forEach {
                val simpleName = it.simpleName.getShortName()
                if (
                    includeRegex ?.matches(simpleName) == false
                    || excludeRegex ?.matches(simpleName) == true
                    || it.isAnnotationPresent(ClassCastsExcluded::class)
                ) {
                    return@forEach
                }
                classesSubtypes.getOrPut(ksClassDeclaration) { mutableSetOf() }.add(it)
                fillWithSealeds(it)
            }
        }
        fillWithSealeds(ksClassDeclaration)

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
        fill(
            ksClassDeclaration,
            classesSubtypes.values.flatten().toSet()
        )
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
