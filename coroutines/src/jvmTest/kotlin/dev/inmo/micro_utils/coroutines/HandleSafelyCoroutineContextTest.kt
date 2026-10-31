package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HandleSafelyCoroutineContextTest {
    @Test
    fun testHandleSafelyCoroutineContext() = runTest {
        val scope = this
        var contextHandlerHappen = false
        var localHandlerHappen = false
        val checkJob = scope.launch {
            runCatchingLogging ({
                contextHandlerHappen = true
            }) {
                runCatchingLogging (
                    {
                        localHandlerHappen = true
                    }
                ) {
                    error("That must happen :)")
                }
                println(coroutineContext)
                error("That must happen too:)")
            }
        }.join()
        assert(contextHandlerHappen)
        assert(localHandlerHappen)
    }
}