package dev.inmo.micro_utils.common

/**
 * Creates simple [Comparator] which will use [compareTo] of [T] for both objects
 */
fun <T : Comparable<C>, C : T> T.createComparator() = Comparator<C> { o1, o2 -> o1.compareTo(o2) }
