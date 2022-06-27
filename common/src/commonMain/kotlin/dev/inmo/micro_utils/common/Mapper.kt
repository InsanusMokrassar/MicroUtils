package dev.inmo.micro_utils.common

import kotlin.jvm.JvmName

interface SimpleMapper<T1, T2> {
    fun convertToT1(from: T2): T1
    fun convertToT2(from: T1): T2
}

@JvmName("convertFromT2")
fun <T1, T2> SimpleMapper<T1, T2>.convert(from: T2) = convertToT1(from)
@JvmName("convertFromT1")
fun <T1, T2> SimpleMapper<T1, T2>.convert(from: T1) = convertToT2(from)

class SimpleMapperImpl<T1, T2>(
    private val t1: (T2) -> T1,
    private val t2: (T1) -> T2,
) : SimpleMapper<T1, T2> {
    override fun convertToT1(from: T2): T1 = t1.invoke(from)

    override fun convertToT2(from: T1): T2 = t2.invoke(from)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T1, T2> simpleMapper(
    noinline t1: (T2) -> T1,
    noinline t2: (T1) -> T2,
) = SimpleMapperImpl(t1, t2)

interface SimpleSuspendableMapper<T1, T2> {
    suspend fun convertToT1(from: T2): T1
    suspend fun convertToT2(from: T1): T2
}

@JvmName("convertFromT2")
suspend fun <T1, T2> SimpleSuspendableMapper<T1, T2>.convert(from: T2) = convertToT1(from)
@JvmName("convertFromT1")
suspend fun <T1, T2> SimpleSuspendableMapper<T1, T2>.convert(from: T1) = convertToT2(from)

class SimpleSuspendableMapperImpl<T1, T2>(
    private val t1: suspend (T2) -> T1,
    private val t2: suspend (T1) -> T2,
) : SimpleSuspendableMapper<T1, T2> {
    override suspend fun convertToT1(from: T2): T1 = t1.invoke(from)

    override suspend fun convertToT2(from: T1): T2 = t2.invoke(from)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T1, T2> simpleSuspendableMapper(
    noinline t1: suspend (T2) -> T1,
    noinline t2: suspend (T1) -> T2,
) = SimpleSuspendableMapperImpl(t1, t2)
