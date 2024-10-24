package dev.inmo.micro_utils.common

fun <T> List<T>.breakAsPairs(): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()

    for (i in 0 until size - 1) {
        val first = get(i)
        val second = get(i + 1)
        result.add(first to second)
    }

    return result
}
