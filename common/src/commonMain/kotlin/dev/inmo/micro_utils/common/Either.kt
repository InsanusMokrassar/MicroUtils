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

data class EitherFirst<T1, T2>(
    override val t1: T1
) : Either<T1, T2> {
    override val t2: T2?
        get() = null
}

data class EitherSecond<T1, T2>(
    override val t2: T2
) : Either<T1, T2> {
    override val t1: T1?
        get() = null
}

inline fun <T1, T2> Either.Companion.first(t1: T1): Either<T1, T2> = EitherFirst(t1)
inline fun <T1, T2> Either.Companion.second(t1: T1): Either<T1, T2> = EitherFirst(t1)

inline fun <T1, T2, E : Either<T1, T2>> E.onFirst(crossinline block: (T1) -> Unit): E {
    if (this is EitherFirst<*, *>) block(t1 as T1)
    return this
}

inline fun <T1, T2, E : Either<T1, T2>> E.onSecond(crossinline block: (T2) -> Unit): E {
    if (this is EitherSecond<*, *>) block(t2 as T2)
    return this
}
