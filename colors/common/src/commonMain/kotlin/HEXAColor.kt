package dev.inmo.micro_utils.colors.common

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.math.floor

/**
 * Wrapper for RGBA colors. Receiving [UInt] in main constructor. Each part in main constructor
 * configured with `0x00 - 0xff` range. Examples:
 *
 * * Red: `0xff0000ffu`
 * * Red (0.5 capacity): `0xff000088u`
 *
 * Anyway it is recommended to use
 */
@Serializable
@JvmInline
value class HEXAColor (
    val uint: UInt
) : Comparable<HEXAColor> {
    val hexa: String
        get() = "#${uint.toString(16).padStart(8, '0')}"
    val hex: String
        get() = hexa.take(7)
    val rgba: String
        get() = "rgba($r,$g,$b,${aOfOne.toString().take(5)})"
    val rgb: String
        get() = "rgb($r,$g,$b)"
    val shortHex: String
        get() = "#${r.shortPart()}${g.shortPart()}${b.shortPart()}"
    val shortHexa: String
        get() = "$shortHex${a.shortPart()}"
    val rgbInt: Int
        get() = (uint shr 2).toInt()

    val r: Int
        get() = ((uint and 0xff000000u) / 0x1000000u).toInt()
    val g: Int
        get() = ((uint and 0x00ff0000u) / 0x10000u).toInt()
    val b: Int
        get() = ((uint and 0x0000ff00u) / 0x100u).toInt()
    val a: Int
        get() = ((uint and 0x000000ffu)).toInt()
    val aOfOne: Float
        get() = a.toFloat() / (0xff)
    init {
        require(uint in 0u ..0xffffffffu)
    }

    constructor(r: Int, g: Int, b: Int, a: Int) : this(
        ((r * 0x1000000).toLong() + g * 0x10000 + b * 0x100 + a).toUInt()
    ) {
        require(r in 0 ..0xff)
        require(g in 0 ..0xff)
        require(b in 0 ..0xff)
        require(a in 0 ..0xff)
    }

    constructor(r: Int, g: Int, b: Int, aOfOne: Float = 1f) : this(
        r = r, g = g, b = b, a = (aOfOne * 0xff).toInt()
    )

    override fun toString(): String {
        return hexa
    }

    override fun compareTo(other: HEXAColor): Int = (uint - other.uint).coerceIn(Int.MIN_VALUE.toUInt(), Int.MAX_VALUE.toLong().toUInt()).toInt()

    fun copy(
        r: Int = this.r,
        g: Int = this.g,
        b: Int = this.b,
        aOfOne: Float = this.aOfOne
    ) = HEXAColor(r = r, g = g, b = b, aOfOne = aOfOne)
    fun copy(
        r: Int = this.r,
        g: Int = this.g,
        b: Int = this.b,
        a: Int
    ) = HEXAColor(r = r, g = g, b = b, a = a)

    companion object {
        /**
         * Parsing color from [color]
         *
         * Supported formats samples (on Red color based):
         *
         * * `#f00`
         * * `#f00f`
         * * `#ff0000`
         * * `#ff0000ff`
         * * `rgb(255, 0, 0)`
         * * `rgba(255, 0, 0, 1)`
         */
        fun parseStringColor(color: String): HEXAColor = when {
            color.startsWith("#") -> color.removePrefix("#").let { color ->
                when (color.length) {
                    3 -> color.map { "$it$it" }.joinToString(separator = "", postfix = "ff")
                    4 -> color.take(3).map { "$it$it" }.joinToString(separator = "", postfix = color.takeLast(1).let { "${it}0" })
                    6 -> "${color}ff"
                    8 -> color
                    else -> error("Malfurmed color string: $color. It is expected that color started with # will contains 3, 6 or 8 valuable parts")
                }
            }
            color.startsWith("rgb(") -> color
                .removePrefix("rgb(")
                .removeSuffix(")")
                .replace(Regex("\\s"), "")
                .split(",")
                .joinToString("", postfix = "ff") {
                    it.toInt().toString(16).padStart(2, '0')
                }
            color.startsWith("rgba(") -> color
                .removePrefix("rgba(")
                .removeSuffix(")")
                .replace(Regex("\\s"), "")
                .split(",").let {
                    it.take(3).map { it.toInt().toString(16).padStart(2, '0') } + (it.last().toFloat() * 0xff).toInt().toString(16).padStart(2, '0')
                }
                .joinToString("")
            else -> color
        }.lowercase().toUInt(16).let(::HEXAColor)

        /**
         * Parsing color from [color]
         *
         * Supported formats samples (on Red color based):
         *
         * * `#f00`
         * * `#ff0000`
         * * `#ff0000ff`
         * * `rgb(255, 0, 0)`
         * * `rgba(255, 0, 0, 1)`
         */
        operator fun invoke(color: String) = parseStringColor(color)

        private fun Int.shortPart(): String {
            return (floor(toFloat() / 16)).toInt().toString(16)
        }
    }
}
