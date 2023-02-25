package dev.inmo.micro_utils.repos.annotations

import kotlin.reflect.KClass

/**
 * Use this annotation and ksp generator (module `micro_utils.repos.generator`) to create the next hierarchy of models:
 *
 * * New model. For example: data class NewTest
 * * Registered model. For example: data class RegisteredTest
 *
 * @param registeredSupertypes These [KClass]es will be used as supertypes for registered model
 * @param serializable If true (default) will generate @[kotlinx.serialization.Serializable] for models. Affects [generateSerialName]
 * @param serializable If true (default) will generate @[kotlinx.serialization.SerialName] for models with their names as values
 *
 * @see GenerateCRUDModelExcludeOverride
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class GenerateCRUDModel(
    vararg val registeredSupertypes: KClass<*>,
    val serializable: Boolean = true,
    val generateSerialName: Boolean = true
)


/**
 * Use this annotation on properties which should be excluded from overriding in models.
 *
 * @see GenerateCRUDModel
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.PROPERTY)
annotation class GenerateCRUDModelExcludeOverride


