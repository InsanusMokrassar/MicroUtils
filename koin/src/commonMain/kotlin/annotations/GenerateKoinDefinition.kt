package dev.inmo.micro_utils.koin.annotations

import kotlin.reflect.KClass

/**
 * Use this annotation to mark files near to which generator should place generated extensions for koin [org.koin.core.scope.Scope]
 * and [org.koin.core.Koin]
 *
 * @param name Name for definitions. This name will be available as extension for [org.koin.core.scope.Scope] and [org.koin.core.Koin]
 * @param type Type of extensions. It is base star-typed class
 * @param typeArgs Generic types for [type]. For example, if [type] == `Something::class` and [typeArgs] == `G1::class,
 * G2::class`, the result type will be `Something<G1, G2>`
 * @param nullable In case when true, extension will not throw error when definition has not been registered in koin
 * @param generateSingle Generate definition factory with [org.koin.core.module.Module.single]. You will be able to use
 * the extension [org.koin.core.module.Module].[name]Single(createdAtStart/* default false */) { /* your definition */ }
 * @param generateFactory Generate definition factory with [org.koin.core.module.Module.factory]. You will be able to use
 * the extension [org.koin.core.module.Module].[name]Factory { /* your definition */ }
 */
@Target(AnnotationTarget.FILE)
@Repeatable
annotation class GenerateKoinDefinition(
    val name: String,
    val type: KClass<*>,
    vararg val typeArgs: KClass<*>,
    val nullable: Boolean = true,
    val generateSingle: Boolean = true,
    val generateFactory: Boolean = true
)
