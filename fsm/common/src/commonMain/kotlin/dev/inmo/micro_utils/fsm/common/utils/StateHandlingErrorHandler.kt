package dev.inmo.micro_utils.fsm.common.utils

/**
 * A handler function type for dealing with errors during state handling in a finite state machine.
 * The handler receives the state that caused the error and the thrown exception, and can optionally
 * return a new state to continue the chain, or null to end the chain.
 *
 * @param T The state type that caused the error
 * @param Throwable The exception that was thrown during state handling
 * @return A new state to continue with, or null to end the state chain
 */
typealias StateHandlingErrorHandler<T> = suspend (T, Throwable) -> T?

/**
 * The default error handler that returns null for all errors, effectively ending the state chain.
 */
val DefaultStateHandlingErrorHandler: StateHandlingErrorHandler<*> = { _, _ -> null }

/**
 * Returns a typed version of the [DefaultStateHandlingErrorHandler].
 *
 * @param T The state type
 * @return A [StateHandlingErrorHandler] for type [T]
 */
inline fun <T> defaultStateHandlingErrorHandler(): StateHandlingErrorHandler<T> = DefaultStateHandlingErrorHandler as StateHandlingErrorHandler<T>

