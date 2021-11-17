package dev.inmo.micro_utils.common

import kotlinx.serialization.Serializable

/**
 * This type represents [T] as not only potentially nullable data, but also as a data which can not be presented. This
 * type will be useful in cases when [T] is nullable and null as valuable data too in time of data absence should be
 * presented by some third type.
 *
 * Let's imagine, you have nullable name in some database. In case when name is not nullable everything is clear - null
 * will represent absence of row in the database. In case when name is nullable null will be a little bit dual-meaning,
 * cause this null will say nothing about availability of the row (of course, it is exaggerated example)
 *
 * @see Optional.presented
 * @see Optional.absent
 * @see Optional.optional
 * @see Optional.onPresented
 * @see Optional.onAbsent
 */
@Serializable
data class Optional<T> internal constructor(
    internal val data: T?,
    internal val dataPresented: Boolean
) {
    companion object {
        /**
         * Will create [Optional] with presented data
         */
        fun <T> presented(data: T) = Optional(data, true)
        /**
         * Will create [Optional] without data
         */
        fun <T> absent() = Optional<T>(null, false)
    }
}

inline val <T> T.optional
    get() = Optional.presented(this)

/**
 * Will call [block] when data presented ([Optional.dataPresented] == true)
 */
fun <T> Optional<T>.onPresented(block: (T) -> Unit): Optional<T> = apply {
    if (dataPresented) { block(data as T) }
}

/**
 * Will call [block] when data absent ([Optional.dataPresented] == false)
 */
fun <T> Optional<T>.onAbsent(block: () -> Unit): Optional<T> = apply {
    if (!dataPresented) { block() }
}

/**
 * Returns [Optional.data] if [Optional.dataPresented] of [this] is true, or null otherwise
 */
fun <T> Optional<T>.dataOrNull() = if (dataPresented) data as T else null

/**
 * Returns [Optional.data] if [Optional.dataPresented] of [this] is true, or throw [throwable] otherwise
 */
fun <T> Optional<T>.dataOrThrow(throwable: Throwable) = if (dataPresented) data as T else throw throwable


/**
 * Returns [Optional.data] if [Optional.dataPresented] of [this] is true, or call [block] and returns the result of it
 */
fun <T> Optional<T>.dataOrElse(block: () -> T) = if (dataPresented) data as T else block()

/**
 * Returns [Optional.data] if [Optional.dataPresented] of [this] is true, or call [block] and returns the result of it
 */
suspend fun <T> Optional<T>.dataOrElseSuspendable(block: suspend () -> T) = if (dataPresented) data as T else block()
