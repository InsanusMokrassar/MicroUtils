package dev.inmo.micro_utils.common

@Deprecated("Redundant", ReplaceWith("coerceIn(min, max)"))
@Suppress("NOTHING_TO_INLINE")
inline fun <T : Comparable<T>> T.clamp(min: T, max: T): T = coerceIn(min, max)
