package dev.inmo.micro_utils.fsm.common.managers

import dev.inmo.micro_utils.fsm.common.State
import kotlinx.coroutines.flow.*

/**
 * Creates [DefaultStatesManager] with [InMemoryDefaultStatesManagerRepo]
 *
 * @param onContextsConflictResolver Receive old [State], new one and the state currently placed on new [State.context]
 * key. In case when this callback will returns true, the state placed on [State.context] of new will be replaced by
 * new state by using [endChain] with that state
 */
@Deprecated("Use DefaultStatesManager instead", ReplaceWith("DefaultStatesManager"))
fun <T: State> InMemoryStatesManager(
    onContextsConflictResolver: suspend (old: T, new: T, currentNew: T) -> Boolean = { _, _, _ -> true }
) = DefaultStatesManager(onContextsConflictResolver = onContextsConflictResolver)
