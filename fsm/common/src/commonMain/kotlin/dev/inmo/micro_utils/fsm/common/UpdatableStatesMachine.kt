package dev.inmo.micro_utils.fsm.common

import dev.inmo.micro_utils.common.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.withLock

/**
 * This extender of [StatesMachine] interface declare one new function [updateChain]. Realizations of this interface
 * must be able to perform update of chain in internal [StatesManager]
 */
interface UpdatableStatesMachine<T : State> : StatesMachine<T> {
    /**
     * Update chain with current state equal to [currentState] with [newState]. Behaviour of this update preforming
     * in cases when [currentState] does not exist in [StatesManager] must be declared inside of realization of
     * [StatesManager.update] function
     */
    suspend fun updateChain(currentState: T, newState: T)
}

open class DefaultUpdatableStatesMachine<T : State>(
    statesManager: StatesManager<T>,
    handlers: List<CheckableHandlerHolder<in T, T>>,
) : DefaultStatesMachine<T>(
    statesManager,
    handlers
), UpdatableStatesMachine<T> {
    protected val jobsStates = mutableMapOf<Job, T>()

    /**
     * Realization of this update will use the [Job] of [previousState] in [statesJobs] and [jobsStates] if
     * [previousState] is [Optional.presented] and [shouldReplaceJob] has returned true for [previousState] and [actualState]. In
     * other words, [Job] of [previousState] WILL NOT be replaced with the new one if they are "equal". Equality of
     * states is solved in [shouldReplaceJob] and can be rewritten in subclasses
     */
    override suspend fun performStateUpdate(previousState: Optional<T>, actualState: T, scope: CoroutineScope) {
        statesJobsMutex.withLock {
            if (compare(previousState, actualState)) {
                statesJobs[actualState] ?.cancel()
            }
            val job = previousState.mapOnPresented {
                statesJobs.remove(it)
            } ?.takeIf { it.isActive } ?: scope.launch {
                performUpdate(actualState)
            }.also { job ->
                job.invokeOnCompletion { _ ->
                    scope.launch {
                        statesJobsMutex.withLock {
                            statesJobs.remove(
                                jobsStates[job] ?: return@withLock
                            )
                        }
                    }
                }
            }
            jobsStates.remove(job)
            statesJobs[actualState] = job
            jobsStates[job] = actualState
        }
    }

    /**
     * Compare if [previous] potentially lead to the same behaviour with [new]
     */
    protected open suspend fun shouldReplaceJob(previous: Optional<T>, new: T): Boolean = previous.dataOrNull() != new

    @Deprecated("Overwrite shouldReplaceJob instead")
    protected open suspend fun compare(previous: Optional<T>, new: T): Boolean = shouldReplaceJob(previous, new)

    override suspend fun updateChain(currentState: T, newState: T) {
        statesManager.update(currentState, newState)
    }
}
