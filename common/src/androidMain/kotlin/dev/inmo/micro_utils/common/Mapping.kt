package dev.inmo.micro_utils.common

@Suppress("UNCHECKED_CAST", "SimplifiableCall")
inline fun <T, R> Iterable<T>.mapNotNullA(transform: (T) -> R?): List<R> = map(transform).filter { it != null } as List<R>

@Suppress("UNCHECKED_CAST", "SimplifiableCall")
inline fun <T, R> Array<T>.mapNotNullA(mapper: (T) -> R?): List<R> = map(mapper).filter { it != null } as List<R>
