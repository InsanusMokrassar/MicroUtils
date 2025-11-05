package dev.inmo.micro_ksp.generator

inline fun <T> withNoSuchElementWorkaround(
    default: T,
    block: () -> T
): T = runCatching(block).getOrElse {
    if (it is NoSuchElementException) {
        default
    } else {
        throw it
    }
}
