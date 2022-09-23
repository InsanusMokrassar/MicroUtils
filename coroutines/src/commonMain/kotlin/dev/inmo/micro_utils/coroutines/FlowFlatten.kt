package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.*

inline fun <T, R> Flow<Flow<T>>.flatMap(
    crossinline mapper: suspend (T) -> R
) = flow {
    collect {
        it.collect {
            emit(mapper(it))
        }
    }
}

inline fun <T, R> Flow<Iterable<T>>.flatMap(
    crossinline mapper: suspend (T) -> R
) = map {
    it.asFlow()
}.flatMap(mapper)

inline fun <T, R> Flow<Flow<T>>.flatMapNotNull(
    crossinline mapper: suspend (T) -> R
) = flatMap(mapper).filterNot { it == null }

inline fun <T, R> Flow<Iterable<T>>.flatMapNotNull(
    crossinline mapper: suspend (T) -> R
) = flatMap(mapper).filterNot { it == null }

fun <T> Flow<Iterable<T>>.flatten() = flatMap { it }

fun <T> Flow<Flow<T>>.flatten() = flatMap { it }
