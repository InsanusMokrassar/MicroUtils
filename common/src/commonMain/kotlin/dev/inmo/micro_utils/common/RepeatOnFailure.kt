package dev.inmo.micro_utils.common

/**
 * Executes the given [action] until getting of successful result specified number of [times].
 *
 * A zero-based index of current iteration is passed as a parameter to [action].
 */
inline fun <R> repeatOnFailure(
    times: Int,
    crossinline onEachFailure: (Throwable) -> Unit = {},
    crossinline action: (Int) -> R
): Optional<R> {
    repeat(times) {
        runCatching {
            action(it)
        }.onFailure(onEachFailure).onSuccess {
            return Optional.presented(it)
        }
    }
    return Optional.absent()
}
