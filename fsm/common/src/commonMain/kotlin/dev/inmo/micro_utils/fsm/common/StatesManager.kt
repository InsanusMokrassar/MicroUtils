package dev.inmo.micro_utils.fsm.common

import kotlinx.coroutines.flow.*

interface StatesManager<T : State> {
    val onChainStateUpdated: Flow<Pair<T, T>>
    val onStartChain: Flow<T>
    val onEndChain: Flow<T>


    /**
     * Must set current set using [State.context]
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

