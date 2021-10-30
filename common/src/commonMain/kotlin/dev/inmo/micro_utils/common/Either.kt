package dev.inmo.micro_utils.common

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
sealed interface Either<T1, T2> {
    val t1: T1?
    val t2: T2?

    companion object
}

/**
 * This type [Either] will always have not nullable [t1]
 */
data class EitherFirst<T1, T2>(
    override val t1: T1
) : Either<T1, T2> {
    override val t2: T2?
        get() = null
}

/**
 * This type [Either] will always have not nullable [t2]
 */
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
