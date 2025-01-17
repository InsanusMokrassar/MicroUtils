package dev.inmo.micro_utils.fsm.common

import kotlinx.coroutines.flow.Flow

interface StatesManager<T : State> {
    val onChainStateUpdated: Flow<Pair<T, T>>
    val onStartChain: Flow<T>
    val onEndChain: Flow<T>


    /**
     * It is expected, that [new] state will be saved in manager.
     *
     * If [new] context will not be equal to [old] one, it must do some check of availability for replacement
     * of potentially exists state on [new] context. If this state can't be replaced, it will throw [IllegalStateException]
     *
     * @throws IllegalStateException - in case when [new] [State] can't be set
     */
    suspend fun update(old: T, new: T)

    /**
     * Starts chain with [state] as first [State]. May returns false in case of [State.context] of [state] is already
     * busy by the other [State]
     */
    suspend fun startChain(state: T)

    /**
     * Ends chain with context from [state]. In case when [State.context] of [state] is absent, [state] should be just
     * ignored
     */
    suspend fun endChain(state: T)

    suspend fun getActiveStates(): List<T>
}

