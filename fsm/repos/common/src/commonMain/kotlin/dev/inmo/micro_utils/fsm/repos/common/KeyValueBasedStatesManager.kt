package dev.inmo.micro_utils.fsm.repos.common

import dev.inmo.micro_utils.fsm.common.State
import dev.inmo.micro_utils.fsm.common.StatesManager
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.mappers.withMapper
import dev.inmo.micro_utils.repos.pagination.getAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Deprecated("Replace with DefaultStatesManager and KeyValueBasedDefaultStatesManagerRepo")
class KeyValueBasedStatesManager(
    private val keyValueRepo: KeyValueRepo<Any, State>,
    private val onContextsConflictResolver: suspend (old: State, new: State, currentNew: State) -> Boolean = { _, _, _ -> true }
) : StatesManager {
    private val _onChainStateUpdated = MutableSharedFlow<Pair<State, State>>(0)
    override val onChainStateUpdated: Flow<Pair<State, State>> = _onChainStateUpdated.asSharedFlow()
    private val _onEndChain = MutableSharedFlow<State>(0)
    override val onEndChain: Flow<State> = _onEndChain.asSharedFlow()

    override val onStartChain: Flow<State> = keyValueRepo.onNewValue.map { it.second }

    private val mutex = Mutex()

    override suspend fun update(old: State, new: State) {
        mutex.withLock {
            when {
                keyValueRepo.get(old.context) != old -> return@withLock
                old.context == new.context || !keyValueRepo.contains(new.context) -> {
                    keyValueRepo.set(old.context, new)
                    _onChainStateUpdated.emit(old to new)
                }
                else -> {
                    val stateOnNewOneContext = keyValueRepo.get(new.context)!!
                    if (onContextsConflictResolver(old, new, stateOnNewOneContext)) {
                        endChainWithoutLock(stateOnNewOneContext)
                        keyValueRepo.unset(old.context)
                        keyValueRepo.set(new.context, new)
                        _onChainStateUpdated.emit(old to new)
                    }
                }
            }

        }
    }

    override suspend fun startChain(state: State) {
        if (!keyValueRepo.contains(state.context)) {
            keyValueRepo.set(state.context, state)
        }
    }

    private suspend fun endChainWithoutLock(state: State) {
        if (keyValueRepo.get(state.context) == state) {
            keyValueRepo.unset(state.context)
            _onEndChain.emit(state)
        }
    }

    override suspend fun endChain(state: State) {
        mutex.withLock { endChainWithoutLock(state) }
    }

    override suspend fun getActiveStates(): List<State> {
        return keyValueRepo.getAll { keys(it) }.map { it.second }
    }

}

inline fun <reified TargetContextType, reified TargetStateType> createStatesManager(
    targetKeyValueRepo: KeyValueRepo<TargetContextType, TargetStateType>,
    noinline contextToOutTransformer: suspend Any.() -> TargetContextType,
    noinline stateToOutTransformer: suspend State.() -> TargetStateType,
    noinline outToContextTransformer: suspend TargetContextType.() -> Any,
    noinline outToStateTransformer: suspend TargetStateType.() -> State,
) = KeyValueBasedStatesManager(
    targetKeyValueRepo.withMapper<Any, State, TargetContextType, TargetStateType>(
        contextToOutTransformer,
        stateToOutTransformer,
        outToContextTransformer,
        outToStateTransformer
    )
)
