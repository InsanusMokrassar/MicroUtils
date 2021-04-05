package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import org.junit.Test

class WeakJob {
    @Test
    fun `test that weak jobs works correctly`() {
        val scope = CoroutineScope(Dispatchers.Default)
        lateinit var weakLaunchJob: Job
        lateinit var weakAsyncJob: Job
        scope.launchSynchronously {
            val completeDeferred = Job()
            coroutineScope {
                weakLaunchJob = weakLaunch {
                    while (isActive) {
                        delay(100L)
                    }
                }
                weakAsyncJob = weakAsync {
                    while (isActive) {
                        delay(100L)
                    }
                }

                coroutineContext.job.invokeOnCompletion {
                    scope.launch {
                        delay(1000L)
                        completeDeferred.complete()
                    }
                }
                launch { delay(1000L); cancel() }
            }
            completeDeferred.join()
        }

        assert(!weakLaunchJob.isActive)
        assert(!weakAsyncJob.isActive)
    }
}
