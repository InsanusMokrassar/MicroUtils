package dev.inmo.micro_utils.common

/**
 * Will try to execute [action] and, if any exception will happen, execution will be retried.
 * This process will happen at most [count] times. There is no any limits on [count] value, but [action] will run at
 * least once and [retryOnFailure] will return its result if it is successful
 */
inline fun <T> retryOnFailure(count: Int, action: () -> T): T {
    var triesCount = 0
    while (true) {
        val result = runCatching {
            action()
        }.onFailure {
            triesCount++

            if (triesCount >= count) {
                throw it
            } else {
                null
            }
        }

        if (result.isSuccess) return result.getOrThrow()
    }
    error("Unreachable code: retry must throw latest exception if error happen or success value if not")
}
