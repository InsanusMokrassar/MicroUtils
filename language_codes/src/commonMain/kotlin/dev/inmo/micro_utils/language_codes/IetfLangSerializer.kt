package dev.inmo.micro_utils.language_codes

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Serializer for [IetfLang] that serializes language codes as their string representation.
 * The language code is serialized as a simple string (e.g., "en-US", "fr", "de-DE").
 */
object IetfLangSerializer : KSerializer<IetfLang> {
    override val descriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): IetfLang {
        return IetfLang(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: IetfLang) {
        encoder.encodeString(value.code)
    }
}
@Deprecated("Renamed", ReplaceWith("IetfLangSerializer", "dev.inmo.micro_utils.language_codes.IetfLangSerializer"))
typealias IetfLanguageCodeSerializer = IetfLangSerializer
