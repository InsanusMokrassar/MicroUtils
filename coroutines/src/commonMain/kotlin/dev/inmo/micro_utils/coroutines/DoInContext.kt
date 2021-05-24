package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

inline val UI
    get() = Dispatchers.Main
inline val Default
    get() = Dispatchers.Default

suspend inline fun <T> doIn(context: CoroutineContext, noinline block: suspend CoroutineScope.() -> T) = withContext(
    context,
    block
)

suspend inline fun <T> doInUI(noinline block: suspend CoroutineScope.() -> T) = doIn(
    UI,
    block
)
suspend inline fun <T> doInDefault(noinline block: suspend CoroutineScope.() -> T) = doIn(
    Default,
    block
)
