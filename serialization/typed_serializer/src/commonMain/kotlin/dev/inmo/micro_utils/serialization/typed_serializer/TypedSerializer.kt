package dev.inmo.micro_utils.serialization.typed_serializer

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KClass

open class TypedSerializer<T : Any>(
    kClass: KClass<T>,
    presetSerializers: Map<String, KSerializer<out T>> = emptyMap(),
) : KSerializer<T> {
    protected val serializers = presetSerializers.toMutableMap()
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor(
        "TypedSerializer",
        SerialKind.CONTEXTUAL
    ) {
        element("type", String.serializer().descriptor)
        element("value", ContextualSerializer(kClass).descriptor)
    }

    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
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

    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    protected open fun <O: T> CompositeEncoder.encode(value: O) {
        encodeSerializableElement(descriptor, 1, value::class.serializer() as KSerializer<O>, value)
    }

    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeStructure(descriptor) {
            val valueSerializer = value::class.serializer()
            val type = serializers.keys.first { serializers[it] == valueSerializer }
            encodeStringElement(descriptor, 0, type)
            encode(value)
        }
    }


    open fun <O: T> include(type: String, serializer: KSerializer<O>) {
        serializers[type] = serializer
    }

    open fun exclude(type: String) {
        serializers.remove(type)
    }
}

@InternalSerializationApi
operator fun <T : Any> TypedSerializer<T>.plusAssign(kClass: KClass<T>) {
    include(kClass.simpleName!!, kClass.serializer())
}

@InternalSerializationApi
operator fun <T : Any> TypedSerializer<T>.minusAssign(kClass: KClass<T>) {
    exclude(kClass.simpleName!!)
}

inline fun <reified T : Any> TypedSerializer(
    presetSerializers: Map<String, KSerializer<out T>> = emptyMap()
) = TypedSerializer(T::class, presetSerializers)

inline fun <reified T : Any> TypedSerializer(
    vararg presetSerializers: Pair<String, KSerializer<out T>>
) = TypedSerializer(presetSerializers.toMap())
