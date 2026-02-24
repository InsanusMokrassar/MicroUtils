package dev.inmo.micro_utils.coroutines

/**
 * Replaces a failed [Result] with a new value computed from the exception.
 * If this [Result] is successful, it is returned as-is. If it represents a failure,
 * the [onException] handler is called with the exception to compute a replacement value,
 * which is then wrapped in a new [Result].
 *
 * @param T The type of the successful value
 * @param onException A function that computes a replacement value from the caught exception
 * @return The original [Result] if successful, or a new [Result] containing the replacement value
 */
inline fun <T> Result<T>.replaceIfFailure(onException: (Throwable) -> T) = if (isSuccess) { this } else { runCatching { onException(exceptionOrNull()!!) } }
