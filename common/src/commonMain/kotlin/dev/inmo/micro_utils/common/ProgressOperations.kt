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

public operator fun Progress.plus(other: Progress): Progress = Progress(of1 + other.of1)

public operator fun Progress.minus(other: Progress): Progress = Progress(of1 - other.of1)

public operator fun Progress.plus(i: Byte): Progress = Progress((of1 + i).toDouble())

public operator fun Progress.minus(i: Byte): Progress = Progress((of1 - i).toDouble())

public operator fun Progress.times(i: Byte): Progress = Progress((of1 * i).toDouble())

public operator fun Progress.div(i: Byte): Progress = Progress((of1 / i).toDouble())

public operator fun Progress.rem(i: Byte): Progress = Progress((of1 % i).toDouble())

public operator fun Progress.plus(i: Short): Progress = Progress((of1 + i).toDouble())

public operator fun Progress.minus(i: Short): Progress = Progress((of1 - i).toDouble())

public operator fun Progress.times(i: Short): Progress = Progress((of1 * i).toDouble())

public operator fun Progress.div(i: Short): Progress = Progress((of1 / i).toDouble())

public operator fun Progress.rem(i: Short): Progress = Progress((of1 % i).toDouble())

public operator fun Progress.plus(i: Int): Progress = Progress((of1 + i).toDouble())

public operator fun Progress.minus(i: Int): Progress = Progress((of1 - i).toDouble())

public operator fun Progress.times(i: Int): Progress = Progress((of1 * i).toDouble())

public operator fun Progress.div(i: Int): Progress = Progress((of1 / i).toDouble())

public operator fun Progress.rem(i: Int): Progress = Progress((of1 % i).toDouble())

public operator fun Progress.plus(i: Long): Progress = Progress((of1 + i).toDouble())

public operator fun Progress.minus(i: Long): Progress = Progress((of1 - i).toDouble())

public operator fun Progress.times(i: Long): Progress = Progress((of1 * i).toDouble())

public operator fun Progress.div(i: Long): Progress = Progress((of1 / i).toDouble())

public operator fun Progress.rem(i: Long): Progress = Progress((of1 % i).toDouble())

public operator fun Progress.plus(i: Float): Progress = Progress((of1 + i).toDouble())

public operator fun Progress.minus(i: Float): Progress = Progress((of1 - i).toDouble())

public operator fun Progress.times(i: Float): Progress = Progress((of1 * i).toDouble())

public operator fun Progress.div(i: Float): Progress = Progress((of1 / i).toDouble())

public operator fun Progress.rem(i: Float): Progress = Progress((of1 % i).toDouble())

public operator fun Progress.plus(i: Double): Progress = Progress((of1 + i).toDouble())

public operator fun Progress.minus(i: Double): Progress = Progress((of1 - i).toDouble())

public operator fun Progress.times(i: Double): Progress = Progress((of1 * i).toDouble())

public operator fun Progress.div(i: Double): Progress = Progress((of1 / i).toDouble())

public operator fun Progress.rem(i: Double): Progress = Progress((of1 % i).toDouble())

public operator fun Progress.compareTo(other: Progress): Int = (of1 - other.of1).toInt()
