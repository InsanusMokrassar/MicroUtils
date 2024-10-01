package dev.inmo.micro_utils.common

fun <T> Iterable<T>.withReplacedAt(i: Int, block: (T) -> T): List<T> = take(i) + block(elementAt(i)) + drop(i + 1)
fun <T> Iterable<T>.withReplaced(t: T, block: (T) -> T): List<T> = withReplacedAt(indexOf(t), block)

