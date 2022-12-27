package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals

class LaunchInCurrentThreadTests {
    @Test
    fun simpleTestThatLaunchInCurrentThreadWorks() {
        val expectedResult = 10
        val result = launchInCurrentThread {
            expectedResult
        }
        assertEquals(expectedResult, result)
    }
    @Test
    fun simpleTestThatSeveralLaunchInCurrentThreadWorks() {
        val testData = 0 until 100

        testData.forEach {
            val result = launchInCurrentThread {
                it
            }
            assertEquals(it, result)
        }
    }
    @Test
    fun simpleTestThatLaunchInCurrentThreadWillCorrectlyHandleSuspensionsWorks() {
        val testData = 0 until 100

        suspend fun test(data: Any): Any {
            return withContext(Dispatchers.Default) {
                delay(1)
                data
            }
        }

        testData.forEach {
            val result = launchInCurrentThread {
                test(it)
            }
            assertEquals(it, result)
        }
    }
}
