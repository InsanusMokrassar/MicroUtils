package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

suspend inline fun <T> doInUI(noinline block: suspend CoroutineScope.() -> T) = withContext(
    Dispatchers.Main,
    block
)
suspend inline fun <T> doInDefault(noinline block: suspend CoroutineScope.() -> T) = withContext(
    Dispatchers.Default,
    block
)
