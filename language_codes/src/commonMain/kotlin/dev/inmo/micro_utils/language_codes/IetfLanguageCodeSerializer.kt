package dev.inmo.micro_utils.language_codes

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IetfLanguageCodeSerializer : KSerializer<IetfLanguageCode> {
    override val descriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): IetfLanguageCode {
        return IetfLanguageCode(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: IetfLanguageCode) {
        encoder.encodeString(value.code)
    }
}
