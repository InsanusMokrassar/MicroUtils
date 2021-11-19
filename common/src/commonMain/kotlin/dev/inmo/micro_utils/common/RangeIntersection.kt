package dev.inmo.micro_utils.common

fun <T : Comparable<T>> ClosedRange<T>.intersect(other: ClosedRange<T>): Pair<T, T>? = when {
    start == other.start && endInclusive == other.endInclusive -> start to endInclusive
    start > other.endInclusive || other.start > endInclusive -> null
    else -> maxOf(start, other.start) to minOf(endInclusive, other.endInclusive)
}

fun IntRange.intersect(
    other: IntRange
): IntRange? = (this as ClosedRange<Int>).intersect(other as ClosedRange<Int>) ?.let {
    it.first .. it.second
}

fun LongRange.intersect(
    other: LongRange
): LongRange? = (this as ClosedRange<Long>).intersect(other as ClosedRange<Long>) ?.let {
    it.first .. it.second
}
