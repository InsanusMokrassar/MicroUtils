package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.test.Test

class HandleSafelyCoroutineContextTest {
    @Test
    fun testHandleSafelyCoroutineContext() {
        val scope = CoroutineScope(Dispatchers.Default)
        var contextHandlerHappen = false
        var localHandlerHappen = false
        var defaultHandlerHappen = false
        defaultSafelyExceptionHandler = {
            defaultHandlerHappen = true
            throw it
        }
        val contextHandler: ExceptionHandler<Unit> = {
            contextHandlerHappen = true
        }
        val checkJob = scope.launch {
            safelyWithContextExceptionHandler(contextHandler) {
                safely(
                    {
                        localHandlerHappen = true
                    }
                ) {
                    error("That must happen :)")
                }
                println(coroutineContext)
                error("That must happen too:)")
            }
        }
        launchSynchronously { checkJob.join() }
        assert(contextHandlerHappen)
        assert(localHandlerHappen)
        assert(defaultHandlerHappen)
    }
}