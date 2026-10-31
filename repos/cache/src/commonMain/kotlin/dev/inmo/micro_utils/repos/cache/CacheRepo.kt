package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.launchLoggingDropExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface InvalidatableRepo {
    /**
     * Invalidates its internal data. It __may__ lead to autoreload of data. In case when repo makes autoreload,
     * it must do loading of data __before__ clear
     */
    suspend fun invalidate()
}

suspend fun <T : InvalidatableRepo> T.alsoInvalidate() = also {
    invalidate()
}

fun <T : InvalidatableRepo> T.alsoInvalidateAsync(scope: CoroutineScope) = also {
    scope.launchLoggingDropExceptions {
        invalidate()
    }
}

typealias CacheRepo = InvalidatableRepo
