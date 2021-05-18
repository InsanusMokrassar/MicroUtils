package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> doInUI(noinline block: suspend CoroutineScope.() -> T) = withContext(
    Dispatchers.Main,
    block
)
suspend inline fun <T> doInDefault(noinline block: suspend CoroutineScope.() -> T) = withContext(
    Dispatchers.Default,
    block
)
suspend inline fun <T> doInIO(noinline block: suspend CoroutineScope.() -> T) = withContext(
    Dispatchers.IO,
    block
)
