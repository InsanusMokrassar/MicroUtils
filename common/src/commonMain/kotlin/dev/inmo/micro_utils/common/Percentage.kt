package dev.inmo.micro_utils.common

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Contains [of1] as main value, where 100% of percentage is when of1 == 1
 *
 * @see invoke
 * @see partOfTotal
 * @see of100
 */
@Serializable
@JvmInline
value class Percentage private constructor(
    /**
     * Value of percentage. When it equals to 1, means 100%
     */
    val of1: Double
) {
    /**
     * Same as [of1], but float (using [Double.toFloat])
     */
    val of1Float
        get() = of1.toFloat()

    /**
     * Represent this percentage as common percentage where 100% is 100%
     */
    val of100
        get() = of1 * 100

    /**
     * Same as [of100], but float (using [Double.toFloat])
     */
    val of100Float
        get() = of100.toFloat()

    /**
     * Same as [of100], but int (using [Double.toInt])
     */
    val of100Int
        get() = of100.toInt()

    companion object {
        val rangeOfValues = 0.0 .. 1.0

        val START = Percentage(rangeOfValues.start)
        val COMPLETED = Percentage(rangeOfValues.endInclusive)

        operator fun invoke(of1: Double) = Percentage(of1.coerceIn(rangeOfValues))
        operator fun invoke(part: Number, total: Number) = Percentage(
            part.toDouble() / total.toDouble()
        )
        fun of1(of1: Double) = Percentage(of1 = of1)
        fun of100(of100: Double) = Percentage(of1 = of100 / 100)
        fun partOfTotal(part: Number, total: Number) = Percentage(part = part, total = total)
    }
}

typealias Progress = Percentage

/**
 * Will return [this] [Progress] if [Percentage.of1] in `0 .. 1` range
 */
fun Progress.ensureStrictOrNull(): Progress? = if (of1 in Percentage.rangeOfValues) this else null
/**
 * Will return [this] [Progress] if [Percentage.of1] in `0 .. 1` range. Otherwise, will throw error
 * [IllegalArgumentException] due to [require] failure
 */
fun Progress.ensureStrictOrThrow(): Progress {
    require(of1 in Percentage.rangeOfValues) {
        "For strict checks value of percentage must be in ${Percentage.rangeOfValues}, but actual value is $of1"
    }
    return this
}
