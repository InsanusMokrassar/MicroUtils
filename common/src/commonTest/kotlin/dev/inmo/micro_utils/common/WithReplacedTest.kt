package dev.inmo.micro_utils.common

import kotlin.test.Test
import kotlin.test.assertEquals

class WithReplacedTest {
    @Test
    fun testReplaced() {
        val data = 0 until 10
        val testData = Int.MAX_VALUE

        for (i in 0 until data.last) {
            val withReplaced = data.withReplacedAt(i) {
                testData
            }
            val dataAsMutableList = data.toMutableList()
            dataAsMutableList[i] = testData
            assertEquals(withReplaced, dataAsMutableList.toList())
        }
    }
}