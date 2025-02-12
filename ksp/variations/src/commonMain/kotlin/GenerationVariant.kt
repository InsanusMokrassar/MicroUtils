package dev.inmo.micro_utils.ksp.variations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.BINARY)
@Repeatable
@Target(AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER)
annotation class GenerationVariant(
    val argName: String,
    val type: String,
    val conversion: String,
    vararg val varargTypes: KClass<*>
)
