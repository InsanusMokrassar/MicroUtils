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

    override suspend fun performStateUpdate(previousState: Optional<T>, actualState: T, scope: CoroutineScope) {
        statesJobsMutex.withLock {
            statesJobs[actualState] ?.cancel()
            val job = previousState.mapOnPresented {
                statesJobs.remove(it)
            } ?: scope.launch {
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

    override suspend fun updateChain(currentState: T, newState: T) {
        statesManager.update(currentState, newState)
    }
}
