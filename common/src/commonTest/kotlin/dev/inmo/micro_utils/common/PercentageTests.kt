package dev.inmo.micro_utils.common

import kotlin.test.Test
import kotlin.test.assertEquals

class PercentageTests {
    @Test
    fun testCompareTo() {
        val step = 0.01

        var i = Percentage.START.of1
        while (i <= Percentage.COMPLETED.of1) {
            val percentageI = Percentage(i)

            var j = Percentage.START.of1
            while (j <= Percentage.COMPLETED.of1) {
                val percentageJ = Percentage(j)

                assertEquals(percentageI.of1.compareTo(percentageJ.of1), percentageI.compareTo(percentageJ))
                assertEquals(percentageI.of1 > percentageJ.of1, percentageI > percentageJ)
                assertEquals(percentageI.of1 < percentageJ.of1, percentageI < percentageJ)

                j += step
            }

            i += step
        }
    }
}