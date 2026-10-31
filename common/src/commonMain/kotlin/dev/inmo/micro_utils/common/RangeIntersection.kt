package dev.inmo.micro_utils.common

/**
 * Computes the intersection of this range with [other]. Returns a pair representing
 * the intersecting range, or null if the ranges don't overlap.
 *
 * @param T The type of comparable values in the range
 * @param other The other range to intersect with
 * @return A pair (start, end) representing the intersection, or null if no intersection exists
 */
fun <T : Comparable<T>> ClosedRange<T>.intersect(other: ClosedRange<T>): Pair<T, T>? = when {
    start == other.start && endInclusive == other.endInclusive -> start to endInclusive
    start > other.endInclusive || other.start > endInclusive -> null
    else -> maxOf(start, other.start) to minOf(endInclusive, other.endInclusive)
}

/**
 * Computes the intersection of this [IntRange] with [other].
 * Returns the intersecting range, or null if the ranges don't overlap.
 *
 * @param other The other range to intersect with
 * @return An [IntRange] representing the intersection, or null if no intersection exists
 */
fun IntRange.intersect(
    other: IntRange
): IntRange? = (this as ClosedRange<Int>).intersect(other as ClosedRange<Int>) ?.let {
    it.first .. it.second
}

/**
 * Computes the intersection of this [LongRange] with [other].
 * Returns the intersecting range, or null if the ranges don't overlap.
 *
 * @param other The other range to intersect with
 * @return A [LongRange] representing the intersection, or null if no intersection exists
 */
fun LongRange.intersect(
    other: LongRange
): LongRange? = (this as ClosedRange<Long>).intersect(other as ClosedRange<Long>) ?.let {
    it.first .. it.second
}
