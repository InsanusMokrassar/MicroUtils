package dev.inmo.micro_utils.common

/**
 * Breaks this list into a list of consecutive pairs.
 * Each element is paired with the next element in the list.
 * For a list of size n, the result will contain n-1 pairs.
 *
 * Example: `[1, 2, 3, 4].breakAsPairs()` returns `[(1, 2), (2, 3), (3, 4)]`
 *
 * @param T The type of elements in the list
 * @return A list of pairs where each pair consists of consecutive elements
 */
fun <T> List<T>.breakAsPairs(): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()

    for (i in 0 until size - 1) {
        val first = get(i)
        val second = get(i + 1)
        result.add(first to second)
    }

    return result
}
