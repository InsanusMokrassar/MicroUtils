package dev.inmo.micro_utils.strings

import dev.inmo.micro_utils.language_codes.IetfLanguageCode

/**
 * Use this class as a type of your strings object fields. For example:
 *
 * ```kotlin
 * object Strings {
 *     val someResource: StringResource
 * }
 * ```
 *
 * Use [buildStringResource] for useful creation of string resource
 *
 * @see buildStringResource
 */
class StringResource(
    val default: String,
    val map: Map<IetfLanguageCode, Lazy<String>>
) {
    class Builder(
        var default: String
    ) {
        private val map = mutableMapOf<IetfLanguageCode, Lazy<String>>()

        infix fun IetfLanguageCode.variant(value: Lazy<String>) {
            map[this] = value
        }
        infix fun IetfLanguageCode.variant(value: () -> String) = this variant lazy(value)
        infix fun IetfLanguageCode.variant(value: String) = this variant lazyOf(value)


        infix fun String.variant(value: Lazy<String>) = IetfLanguageCode(this) variant value
        infix fun String.variant(value: () -> String) = IetfLanguageCode(this) variant lazy(value)
        infix fun String.variant(value: String) = this variant lazyOf(value)

        fun build() = StringResource(default, map.toMap())
    }

    fun translation(languageCode: IetfLanguageCode): String {
        map[languageCode] ?.let { return it.value }

        return languageCode.parentLang ?.let {
            map[it] ?.value
        } ?: default
    }
}

inline fun buildStringResource(
    default: String,
    builder: StringResource.Builder.() -> Unit
): StringResource {
    return StringResource.Builder(default).apply(builder).build()
}
