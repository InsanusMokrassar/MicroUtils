package dev.inmo.micro_utils.repos.annotations


@RequiresOptIn(
    "Overriding of this invalidate message requires manual launching of invalidation on class initialization process",
    RequiresOptIn.Level.WARNING
)
@Target(
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
)
annotation class OverrideRequireManualInvalidation