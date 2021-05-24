package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

val IO
    get() = Dispatchers.IO

suspend inline fun <T> doInIO(noinline block: suspend CoroutineScope.() -> T) = doIn(
    IO,
    block
)
