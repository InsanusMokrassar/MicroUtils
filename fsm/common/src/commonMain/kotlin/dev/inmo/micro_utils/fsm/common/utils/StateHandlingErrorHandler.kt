package dev.inmo.micro_utils.fsm.common.utils

typealias StateHandlingErrorHandler<T> = suspend (T, Throwable) -> T?
val DefaultStateHandlingErrorHandler: StateHandlingErrorHandler<*> = { _, _ -> null }
inline fun <T> defaultStateHandlingErrorHandler(): StateHandlingErrorHandler<T> = DefaultStateHandlingErrorHandler as StateHandlingErrorHandler<T>

