package dev.inmo.micro_utils.fsm.repos.common

import dev.inmo.kslog.common.TagLogger
import dev.inmo.kslog.common.i
import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.fsm.common.State
import dev.inmo.micro_utils.fsm.common.managers.DefaultStatesManagerRepo
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.pagination.getAll
import dev.inmo.micro_utils.repos.unset

class KeyValueBasedDefaultStatesManagerRepo<T : State>(
    private val keyValueRepo: KeyValueRepo<Any, T>
) : DefaultStatesManagerRepo<T> {
    private val locker = SmartRWLocker()
    private val logger = TagLogger("KeyValueBasedDefaultStatesManagerRepo")
    override suspend fun set(state: T) {
        locker.withWriteLock {
            keyValueRepo.set(state.context, state)
            logger.i { "Set ${state.context} value to $state" }
        }
    }

    override suspend fun removeState(state: T) {
        locker.withWriteLock {
            if (keyValueRepo.get(state.context) == state) {
                keyValueRepo.unset(state.context)
                logger.i { "Unset $state" }
            }
        }
    }

    override suspend fun removeAndSet(toRemove: T, toSet: T) {
        locker.withWriteLock {
            when {
                toRemove.context == toSet.context -> {
                    keyValueRepo.set(toSet.context, toSet)
                }
                else -> {
                    keyValueRepo.set(toSet.context, toSet)
                    keyValueRepo.unset(toRemove)
                }
            }
        }
    }

    override suspend fun getStates(): List<T> = locker.withReadAcquire {
        keyValueRepo.getAll { keys(it) }.map { it.second }
    }
    override suspend fun getContextState(context: Any): T? = locker.withReadAcquire {
        keyValueRepo.get(context)
    }

    override suspend fun contains(context: Any): Boolean = locker.withReadAcquire {
        keyValueRepo.contains(context)
    }
}
