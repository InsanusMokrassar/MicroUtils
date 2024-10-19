package dev.inmo.micro_utils.ksp.sealed

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class GenerateSealedWorkaround(
    val prefix: String = "",
    val includeNonSealedSubTypes: Boolean = false
) {
    @Retention(AnnotationRetention.BINARY)
    @Target(AnnotationTarget.CLASS)
    annotation class Order(val order: Int)
    @Retention(AnnotationRetention.BINARY)
    @Target(AnnotationTarget.CLASS)
    annotation class Exclude
}