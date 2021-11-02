package dev.inmo.micro_utils.common

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Realization of this interface will contains at least one not null - [t1] or [t2]
 *
 * @see EitherFirst
 * @see EitherSecond
 * @see Either.Companion.first
 * @see Either.Companion.second
 * @see Either.onFirst
 * @see Either.onSecond
 */
@Serializable(EitherSerializer::class)
sealed interface Either<T1, T2> {
    val t1: T1?
    val t2: T2?

    companion object {
        fun <T1, T2> serializer(
            t1Serializer: KSerializer<T1>,
            t2Serializer: KSerializer<T2>,
        ): KSerializer<Either<T1, T2>> = EitherSerializer(t1Serializer, t2Serializer)
    }
}

class EitherSerializer<T1, T2>(
    t1Serializer: KSerializer<T1>,
    t2Serializer: KSerializer<T2>,
) : KSerializer<Either<T1, T2>> {
    @ExperimentalSerializationApi
    @InternalSerializationApi
    override val descriptor: SerialDescriptor = buildSerialDescriptor(
        "TypedSerializer",
        SerialKind.CONTEXTUAL
    ) {
        element("type", String.serializer().descriptor)
        element("value", ContextualSerializer(Either::class).descriptor)
    }
    private val t1EitherSerializer = EitherFirst.serializer(t1Serializer, t2Serializer)
    private val t2EitherSerializer = EitherSecond.serializer(t1Serializer, t2Serializer)

    @ExperimentalSerializationApi
    @InternalSerializationApi
    override fun deserialize(decoder: Decoder): Either<T1, T2> {
        return decoder.decodeStructure(descriptor) {
            var type: String? = null
            lateinit var result: Either<T1, T2>
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> type = decodeStringElement(descriptor, 0)
                    1 -> {
                        result = when (type) {
                            "t1" -> decodeSerializableElement(
                                descriptor,
                                1,
                                t1EitherSerializer
                            )
                            "t2" -> decodeSerializableElement(
                                descriptor,
                                1,
                                t2EitherSerializer
                            )
                            else -> error("Unknown type of either: $type")
                        }
                    }
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            result
        }
    }


    @ExperimentalSerializationApi
    @InternalSerializationApi
    override fun serialize(encoder: Encoder, value: Either<T1, T2>) {
        encoder.encodeStructure(descriptor) {
            when (value) {
                is EitherFirst -> {
                    encodeStringElement(descriptor, 0, "t1")
                    encodeSerializableElement(descriptor, 1, t1EitherSerializer, value)
                }
                is EitherSecond -> {
                    encodeStringElement(descriptor, 0, "t2")
                    encodeSerializableElement(descriptor, 1, t2EitherSerializer, value)
                }
            }
        }
    }
}

/**
 * This type [Either] will always have not nullable [t1]
 */
@Serializable
data class EitherFirst<T1, T2>(
    override val t1: T1
) : Either<T1, T2> {
    override val t2: T2?
        get() = null
}

/**
 * This type [Either] will always have not nullable [t2]
 */
@Serializable
data class EitherSecond<T1, T2>(
    override val t2: T2
) : Either<T1, T2> {
    override val t1: T1?
        get() = null
}

/**
 * @return New instance of [EitherFirst]
 */
inline fun <T1, T2> Either.Companion.first(t1: T1): Either<T1, T2> = EitherFirst(t1)
/**
 * @return New instance of [EitherSecond]
 */
inline fun <T1, T2> Either.Companion.second(t2: T2): Either<T1, T2> = EitherSecond(t2)

/**
 * Will call [block] in case when [Either.t1] of [this] is not null
 */
inline fun <T1, T2, E : Either<T1, T2>> E.onFirst(crossinline block: (T1) -> Unit): E {
    val t1 = t1
    t1 ?.let(block)
    return this
}

/**
 * Will call [block] in case when [Either.t2] of [this] is not null
 */
inline fun <T1, T2, E : Either<T1, T2>> E.onSecond(crossinline block: (T2) -> Unit): E {
    val t2 = t2
    t2 ?.let(block)
    return this
}

inline fun <reified T1, reified T2> Any.either() = when (this) {
    is T1 -> Either.first<T1, T2>(this)
    is T2 -> Either.second<T1, T2>(this)
    else -> error("Incorrect type of either argument $this")
}
