package dev.inmo.micro_utils.fsm.common.managers

import dev.inmo.micro_utils.fsm.common.State

/**
 * Simple [DefaultStatesManagerRepo] for [DefaultStatesManager] which will store data in [map] and use primitive
 * functionality
 */
class InMemoryDefaultStatesManagerRepo<T : State>(
    private val map: MutableMap<Any, T> = mutableMapOf()
) : DefaultStatesManagerRepo<T> {
    override suspend fun set(state: T) {
        map[state.context] = state
    }

    override suspend fun removeState(state: T) {
        map.remove(state.context)
    }

    override suspend fun getStates(): List<T> = map.values.toList()

    override suspend fun getContextState(context: Any): T? = map[context]

    override suspend fun contains(context: Any): Boolean = map.contains(context)
}
