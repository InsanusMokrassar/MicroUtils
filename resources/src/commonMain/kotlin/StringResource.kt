package dev.inmo.micro_utils.strings

import dev.inmo.micro_utils.language_codes.IetfLang

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
    val map: Map<IetfLang, Lazy<String>>
) {
    class Builder(
        var default: String
    ) {
        private val map = mutableMapOf<IetfLang, Lazy<String>>()

        infix fun IetfLang.variant(value: Lazy<String>) {
            map[this] = value
        }

        infix fun IetfLang.variant(value: () -> String) = this variant lazy(value)
        infix fun IetfLang.variant(value: String) = this variant lazyOf(value)


        infix fun String.variant(value: Lazy<String>) = IetfLang(this) variant value
        infix fun String.variant(value: () -> String) = IetfLang(this) variant lazy(value)
        infix fun String.variant(value: String) = this variant lazyOf(value)

        fun build() = StringResource(default, map.toMap())
    }

    fun translation(languageCode: IetfLang?): String {
        if (languageCode == null) {
            return default
        }
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
