package dev.inmo.micro_utils.coroutines

inline fun <T> Result<T>.replaceIfFailure(onException: (Throwable) -> T) = if (isSuccess) { this } else { runCatching { onException(exceptionOrNull()!!) } }
