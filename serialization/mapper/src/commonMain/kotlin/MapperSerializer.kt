package dev.inmo.micro_utils.serialization.mapper

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
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
    private val deserialize: (Decoder, I) -> O
) : KSerializer<O>,
    DeserializationStrategy<O> by MapperDeserializationStrategy<I, O>(base, deserialize),
    SerializationStrategy<O> by MapperSerializationStrategy<I, O>(base, serialize) {
    override val descriptor: SerialDescriptor = base.descriptor

    constructor(
        base: KSerializer<I>,
        serialize: (O) -> I,
        deserialize: (I) -> O
    ) : this(base, serialize, { _, i -> deserialize(i) })
}
