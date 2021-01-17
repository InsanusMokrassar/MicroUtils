package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AwaitFirstTests {
    private fun CoroutineScope.createTestDeferred(value: Int, wait: Long = 100000) = async(start = CoroutineStart.LAZY) { delay(wait); value }
    @Test
    fun testThatAwaitFirstIsWorkingCorrectly() {
        val baseScope = CoroutineScope(Dispatchers.Default)
        val resultDeferred = baseScope.createTestDeferred(-1, 0)
        val deferreds = listOf(
            baseScope.async { createTestDeferred(0) },
            baseScope.async { createTestDeferred(1) },
            baseScope.async { createTestDeferred(2) },
            resultDeferred
        )
        val controlJob = baseScope.launch {
            delay(1000000)
        }
        val result = baseScope.launchSynchronously {
            val result = deferreds.awaitFirst(baseScope)

            assertTrue(baseScope.isActive)
            assertTrue(controlJob.isActive)

            result
        }
        assertEquals(baseScope.launchSynchronously { resultDeferred.await() }, result)
        assertTrue(deferreds.all { it == resultDeferred || it.isCancelled })
    }
}