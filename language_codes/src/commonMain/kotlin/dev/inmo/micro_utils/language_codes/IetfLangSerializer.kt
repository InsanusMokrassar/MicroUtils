package dev.inmo.micro_utils.language_codes

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
