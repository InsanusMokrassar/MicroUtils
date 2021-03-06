package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DoWithFirstTests {
    @Test
    fun testHandleOneOf() {
        val scope = CoroutineScope(Dispatchers.Default)
        val happenedDeferreds = mutableListOf<Int>()
        val deferredWhichMustHappen = (-1).asDeferred
        scope.launchSynchronously {
            scope.launch {
                ((0 until 100).map {
                    DeferredAction(
                        scope.async { delay(10000); it },
                        happenedDeferreds::add
                    )
                } + DeferredAction(deferredWhichMustHappen, happenedDeferreds::add)).invokeFirstOf(scope)
            }.join()
        }
        assertEquals(1, happenedDeferreds.size)
        assertEquals(scope.launchSynchronously { deferredWhichMustHappen.await() }, happenedDeferreds.first())
    }
}
