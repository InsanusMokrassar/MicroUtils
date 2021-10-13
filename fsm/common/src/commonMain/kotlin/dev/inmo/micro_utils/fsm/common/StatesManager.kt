package dev.inmo.micro_utils.fsm.common

import kotlinx.coroutines.flow.*

interface StatesManager {
    val onChainStateUpdated: Flow<Pair<State, State>>
    val onStartChain: Flow<State>
    val onEndChain: Flow<State>


    /**
     * Must set current set using [State.context]
     */
    suspend fun update(old: State, new: State)

    /**
     * Starts chain with [state] as first [State]. May returns false in case of [State.context] of [state] is already
     * busy by the other [State]
     */
    suspend fun startChain(state: State)

    /**
     * Ends chain with context from [state]. In case when [State.context] of [state] is absent, [state] should be just
     * ignored
     */
    suspend fun endChain(state: State)

    suspend fun getActiveStates(): List<State>
}

