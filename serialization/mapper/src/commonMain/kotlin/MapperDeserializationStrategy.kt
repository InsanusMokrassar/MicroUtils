package dev.inmo.micro_utils.serialization.mapper

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Use this serializer when you have deserializable type [I] and want to map it to some [O] in process of
 * deserialization
 *
 * @param base Serializer for [I]
 * @param deserialize Will be used in [deserialize] method to convert deserialized by [base] [I] to [O]
 */
open class MapperDeserializationStrategy<I, O>(
    private val base: DeserializationStrategy<I>,
    private val deserialize: (Decoder, I) -> O
) : DeserializationStrategy<O> {
    override val descriptor: SerialDescriptor = base.descriptor

    constructor(
        base: DeserializationStrategy<I>,
        deserialize: (I) -> O
    ) : this(base, { _, i -> deserialize(i) })

    override fun deserialize(decoder: Decoder): O {
        return deserialize(decoder, base.deserialize(decoder))
    }
}
