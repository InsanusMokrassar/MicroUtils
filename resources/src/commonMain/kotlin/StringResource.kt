package dev.inmo.micro_utils.strings

import dev.inmo.micro_utils.language_codes.IetfLang
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
@Serializable(StringResource.Companion::class)
data class StringResource(
    val default: String,
    val translations: Map<IetfLang, Lazy<String>>
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

        operator fun IetfLang.invoke(value: () -> String) = this variant value
        operator fun IetfLang.invoke(value: String) = this variant value


        infix fun String.variant(value: Lazy<String>) = IetfLang(this) variant value
        infix fun String.variant(value: () -> String) = IetfLang(this) variant lazy(value)
        infix fun String.variant(value: String) = this variant lazyOf(value)


        operator fun String.invoke(value: () -> String) = this variant value
        operator fun String.invoke(value: String) = this variant value

        fun build() = StringResource(default, map.toMap())
    }

    fun translation(languageCode: IetfLang?): String {
        if (languageCode == null) {
            return default
        }
        translations[languageCode] ?.let { return it.value }

        return languageCode.parentLang ?.let {
            translations[it] ?.value
        } ?: default
    }

    companion object : KSerializer<StringResource> {
        @Serializable
        private class Surrogate(
            val default: String,
            val translations: Map<String, String>
        )

        override val descriptor: SerialDescriptor
            get() = Surrogate.serializer().descriptor

        override fun deserialize(decoder: Decoder): StringResource {
            val surrogate = Surrogate.serializer().deserialize(decoder)
            return StringResource(
                surrogate.default,
                surrogate.translations.map { IetfLang(it.key) to lazyOf(it.value) }.toMap()
            )
        }

        override fun serialize(encoder: Encoder, value: StringResource) {
            Surrogate.serializer().serialize(
                encoder,
                Surrogate(
                    value.default,
                    value.translations.map {
                        it.key.code to it.value.value
                    }.toMap()
                )
            )
        }
    }
}

inline fun buildStringResource(
    default: String,
    builder: StringResource.Builder.() -> Unit = {}
): StringResource {
    return StringResource.Builder(default).apply(builder).build()
}
