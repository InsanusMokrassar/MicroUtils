package dev.inmo.micro_utils.common

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Realization of this interface will contains at least one not null - [optionalT1] or [optionalT2]
 *
 * @see EitherFirst
 * @see EitherSecond
 * @see Either.Companion.first
 * @see Either.Companion.second
 * @see Either.onFirst
 * @see Either.onSecond
 * @see Either.mapOnFirst
 * @see Either.mapOnSecond
 */
@Serializable(EitherSerializer::class)
sealed interface Either<T1, T2> {
    val optionalT1: Optional<T1>
    val optionalT2: Optional<T2>
    @Deprecated("Use optionalT1 instead", ReplaceWith("optionalT1"))
    val t1: T1?
        get() = optionalT1.dataOrNull()
    @Deprecated("Use optionalT2 instead", ReplaceWith("optionalT2"))
    val t2: T2?
        get() = optionalT2.dataOrNull()
}

class EitherSerializer<T1, T2>(
    t1Serializer: KSerializer<T1>,
    t2Serializer: KSerializer<T2>,
) : KSerializer<Either<T1, T2>> {
    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor(
        "TypedSerializer",
        SerialKind.CONTEXTUAL
    ) {
        element("type", String.serializer().descriptor)
        element("value", ContextualSerializer(Either::class).descriptor)
    }
    private val t1EitherSerializer = EitherFirst.serializer(t1Serializer, t2Serializer)
    private val t2EitherSerializer = EitherSecond.serializer(t1Serializer, t2Serializer)

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
 * This type [Either] will always have not nullable [optionalT1]
 */
@Serializable
data class EitherFirst<T1, T2>(
    override val t1: T1
) : Either<T1, T2> {
    override val optionalT1: Optional<T1> = t1.optional
    override val optionalT2: Optional<T2> = Optional.absent()
}

/**
 * This type [Either] will always have not nullable [optionalT2]
 */
@Serializable
data class EitherSecond<T1, T2>(
    override val t2: T2
) : Either<T1, T2> {
    override val optionalT1: Optional<T1> = Optional.absent()
    override val optionalT2: Optional<T2> = t2.optional
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
 * Will call [block] in case when [this] is [EitherFirst]
 */
inline fun <T1, T2, E : Either<T1, T2>> E.onFirst(block: (T1) -> Unit): E {
    optionalT1.onPresented(block)
    return this
}

/**
 * Will call [block] in case when [this] is [EitherSecond]
 */
inline fun <T1, T2, E : Either<T1, T2>> E.onSecond(block: (T2) -> Unit): E {
    optionalT2.onPresented(block)
    return this
}

/**
 * @return Result of [block] if [this] is [EitherFirst]
 */
inline fun <T1, R> Either<T1, *>.mapOnFirst(block: (T1) -> R): R? {
    return optionalT1.mapOnPresented(block)
}

/**
 * @return Result of [block] if [this] is [EitherSecond]
 */
inline fun <T2, R> Either<*, T2>.mapOnSecond(block: (T2) -> R): R? {
    return optionalT2.mapOnPresented(block)
}

inline fun <reified T1, reified T2> Any.either() = when (this) {
    is T1 -> Either.first<T1, T2>(this)
    is T2 -> Either.second<T1, T2>(this)
    else -> error("Incorrect type of either argument $this")
}
