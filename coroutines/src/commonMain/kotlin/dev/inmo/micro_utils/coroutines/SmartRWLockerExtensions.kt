package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

suspend inline fun alsoWithUnlockingOnSuccess(
    vararg lockers: SmartRWLocker,
    block: suspend () -> Unit
): Result<Unit> {
    return runCatching {
        block()
    }.onSuccess {
        lockers.forEach { it.unlockWrite() }
    }
}

fun alsoWithUnlockingOnSuccessAsync(
    scope: CoroutineScope,
    vararg lockers: SmartRWLocker,
    block: suspend () -> Unit
): Job = scope.launchLoggingDropExceptions {
    alsoWithUnlockingOnSuccess(*lockers, block = block)
}