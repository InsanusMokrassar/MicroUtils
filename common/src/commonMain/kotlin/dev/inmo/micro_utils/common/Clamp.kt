package dev.inmo.micro_utils.common

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Comparable<T>> T.clamp(min: T, max: T): T {
    return when {
        this < min -> min
        this > max -> max
        else -> this
    }
}
