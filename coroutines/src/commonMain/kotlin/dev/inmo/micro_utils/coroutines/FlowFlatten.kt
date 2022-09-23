package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.*
import kotlin.js.JsName
import kotlin.jvm.JvmName

inline fun <T, R> Flow<Flow<T>>.flatMap(
    crossinline mapper: suspend (T) -> R
) = flow {
    collect {
        it.collect {
            emit(mapper(it))
        }
    }
}

@JsName("flatMapIterable")
@JvmName("flatMapIterable")
inline fun <T, R> Flow<Iterable<T>>.flatMap(
    crossinline mapper: suspend (T) -> R
) = map {
    it.asFlow()
}.flatMap(mapper)

inline fun <T, R> Flow<Flow<T>>.flatMapNotNull(
    crossinline mapper: suspend (T) -> R
) = flatMap(mapper).takeNotNull()

@JsName("flatMapNotNullIterable")
@JvmName("flatMapNotNullIterable")
inline fun <T, R> Flow<Iterable<T>>.flatMapNotNull(
    crossinline mapper: suspend (T) -> R
) = flatMap(mapper).takeNotNull()

fun <T> Flow<Flow<T>>.flatten() = flatMap { it }

@JsName("flattenIterable")
@JvmName("flattenIterable")
fun <T> Flow<Iterable<T>>.flatten() = flatMap { it }
