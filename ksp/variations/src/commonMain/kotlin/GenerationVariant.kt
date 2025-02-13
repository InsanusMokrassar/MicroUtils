package dev.inmo.micro_utils.ksp.variations

import kotlin.reflect.KClass

/**
 * @param argName New argument name. Default - empty - means "use default arg name"
 * @param type Qualified class name, like "dev.inmo.micro_utils.ksp.variants.GenerationVariant"
 * @param conversion Conversion string with `this`
 */
@Retention(AnnotationRetention.BINARY)
@Repeatable
@Target(AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER)
annotation class GenerationVariant(
    val type: KClass<*>,
    val conversion: String,
    val argName: String = "",
    vararg val genericTypes: KClass<*>
)
