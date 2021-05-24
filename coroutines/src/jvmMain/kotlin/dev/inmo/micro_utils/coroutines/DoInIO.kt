package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> doInIO(noinline block: suspend CoroutineScope.() -> T) = withContext(
    Dispatchers.IO,
    block
)
