package dev.inmo.micro_utils.serialization.typed_serializer

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KClass

class TypedSerializer<T : Any>(
    kClass: KClass<T>,
    presetSerializers: Map<String, KSerializer<out T>> = emptyMap()
) : KSerializer<T> {
    private val serializers = presetSerializers.toMutableMap()
    @InternalSerializationApi
    override val descriptor: SerialDescriptor = buildSerialDescriptor(
        "TextSourceSerializer",
        SerialKind.CONTEXTUAL
    ) {
        element("type", String.serializer().descriptor)
        element("value", ContextualSerializer(kClass).descriptor)
    }

    @InternalSerializationApi
    override fun deserialize(decoder: Decoder): T {
        return decoder.decodeStructure(descriptor) {
            var type: String? = null
            lateinit var result: T
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> type = decodeStringElement(descriptor, 0)
                    1 -> {
                        require(type != null) { "Type is null, but it is expected that was inited already" }
                        result = decodeSerializableElement(
                            descriptor,
                            1,
                            serializers.getValue(type)
                        )
                    }
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            result
        }
    }

    @InternalSerializationApi
    private fun <O: T> CompositeEncoder.encode(value: O) {
        encodeSerializableElement(descriptor, 1, value::class.serializer() as KSerializer<O>, value)
    }

    @InternalSerializationApi
    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeStructure(descriptor) {
            val valueSerializer = value::class.serializer()
            val type = serializers.keys.first { serializers[it] == valueSerializer }
            encodeStringElement(descriptor, 0, type)
            encode(value)
        }
    }


    fun <O: T> include(type: String, serializer: KSerializer<O>) {
        require(type !in serializers.keys)
        serializers[type] = serializer
    }

    fun exclude(type: String) {
        require(type !in serializers.keys)
        serializers.remove(type)
    }
}

inline fun <reified T : Any> TypedSerializer(
    presetSerializers: Map<String, KSerializer<out T>> = emptyMap()
) = TypedSerializer(T::class, presetSerializers)
