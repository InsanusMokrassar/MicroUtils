package dev.inmo.micro_utils.colors.common

import kotlin.math.floor
import kotlin.math.round
import kotlin.test.Test
import kotlin.test.assertEquals

class HexColorTests {
    class TestColor(
        val color: HEXAColor,
        val shortHex: String?,
        val shortHexa: String?,
        val hex: String,
        val hexa: String,
        val r: Int,
        val g: Int,
        val b: Int,
        val a: Int,
    )
    val testColors: List<TestColor>
        get() = listOf(
            TestColor(
                color = HEXAColor(uint = 0xff0000ffu),
                shortHex = "#f00",
                shortHexa = "#f00f",
                hex = "#ff0000",
                hexa = "#ff0000ff",
                r = 0xff,
                g = 0x00,
                b = 0x00,
                a = 0xff,
            ),
            TestColor(
                color = HEXAColor(uint = 0x00ff00ffu),
                shortHex = "#0f0",
                shortHexa = "#0f0f",
                hex = "#00ff00",
                hexa = "#00ff00ff",
                r = 0x00,
                g = 0xff,
                b = 0x00,
                a = 0xff,
            ),
            TestColor(
                color = HEXAColor(0x0000ffffu),
                shortHex = "#00f",
                shortHexa = "#00ff",
                hex = "#0000ff",
                hexa = "#0000ffff",
                r = 0x00,
                g = 0x00,
                b = 0xff,
                a = 0xff,
            ),
            TestColor(
                color = HEXAColor(0xff000088u),
                shortHex = "#f00",
                shortHexa = "#f008",
                hex = "#ff0000",
                hexa = "#ff000088",
                r = 0xff,
                g = 0x00,
                b = 0x00,
                a = 0x88,
            ),
            TestColor(
                color = HEXAColor(0x00ff0088u),
                shortHex = "#0f0",
                shortHexa = "#0f08",
                hex = "#00ff00",
                hexa = "#00ff0088",
                r = 0x00,
                g = 0xff,
                b = 0x00,
                a = 0x88,
            ),
            TestColor(
                color = HEXAColor(0x0000ff88u),
                shortHex = "#00f",
                shortHexa = "#00f8",
                hex = "#0000ff",
                hexa = "#0000ff88",
                r = 0x00,
                g = 0x00,
                b = 0xff,
                a = 0x88,
            ),
            TestColor(
                color = HEXAColor(0xff000022u),
                shortHex = "#f00",
                shortHexa = "#f002",
                hex = "#ff0000",
                hexa = "#ff000022",
                r = 0xff,
                g = 0x00,
                b = 0x00,
                a = 0x22,
            ),
            TestColor(
                HEXAColor(0x00ff0022u),
                "#0f0",
                "#0f02",
                "#00ff00",
                "#00ff0022",
                0x00,
                0xff,
                0x00,
                0x22,
            ),
            TestColor(
                color = HEXAColor(0x0000ff22u),
                shortHex = "#00f",
                shortHexa = "#00f2",
                hex = "#0000ff",
                hexa = "#0000ff22",
                r = 0x00,
                g = 0x00,
                b = 0xff,
                a = 0x22,
            ),
        )

    @Test
    fun baseTest() {
        testColors.forEach {
            assertEquals(it.hex, it.color.hex)
            assertEquals(it.hexa, it.color.hexa)
            assertEquals(it.shortHex, it.color.shortHex)
            assertEquals(it.shortHexa, it.color.shortHexa)
            assertEquals(it.r, it.color.r)
            assertEquals(it.g, it.color.g)
            assertEquals(it.b, it.color.b)
            assertEquals(it.a, it.color.a)
        }
    }

    @Test
    fun testHexParseColor() {
        testColors.forEach {
            assertEquals(it.color, HEXAColor.parseStringColor(it.hexa))
            assertEquals(it.color.copy(aOfOne = 1f), HEXAColor.parseStringColor(it.hex))
            it.shortHex ?.let { _ ->
                assertEquals(it.color.copy(aOfOne = 1f), HEXAColor.parseStringColor(it.shortHex))
            }
            it.shortHexa ?.let { _ ->
                assertEquals(it.color.copy(a = floor(it.color.a.toFloat() / 16).toInt() * 0x10), HEXAColor.parseStringColor(it.shortHexa))
            }
        }
    }
}