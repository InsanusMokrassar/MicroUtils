package dev.inmo.micro_utils.common

/**
 * Executes the given [action] until getting of successful result specified number of [times].
 *
 * A zero-based index of current iteration is passed as a parameter to [action].
 */
inline fun <R> repeatOnFailure(
    onFailure: (Throwable) -> Boolean,
    action: () -> R
): Result<R> {
    do {
        runCatching {
            action()
        }.onFailure {
            if (!onFailure(it)) {
                return Result.failure(it)
            }
        }.onSuccess {
            return Result.success(it)
        }
    } while (true)
}

/**
 * Executes the given [action] until getting of successful result specified number of [times].
 *
 * A zero-based index of current iteration is passed as a parameter to [action].
 */
inline fun <R> repeatOnFailure(
    times: Int,
    onEachFailure: (Throwable) -> Unit = {},
    action: (Int) -> R
): Optional<R> {
    var i = 0
    val result = repeatOnFailure(
        {
            onEachFailure(it)
            if (i < times) {
                i++
                true
            } else {
                false
            }
        }
    ) {
        action(i)
    }
    return if (result.isSuccess) {
        Optional.presented(result.getOrThrow())
    } else {
        Optional.absent()
    }
}
