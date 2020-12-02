package dev.inmo.micro_utils.coroutines

import kotlin.test.Test
import kotlin.test.assertEquals

class LaunchSynchronouslyTest {
    @Test
    fun testRunInCoroutine() {
        (0 .. 10000).forEach {
            assertEquals(it, launchSynchronously { it })
        }
    }
}