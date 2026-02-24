package dev.inmo.micro_utils.fsm.common

/**
 * Represents a state in a finite state machine (FSM).
 * Each state must have an associated context that identifies it uniquely within its chain.
 */
interface State {
    /**
     * The context object that uniquely identifies this state within a state chain.
     * States with the same context are considered to belong to the same chain.
     */
    val context: Any
}
