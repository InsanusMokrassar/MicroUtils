package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

class DeferredAction<T, O>(
    val deferred: Deferred<T>,
    val callback: suspend (T) -> O
) {
    suspend operator fun invoke() = callback(deferred.await())
}

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

fun <T, O> Deferred<T>.buildAction(callback: suspend (T) -> O) = DeferredAction(this, callback)

suspend fun <O> Iterable<DeferredAction<*, O>>.invokeFirstOf(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
): O {
    return map { it.deferred }.awaitFirstWithDeferred(scope, cancelOnResult).let { result ->
        first { it.deferred == result.first }.invoke()
    }
}

suspend fun <O> invokeFirstOf(
    scope: CoroutineScope,
    vararg variants: DeferredAction<*, O>,
    cancelOnResult: Boolean = true
): O = variants.toList().invokeFirstOf(scope, cancelOnResult)

suspend fun <T, O> Iterable<Deferred<T>>.invokeOnFirst(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true,
    callback: suspend (T) -> O
): O = map { it.buildAction(callback) }.invokeFirstOf(scope, cancelOnResult)

suspend fun <T, O> CoroutineScope.invokeOnFirstOf(
    cancelOnResult: Boolean = true,
    block: DoWithFirstBuilder<T>.() -> Unit,
    callback: suspend (T) -> O
) = firstOf(
    DoWithFirstBuilder<T>(this).apply(block).build(),
    cancelOnResult
).let { callback(it) }

suspend fun <T, O> invokeOnFirst(
    scope: CoroutineScope,
    vararg variants: Deferred<T>,
    cancelOnResult: Boolean = true,
    callback: suspend (T) -> O
): O = variants.toList().invokeOnFirst(scope, cancelOnResult, callback)

suspend fun <T> CoroutineScope.firstOf(
    variants: Iterable<Deferred<T>>,
    cancelOnResult: Boolean = true
) = variants.invokeOnFirst(this, cancelOnResult) { it }

suspend fun <T> CoroutineScope.firstOf(
    cancelOnResult: Boolean = true,
    block: DoWithFirstBuilder<T>.() -> Unit
) = firstOf(
    DoWithFirstBuilder<T>(this).apply(block).build(),
    cancelOnResult
)

suspend fun <T> CoroutineScope.firstOf(
    vararg variants: Deferred<T>,
    cancelOnResult: Boolean = true
) = firstOf(variants.toList(), cancelOnResult)

suspend fun <T> List<Deferred<T>>.first(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
) = scope.firstOf(this, cancelOnResult)
