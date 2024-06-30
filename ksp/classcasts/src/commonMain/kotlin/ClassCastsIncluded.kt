package dev.inmo.micro_utils.ksp.classcasts

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ClassCastsIncluded(val typesRegex: String = "", val excludeRegex: String = "", val outputFilePrefix: String = "")
