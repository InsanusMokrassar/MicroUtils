package dev.inmo.micro_utils.serialization.mapper

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Use this serializer when you have serializable type [I] and want to map it to some [O] in process of
 * serialization/deserialization
 *
 * @param base Serializer for [I]
 * @param serialize Will be used in [serialize] method to convert incoming [O] to [I] and serialize with [base]
 * @param deserialize Will be used in [deserialize] method to convert deserialized by [base] [I] to [O]
 */
open class MapperSerializer<I, O>(
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
