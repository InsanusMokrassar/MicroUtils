package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

suspend inline fun <T> doInIO(noinline block: suspend CoroutineScope.() -> T) = withContext(
    Dispatchers.IO,
    block
)
