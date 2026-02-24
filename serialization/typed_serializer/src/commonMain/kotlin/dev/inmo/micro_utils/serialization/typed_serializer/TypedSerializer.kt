package dev.inmo.micro_utils.serialization.typed_serializer

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.KClass

/**
 * A serializer that includes type information in the serialized output.
 * This allows polymorphic serialization where the exact type is preserved in the output
 * as a "type" field alongside the "value" field.
 *
 * The serialized format is: `{"type": "TypeName", "value": {...}}`
 *
 * @param T The base type that this serializer handles
 * @param kClass The Kotlin class of the base type
 * @param presetSerializers A map of type names to their serializers for known subtypes
 */
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


    /**
     * Adds a new type to this serializer with its associated serializer.
     *
     * @param O The specific subtype to include
     * @param type The type name to use in serialized output
     * @param serializer The serializer for this type
     */
    open fun <O: T> include(type: String, serializer: KSerializer<O>) {
        serializers[type] = serializer
    }

    /**
     * Removes a type from this serializer by its name.
     *
     * @param type The type name to remove
     */
    open fun exclude(type: String) {
        serializers.remove(type)
    }
}

/**
 * Adds a type to this [TypedSerializer] using its class name as the type identifier.
 *
 * @param T The type to add
 * @param kClass The Kotlin class to add
 */
@InternalSerializationApi
operator fun <T : Any> TypedSerializer<T>.plusAssign(kClass: KClass<T>) {
    include(kClass.simpleName!!, kClass.serializer())
}

/**
 * Removes a type from this [TypedSerializer] using its class name.
 *
 * @param T The type to remove
 * @param kClass The Kotlin class to remove
 */
@InternalSerializationApi
operator fun <T : Any> TypedSerializer<T>.minusAssign(kClass: KClass<T>) {
    exclude(kClass.simpleName!!)
}

/**
 * Creates a [TypedSerializer] for the reified type [T].
 *
 * @param T The base type to serialize
 * @param presetSerializers A map of type names to serializers for known subtypes
 * @return A new [TypedSerializer] instance
 */
inline fun <reified T : Any> TypedSerializer(
    presetSerializers: Map<String, KSerializer<out T>> = emptyMap()
) = TypedSerializer(T::class, presetSerializers)

/**
 * Creates a [TypedSerializer] for the reified type [T] with vararg preset serializers.
 *
 * @param T The base type to serialize
 * @param presetSerializers Pairs of type names to serializers for known subtypes
 * @return A new [TypedSerializer] instance
 */
inline fun <reified T : Any> TypedSerializer(
    vararg presetSerializers: Pair<String, KSerializer<out T>>
) = TypedSerializer(presetSerializers.toMap())
