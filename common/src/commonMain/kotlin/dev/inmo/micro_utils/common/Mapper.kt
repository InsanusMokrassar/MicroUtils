package dev.inmo.micro_utils.common

interface SimpleMapper<T1, T2> {
    fun t1(from: T2): T1
    fun t2(from: T1): T2

    operator fun T2.invoke(): T1 = t1(this)
    operator fun T1.invoke(): T2 = t2(this)

    fun T2.convert(): T1 = t1(this)
    fun T1.convert(): T2 = t2(this)
}

class SimpleMapperImpl<T1, T2>(
    private val t1: (T2) -> T1,
    private val t2: (T1) -> T2,
) : SimpleMapper<T1, T2> {
    override fun t1(from: T2): T1 = t1.invoke(from)

    override fun t2(from: T1): T2 = t2.invoke(from)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T1, T2> simpleMapper(
    noinline t1: (T2) -> T1,
    noinline t2: (T1) -> T2,
) = SimpleMapperImpl(t1, t2)

interface SimpleSuspendableMapper<T1, T2> {
    suspend fun t1(from: T2): T1
    suspend fun t2(from: T1): T2

    suspend operator fun T2.invoke(): T1 = t1(this)
    suspend operator fun T1.invoke(): T2 = t2(this)

    suspend fun T2.convert(): T1 = t1(this)
    suspend fun T1.convert(): T2 = t2(this)
}

class SimpleSuspendableMapperImpl<T1, T2>(
    private val t1: suspend (T2) -> T1,
    private val t2: suspend (T1) -> T2,
) : SimpleSuspendableMapper<T1, T2> {
    override suspend fun t1(from: T2): T1 = t1.invoke(from)

    override suspend fun t2(from: T1): T2 = t2.invoke(from)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T1, T2> simpleSuspendableMapper(
    noinline t1: suspend (T2) -> T1,
    noinline t2: suspend (T1) -> T2,
) = SimpleSuspendableMapperImpl(t1, t2)
