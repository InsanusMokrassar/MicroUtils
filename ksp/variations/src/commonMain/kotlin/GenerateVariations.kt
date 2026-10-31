package dev.inmo.micro_utils.ksp.variations

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
annotation class GenerateVariations(
    val prefix: String = ""
)
