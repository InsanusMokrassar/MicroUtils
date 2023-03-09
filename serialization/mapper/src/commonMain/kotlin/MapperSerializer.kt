package dev.inmo.micro_utils.serialization.mapper

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class MapperSerializer<I, O>(
    private val base: KSerializer<I>,
    private val serialize: (O) -> I,
    private val deserialize: (I) -> O
) : KSerializer<O> {
    override val descriptor: SerialDescriptor = base.descriptor

    override fun deserialize(decoder: Decoder): O {
        return deserialize(base.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: O) {
        base.serialize(encoder, serialize(value))
    }
}
