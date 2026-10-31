package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * Executes the given [block] and unlocks all provided [lockers] for writing if the block succeeds.
 * If the block throws an exception, the lockers will remain locked.
 *
 * @param lockers Variable number of [SmartRWLocker] instances to unlock on successful execution
 * @param block The suspending function to execute
 * @return A [Result] containing [Unit] on success or the exception that occurred
 */
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

/**
 * Asynchronously executes the given [block] and unlocks all provided [lockers] for writing if the block succeeds.
 * This function launches a new coroutine in the given [scope] and automatically logs and drops any exceptions.
 *
 * @param scope The [CoroutineScope] in which to launch the coroutine
 * @param lockers Variable number of [SmartRWLocker] instances to unlock on successful execution
 * @param block The suspending function to execute
 * @return A [Job] representing the launched coroutine
 */
fun alsoWithUnlockingOnSuccessAsync(
    scope: CoroutineScope,
    vararg lockers: SmartRWLocker,
    block: suspend () -> Unit
): Job = scope.launchLoggingDropExceptions {
    alsoWithUnlockingOnSuccess(*lockers, block = block)
}