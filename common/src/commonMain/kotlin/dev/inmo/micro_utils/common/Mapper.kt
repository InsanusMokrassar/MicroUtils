package dev.inmo.micro_utils.common

import kotlin.jvm.JvmName

/**
 * A bidirectional mapper that can convert between two types [T1] and [T2].
 *
 * @param T1 The first type
 * @param T2 The second type
 */
interface SimpleMapper<T1, T2> {
    fun convertToT1(from: T2): T1
    fun convertToT2(from: T1): T2
}

/**
 * Converts [from] of type [T2] to type [T1] using this mapper.
 *
 * @param from The value to convert
 * @return The converted value of type [T1]
 */
@JvmName("convertFromT2")
fun <T1, T2> SimpleMapper<T1, T2>.convert(from: T2) = convertToT1(from)

/**
 * Converts [from] of type [T1] to type [T2] using this mapper.
 *
 * @param from The value to convert
 * @return The converted value of type [T2]
 */
@JvmName("convertFromT1")
fun <T1, T2> SimpleMapper<T1, T2>.convert(from: T1) = convertToT2(from)

/**
 * Implementation of [SimpleMapper] that uses lambda functions for conversion.
 *
 * @param T1 The first type
 * @param T2 The second type
 * @param t1 Function to convert from [T2] to [T1]
 * @param t2 Function to convert from [T1] to [T2]
 */
class SimpleMapperImpl<T1, T2>(
    private val t1: (T2) -> T1,
    private val t2: (T1) -> T2,
) : SimpleMapper<T1, T2> {
    override fun convertToT1(from: T2): T1 = t1.invoke(from)

    override fun convertToT2(from: T1): T2 = t2.invoke(from)
}

/**
 * Creates a [SimpleMapper] using the provided conversion functions.
 *
 * @param T1 The first type
 * @param T2 The second type
 * @param t1 Function to convert from [T2] to [T1]
 * @param t2 Function to convert from [T1] to [T2]
 * @return A new [SimpleMapperImpl] instance
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T1, T2> simpleMapper(
    noinline t1: (T2) -> T1,
    noinline t2: (T1) -> T2,
) = SimpleMapperImpl(t1, t2)

/**
 * A bidirectional mapper that can convert between two types [T1] and [T2] using suspending functions.
 *
 * @param T1 The first type
 * @param T2 The second type
 */
interface SimpleSuspendableMapper<T1, T2> {
    suspend fun convertToT1(from: T2): T1
    suspend fun convertToT2(from: T1): T2
}

/**
 * Converts [from] of type [T2] to type [T1] using this suspending mapper.
 *
 * @param from The value to convert
 * @return The converted value of type [T1]
 */
@JvmName("convertFromT2")
suspend fun <T1, T2> SimpleSuspendableMapper<T1, T2>.convert(from: T2) = convertToT1(from)

/**
 * Converts [from] of type [T1] to type [T2] using this suspending mapper.
 *
 * @param from The value to convert
 * @return The converted value of type [T2]
 */
@JvmName("convertFromT1")
suspend fun <T1, T2> SimpleSuspendableMapper<T1, T2>.convert(from: T1) = convertToT2(from)

/**
 * Implementation of [SimpleSuspendableMapper] that uses suspending lambda functions for conversion.
 *
 * @param T1 The first type
 * @param T2 The second type
 * @param t1 Suspending function to convert from [T2] to [T1]
 * @param t2 Suspending function to convert from [T1] to [T2]
 */
class SimpleSuspendableMapperImpl<T1, T2>(
    private val t1: suspend (T2) -> T1,
    private val t2: suspend (T1) -> T2,
) : SimpleSuspendableMapper<T1, T2> {
    override suspend fun convertToT1(from: T2): T1 = t1.invoke(from)

    override suspend fun convertToT2(from: T1): T2 = t2.invoke(from)
}

/**
 * Creates a [SimpleSuspendableMapper] using the provided suspending conversion functions.
 *
 * @param T1 The first type
 * @param T2 The second type
 * @param t1 Suspending function to convert from [T2] to [T1]
 * @param t2 Suspending function to convert from [T1] to [T2]
 * @return A new [SimpleSuspendableMapperImpl] instance
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T1, T2> simpleSuspendableMapper(
    noinline t1: suspend (T2) -> T1,
    noinline t2: suspend (T1) -> T2,
) = SimpleSuspendableMapperImpl(t1, t2)
