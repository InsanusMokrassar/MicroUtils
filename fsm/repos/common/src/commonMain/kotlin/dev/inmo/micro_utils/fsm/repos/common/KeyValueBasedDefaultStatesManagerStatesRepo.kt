package dev.inmo.micro_utils.fsm.repos.common

import dev.inmo.micro_utils.fsm.common.State
import dev.inmo.micro_utils.fsm.common.managers.DefaultStatesManagerStatesRepo
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.pagination.getAll

class KeyValueBasedDefaultStatesManagerStatesRepo(
    private val keyValueRepo: KeyValueRepo<Any, State>
) : DefaultStatesManagerStatesRepo {
    override suspend fun newState(state: State) {
        keyValueRepo.set(state.context, state)
    }

    override suspend fun updateState(from: State, to: State) {
        if (from.context != to.context && keyValueRepo.get(from.context) == from) {
            keyValueRepo.unset(from.context)
        }
        keyValueRepo.set(to.context, to)
    }

    override suspend fun removeState(state: State) {
        if (keyValueRepo.get(state.context) == state) {
            keyValueRepo.unset(state.context)
        }
    }

    override suspend fun getStates(): List<State> = keyValueRepo.getAll { keys(it) }.map { it.second }
}
