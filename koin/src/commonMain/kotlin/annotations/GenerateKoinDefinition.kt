package dev.inmo.micro_utils.koin.annotations

import kotlin.reflect.KClass

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
