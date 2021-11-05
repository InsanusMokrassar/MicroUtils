package dev.inmo.micro_utils.fsm.repos.common

import dev.inmo.micro_utils.fsm.common.State
import dev.inmo.micro_utils.fsm.common.managers.DefaultStatesManagerRepo
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.pagination.getAll

class KeyValueBasedDefaultStatesManagerRepo<T : State>(
    private val keyValueRepo: KeyValueRepo<Any, T>
) : DefaultStatesManagerRepo<T> {
    override suspend fun set(state: T) {
        keyValueRepo.set(state.context, state)
    }

    override suspend fun removeState(state: T) {
        if (keyValueRepo.get(state.context) == state) {
            keyValueRepo.unset(state.context)
        }
    }

    override suspend fun getStates(): List<T> = keyValueRepo.getAll { keys(it) }.map { it.second }
    override suspend fun getContextState(context: Any): T? = keyValueRepo.get(context)

    override suspend fun contains(context: Any): Boolean = keyValueRepo.contains(context)
}
