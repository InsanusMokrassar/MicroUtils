package dev.inmo.micro_utils.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class IndexedValueSerializer<T>(private val subSerializer: KSerializer<T>) : KSerializer<IndexedValue<T>> {
    private val originalSerializer = PairSerializer(Int.serializer(), subSerializer)
    override val descriptor: SerialDescriptor
        get() = originalSerializer.descriptor

    override fun deserialize(decoder: Decoder): IndexedValue<T> {
        val pair = originalSerializer.deserialize(decoder)
        return IndexedValue(
            pair.first,
            pair.second
        )
    }

    override fun serialize(encoder: Encoder, value: IndexedValue<T>) {
        originalSerializer.serialize(
            encoder,
            Pair(value.index, value.value)
        )
    }
}
