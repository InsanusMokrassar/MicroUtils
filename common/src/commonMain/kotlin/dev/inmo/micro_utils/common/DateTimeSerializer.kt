package dev.inmo.micro_utils.common

import korlibs.time.DateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(DateTime::class)
object DateTimeSerializer : KSerializer<DateTime> {
    override val descriptor: SerialDescriptor
        get() = Double.serializer().descriptor

    override fun deserialize(decoder: Decoder): DateTime {
        return DateTime(decoder.decodeDouble())
    }

    override fun serialize(encoder: Encoder, value: DateTime) {
        encoder.encodeDouble(value.unixMillis)
    }
}