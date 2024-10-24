package dev.inmo.micro_utils.common

inline fun <T> Sequence<T>.padWith(size: Int, inserter: (Sequence<T>) -> Sequence<T>): Sequence<T> {
    var result = this
    while (result.count() < size) {
        result = inserter(result)
    }
    return result
}

inline fun <T> Sequence<T>.padEnd(size: Int, padBlock: (Int) -> T): Sequence<T> = padWith(size) { it + padBlock(it.count()) }

inline fun <T> Sequence<T>.padEnd(size: Int, o: T) = padEnd(size) { o }

inline fun <T> List<T>.padWith(size: Int, inserter: (List<T>) -> List<T>): List<T> {
    var result = this
    while (result.size < size) {
        result = inserter(result)
    }
    return result
}
inline fun <T> List<T>.padEnd(size: Int, padBlock: (Int) -> T): List<T> = asSequence().padEnd(size, padBlock).toList()

inline fun <T> List<T>.padEnd(size: Int, o: T): List<T> = asSequence().padEnd(size, o).toList()

inline fun <T> Sequence<T>.padStart(size: Int, padBlock: (Int) -> T): Sequence<T> = padWith(size) { sequenceOf(padBlock(it.count())) + it }

inline fun <T> Sequence<T>.padStart(size: Int, o: T) = padStart(size) { o }

inline fun <T> List<T>.padStart(size: Int, padBlock: (Int) -> T): List<T> = asSequence().padStart(size, padBlock).toList()

inline fun <T> List<T>.padStart(size: Int, o: T): List<T> = asSequence().padStart(size, o).toList()
