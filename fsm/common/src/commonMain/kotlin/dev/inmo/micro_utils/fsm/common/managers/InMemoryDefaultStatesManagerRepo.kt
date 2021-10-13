package dev.inmo.micro_utils.fsm.common.managers

import dev.inmo.micro_utils.fsm.common.State

/**
 * Simple [DefaultStatesManagerRepo] for [DefaultStatesManager] which will store data in [map] and use primitive
 * functionality
 */
class InMemoryDefaultStatesManagerRepo(
    private val map: MutableMap<Any, State> = mutableMapOf()
) : DefaultStatesManagerRepo {
    override suspend fun set(state: State) {
        map[state.context] = state
    }

    override suspend fun removeState(state: State) {
        map.remove(state.context)
    }

    override suspend fun getStates(): List<State> = map.values.toList()

    override suspend fun getContextState(context: Any): State? = map[context]

    override suspend fun contains(context: Any): Boolean = map.contains(context)
}
