package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.e

inline fun <T, R> R.runCatchingLogging(
    noinline errorMessageBuilder: R.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    block: R.() -> T
) = runCatching(block).onFailure {
    logger.e(it) { errorMessageBuilder(it) }
}
