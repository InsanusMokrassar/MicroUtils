package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.*
import kotlin.js.JsName
import kotlin.jvm.JvmName

/**
 * Transforms each inner [Flow] element using the given [mapper] function and flattens the result into a single [Flow].
 *
 * @param T The type of elements in the inner flows
 * @param R The type of elements after applying the mapper
 * @param mapper A suspending function to transform each element
 * @return A [Flow] of mapped and flattened elements
 */
inline fun <T, R> Flow<Flow<T>>.flatMap(
    crossinline mapper: suspend (T) -> R
) = flow {
    collect {
        it.collect {
            emit(mapper(it))
        }
    }
}

/**
 * Transforms each element from inner [Iterable]s using the given [mapper] function and flattens the result into a single [Flow].
 *
 * @param T The type of elements in the iterables
 * @param R The type of elements after applying the mapper
 * @param mapper A suspending function to transform each element
 * @return A [Flow] of mapped and flattened elements
 */
@JsName("flatMapIterable")
@JvmName("flatMapIterable")
inline fun <T, R> Flow<Iterable<T>>.flatMap(
    crossinline mapper: suspend (T) -> R
) = map {
    it.asFlow()
}.flatMap(mapper)

/**
 * Transforms each inner [Flow] element using the given [mapper] function, flattens the result,
 * and filters out null values.
 *
 * @param T The type of elements in the inner flows
 * @param R The type of elements after applying the mapper
 * @param mapper A suspending function to transform each element
 * @return A [Flow] of non-null mapped and flattened elements
 */
inline fun <T, R> Flow<Flow<T>>.flatMapNotNull(
    crossinline mapper: suspend (T) -> R
) = flatMap(mapper).takeNotNull()

/**
 * Transforms each element from inner [Iterable]s using the given [mapper] function, flattens the result,
 * and filters out null values.
 *
 * @param T The type of elements in the iterables
 * @param R The type of elements after applying the mapper
 * @param mapper A suspending function to transform each element
 * @return A [Flow] of non-null mapped and flattened elements
 */
@JsName("flatMapNotNullIterable")
@JvmName("flatMapNotNullIterable")
inline fun <T, R> Flow<Iterable<T>>.flatMapNotNull(
    crossinline mapper: suspend (T) -> R
) = flatMap(mapper).takeNotNull()

/**
 * Flattens a [Flow] of [Flow]s into a single [Flow] by collecting all inner flows sequentially.
 *
 * @param T The type of elements in the inner flows
 * @return A [Flow] containing all elements from all inner flows
 */
fun <T> Flow<Flow<T>>.flatten() = flatMap { it }

/**
 * Flattens a [Flow] of [Iterable]s into a single [Flow] by emitting all elements from each iterable.
 *
 * @param T The type of elements in the iterables
 * @return A [Flow] containing all elements from all iterables
 */
@JsName("flattenIterable")
@JvmName("flattenIterable")
fun <T> Flow<Iterable<T>>.flatten() = flatMap { it }
