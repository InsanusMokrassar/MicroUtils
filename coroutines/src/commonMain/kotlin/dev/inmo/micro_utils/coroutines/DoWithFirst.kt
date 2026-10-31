package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

/**
 * Represents a deferred action that combines a [Deferred] value with a callback to be executed on that value.
 *
 * @param T The type of the deferred value
 * @param O The type of the result after applying the callback
 * @param deferred The deferred value to await
 * @param callback The suspending function to apply to the deferred value
 */
class DeferredAction<T, O>(
    val deferred: Deferred<T>,
    val callback: suspend (T) -> O
) {
    suspend operator fun invoke() = callback(deferred.await())
}

/**
 * A builder for creating multiple deferred computations that can be executed, with only the first completing
 * one being used. This is useful for race conditions where you want the result of whichever computation finishes first.
 *
 * @param T The type of values produced by the deferred computations
 * @param scope The [CoroutineScope] in which to create the deferred computations
 */
class DoWithFirstBuilder<T>(
    private val scope: CoroutineScope
) {
    private val deferreds = mutableListOf<Deferred<T>>()
    operator fun plus(block: suspend CoroutineScope.() -> T) {
        deferreds.add(scope.async(start = CoroutineStart.LAZY, block = block))
    }
    fun add(block: suspend CoroutineScope.() -> T) = plus(block)
    fun include(block: suspend CoroutineScope.() -> T) = plus(block)

    fun build() = deferreds.toList()
}

/**
 * Creates a [DeferredAction] from this [Deferred] and a [callback] function.
 *
 * @param T The type of the deferred value
 * @param O The type of the result after applying the callback
 * @param callback The suspending function to apply to the deferred value
 * @return A [DeferredAction] combining the deferred and callback
 */
fun <T, O> Deferred<T>.buildAction(callback: suspend (T) -> O) = DeferredAction(this, callback)

/**
 * Invokes the first [DeferredAction] whose deferred value completes, executing its callback and returning the result.
 * Other deferred actions are cancelled if [cancelOnResult] is true.
 *
 * @param O The type of the result after applying callbacks
 * @param scope The [CoroutineScope] in which to await the deferred values
 * @param cancelOnResult If true, cancels all other deferred actions after the first completes. Defaults to true
 * @return The result of invoking the first completed deferred action
 */
suspend fun <O> Iterable<DeferredAction<*, O>>.invokeFirstOf(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
): O {
    return map { it.deferred }.awaitFirstWithDeferred(scope, cancelOnResult).let { result ->
        first { it.deferred == result.first }.invoke()
    }
}

/**
 * Invokes the first [DeferredAction] from the given [variants] whose deferred value completes,
 * executing its callback and returning the result. Other deferred actions are cancelled if [cancelOnResult] is true.
 *
 * @param O The type of the result after applying callbacks
 * @param scope The [CoroutineScope] in which to await the deferred values
 * @param variants The deferred actions to race
 * @param cancelOnResult If true, cancels all other deferred actions after the first completes. Defaults to true
 * @return The result of invoking the first completed deferred action
 */
suspend fun <O> invokeFirstOf(
    scope: CoroutineScope,
    vararg variants: DeferredAction<*, O>,
    cancelOnResult: Boolean = true
): O = variants.toList().invokeFirstOf(scope, cancelOnResult)

/**
 * Awaits the first [Deferred] to complete and invokes the [callback] on its value.
 * Other deferred values are cancelled if [cancelOnResult] is true.
 *
 * @param T The type of the deferred values
 * @param O The type of the result after applying the callback
 * @param scope The [CoroutineScope] in which to await the deferred values
 * @param cancelOnResult If true, cancels all other deferred values after the first completes. Defaults to true
 * @param callback The suspending function to apply to the first completed value
 * @return The result of applying the callback to the first completed value
 */
suspend fun <T, O> Iterable<Deferred<T>>.invokeOnFirst(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true,
    callback: suspend (T) -> O
): O = map { it.buildAction(callback) }.invokeFirstOf(scope, cancelOnResult)

/**
 * Builds multiple deferred computations using [DoWithFirstBuilder] and invokes [callback] on the first one to complete.
 * Other deferred computations are cancelled if [cancelOnResult] is true.
 *
 * @param T The type of the deferred values
 * @param O The type of the result after applying the callback
 * @param cancelOnResult If true, cancels all other computations after the first completes. Defaults to true
 * @param block Builder DSL to define the deferred computations
 * @param callback The suspending function to apply to the first completed value
 * @return The result of applying the callback to the first completed value
 */
suspend fun <T, O> CoroutineScope.invokeOnFirstOf(
    cancelOnResult: Boolean = true,
    block: DoWithFirstBuilder<T>.() -> Unit,
    callback: suspend (T) -> O
) = firstOf(
    DoWithFirstBuilder<T>(this).apply(block).build(),
    cancelOnResult
).let { callback(it) }

/**
 * Awaits the first [Deferred] from the given [variants] to complete and invokes the [callback] on its value.
 * Other deferred values are cancelled if [cancelOnResult] is true.
 *
 * @param T The type of the deferred values
 * @param O The type of the result after applying the callback
 * @param scope The [CoroutineScope] in which to await the deferred values
 * @param variants The deferred values to race
 * @param cancelOnResult If true, cancels all other deferred values after the first completes. Defaults to true
 * @param callback The suspending function to apply to the first completed value
 * @return The result of applying the callback to the first completed value
 */
suspend fun <T, O> invokeOnFirst(
    scope: CoroutineScope,
    vararg variants: Deferred<T>,
    cancelOnResult: Boolean = true,
    callback: suspend (T) -> O
): O = variants.toList().invokeOnFirst(scope, cancelOnResult, callback)

/**
 * Returns the value of the first [Deferred] from the given [variants] to complete.
 * Other deferred values are cancelled if [cancelOnResult] is true.
 *
 * @param T The type of the deferred values
 * @param variants The deferred values to race
 * @param cancelOnResult If true, cancels all other deferred values after the first completes. Defaults to true
 * @return The value of the first completed deferred
 */
suspend fun <T> CoroutineScope.firstOf(
    variants: Iterable<Deferred<T>>,
    cancelOnResult: Boolean = true
) = variants.invokeOnFirst(this, cancelOnResult) { it }

/**
 * Builds multiple deferred computations using [DoWithFirstBuilder] and returns the value of the first one to complete.
 * Other deferred computations are cancelled if [cancelOnResult] is true.
 *
 * @param T The type of the deferred values
 * @param cancelOnResult If true, cancels all other computations after the first completes. Defaults to true
 * @param block Builder DSL to define the deferred computations
 * @return The value of the first completed computation
 */
suspend fun <T> CoroutineScope.firstOf(
    cancelOnResult: Boolean = true,
    block: DoWithFirstBuilder<T>.() -> Unit
) = firstOf(
    DoWithFirstBuilder<T>(this).apply(block).build(),
    cancelOnResult
)

/**
 * Returns the value of the first [Deferred] from the given [variants] to complete.
 * Other deferred values are cancelled if [cancelOnResult] is true.
 *
 * @param T The type of the deferred values
 * @param variants The deferred values to race
 * @param cancelOnResult If true, cancels all other deferred values after the first completes. Defaults to true
 * @return The value of the first completed deferred
 */
suspend fun <T> CoroutineScope.firstOf(
    vararg variants: Deferred<T>,
    cancelOnResult: Boolean = true
) = firstOf(variants.toList(), cancelOnResult)

/**
 * Returns the value of the first [Deferred] from this list to complete, using the given [scope].
 * Other deferred values are cancelled if [cancelOnResult] is true.
 *
 * @param T The type of the deferred values
 * @param scope The [CoroutineScope] in which to await the deferred values
 * @param cancelOnResult If true, cancels all other deferred values after the first completes. Defaults to true
 * @return The value of the first completed deferred
 */
suspend fun <T> List<Deferred<T>>.first(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
) = scope.firstOf(this, cancelOnResult)
