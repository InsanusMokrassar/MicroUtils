package dev.inmo.micro_utils.meta

/**
 * Marks the direct use of [MetaContainer.map] as requiring explicit opt-in.
 *
 * This annotation warns against direct manipulation of the internal map without using
 * the type-safe accessors, which could break type safety guarantees.
 */
@RequiresOptIn(
    "Do not use this directly without any special reason",
    RequiresOptIn.Level.WARNING
)
@Target(
    AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.BINARY)
annotation class MetaContainerRootMapWarning
