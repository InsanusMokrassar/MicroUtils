package dev.inmo.micro_utils.common

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class Percentage private constructor(
    val of1: Double
) {
    val of1Float
        get() = of1.toFloat()
    val of100
        get() = of1 * 100
    val of100Float
        get() = of100.toFloat()
    val of100Int
        get() = of100.toInt()

    init {
        require(of1 in rangeOfValues) {
            "Progress main value should be in $rangeOfValues, but incoming value is $of1"
        }
    }

    companion object {
        val rangeOfValues = 0.0 .. 1.0

        val START = Percentage(rangeOfValues.start)
        val COMPLETED = Percentage(rangeOfValues.endInclusive)

        operator fun invoke(of1: Double) = Percentage(of1.coerceIn(rangeOfValues))
        operator fun invoke(part: Number, total: Number) = Percentage(
            part.toDouble() / total.toDouble()
        )
        fun of100(of100: Double) = Percentage(of1 = of100 / 100)
    }
}

typealias Progress = Percentage
