package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.e
import kotlin.coroutines.cancellation.CancellationException

/**
 * Executes the given [block] within a `runCatching` context and logs any exceptions that occur, excluding
 * `CancellationException` which is rethrown. This method simplifies error handling by automatically logging
 * the errors using the provided [logger].
 *
 * @param T The result type of the [block].
 * @param R The receiver type on which this function operates.
 * @param errorMessageBuilder A lambda to build the error log message. By default, it returns a generic error message.
 * @param logger The logging instance used for logging errors. Defaults to [KSLog].
 * @param block The code block to execute within the `runCatching` context.
 * @return A [Result] representing the outcome of executing the [block].
 */
inline fun <T, R> R.runCatchingLogging(
    noinline errorMessageBuilder: R.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    block: R.() -> T
) = runCatching(block).onFailure {
    when (it) {
        is CancellationException -> throw it
        else -> logger.e(it) { errorMessageBuilder(it) }
    }
}
