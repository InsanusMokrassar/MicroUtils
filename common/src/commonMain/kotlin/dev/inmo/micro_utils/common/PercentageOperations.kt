@file:Suppress(
  "RemoveRedundantCallsOfConversionMethods",
  "RedundantVisibilityModifier",
)

package dev.inmo.micro_utils.common

import kotlin.Byte
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.Suppress

public operator fun Percentage.plus(other: Percentage): Percentage = Percentage(of1 + other.of1)

public operator fun Percentage.minus(other: Percentage): Percentage = Percentage(of1 - other.of1)

public operator fun Percentage.plus(i: Byte): Percentage = Percentage((of1 + i).toDouble())

public operator fun Percentage.minus(i: Byte): Percentage = Percentage((of1 - i).toDouble())

public operator fun Percentage.times(i: Byte): Percentage = Percentage((of1 * i).toDouble())

public operator fun Percentage.div(i: Byte): Percentage = Percentage((of1 / i).toDouble())

public operator fun Percentage.rem(i: Byte): Percentage = Percentage((of1 % i).toDouble())

public operator fun Percentage.plus(i: Short): Percentage = Percentage((of1 + i).toDouble())

public operator fun Percentage.minus(i: Short): Percentage = Percentage((of1 - i).toDouble())

public operator fun Percentage.times(i: Short): Percentage = Percentage((of1 * i).toDouble())

public operator fun Percentage.div(i: Short): Percentage = Percentage((of1 / i).toDouble())

public operator fun Percentage.rem(i: Short): Percentage = Percentage((of1 % i).toDouble())

public operator fun Percentage.plus(i: Int): Percentage = Percentage((of1 + i).toDouble())

public operator fun Percentage.minus(i: Int): Percentage = Percentage((of1 - i).toDouble())

public operator fun Percentage.times(i: Int): Percentage = Percentage((of1 * i).toDouble())

public operator fun Percentage.div(i: Int): Percentage = Percentage((of1 / i).toDouble())

public operator fun Percentage.rem(i: Int): Percentage = Percentage((of1 % i).toDouble())

public operator fun Percentage.plus(i: Long): Percentage = Percentage((of1 + i).toDouble())

public operator fun Percentage.minus(i: Long): Percentage = Percentage((of1 - i).toDouble())

public operator fun Percentage.times(i: Long): Percentage = Percentage((of1 * i).toDouble())

public operator fun Percentage.div(i: Long): Percentage = Percentage((of1 / i).toDouble())

public operator fun Percentage.rem(i: Long): Percentage = Percentage((of1 % i).toDouble())

public operator fun Percentage.plus(i: Float): Percentage = Percentage((of1 + i).toDouble())

public operator fun Percentage.minus(i: Float): Percentage = Percentage((of1 - i).toDouble())

public operator fun Percentage.times(i: Float): Percentage = Percentage((of1 * i).toDouble())

public operator fun Percentage.div(i: Float): Percentage = Percentage((of1 / i).toDouble())

public operator fun Percentage.rem(i: Float): Percentage = Percentage((of1 % i).toDouble())

public operator fun Percentage.plus(i: Double): Percentage = Percentage((of1 + i).toDouble())

public operator fun Percentage.minus(i: Double): Percentage = Percentage((of1 - i).toDouble())

public operator fun Percentage.times(i: Double): Percentage = Percentage((of1 * i).toDouble())

public operator fun Percentage.div(i: Double): Percentage = Percentage((of1 / i).toDouble())

public operator fun Percentage.rem(i: Double): Percentage = Percentage((of1 % i).toDouble())

public operator fun Percentage.compareTo(other: Percentage): Int = (of1.compareTo(other.of1))
