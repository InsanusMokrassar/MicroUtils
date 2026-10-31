package dev.inmo.micro_utils.repos.cache

import dev.inmo.kslog.common.KSLog
import dev.inmo.micro_utils.coroutines.doSynchronously
import dev.inmo.micro_utils.coroutines.runCatchingLogging
import kotlinx.coroutines.CoroutineScope

fun <T : InvalidatableRepo> T.alsoInvalidateSync(
    scope: CoroutineScope,
    onFailure: suspend (Throwable) -> Unit = {},
) = also {
    scope.doSynchronously {
        runCatching {
            invalidate()
        }.onFailure {
            onFailure(it)
        }
    }
}

fun <T : InvalidatableRepo> T.alsoInvalidateSync(
    onFailure: suspend (Throwable) -> Unit = {},
) = also {
    doSynchronously {
        runCatching {
            invalidate()
        }.onFailure {
            onFailure(it)
        }
    }
}

fun <T : InvalidatableRepo> T.alsoInvalidateSyncLogging(
    scope: CoroutineScope,
    errorMessageBuilder: CoroutineScope.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
) = also {
    scope.doSynchronously {
        runCatchingLogging(errorMessageBuilder, logger) {
            invalidate()
        }
    }
}

fun <T : InvalidatableRepo> T.alsoInvalidateSyncLogging(
    errorMessageBuilder: CoroutineScope.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
) = also {
    doSynchronously {
        runCatchingLogging(errorMessageBuilder, logger) {
            invalidate()
        }
    }
}
