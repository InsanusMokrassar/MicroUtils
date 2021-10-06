package dev.inmo.micro_utils.fsm.repos.common

import dev.inmo.micro_utils.fsm.common.State
import dev.inmo.micro_utils.fsm.common.managers.DefaultStatesManagerRepo
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.pagination.getAll

class KeyValueBasedDefaultStatesManagerRepo(
    private val keyValueRepo: KeyValueRepo<Any, State>
) : DefaultStatesManagerRepo {
    override suspend fun set(state: State) {
        keyValueRepo.set(state.context, state)
    }

    override suspend fun removeState(state: State) {
        if (keyValueRepo.get(state.context) == state) {
            keyValueRepo.unset(state.context)
        }
    }

    override suspend fun getStates(): List<State> = keyValueRepo.getAll { keys(it) }.map { it.second }
    override suspend fun getContextState(context: Any): State? = keyValueRepo.get(context)

    override suspend fun contains(context: Any): Boolean = keyValueRepo.contains(context)
}
