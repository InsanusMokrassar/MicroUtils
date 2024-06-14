import dev.inmo.micro_utils.coroutines.asyncWeak
import dev.inmo.micro_utils.coroutines.launchWeak
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class WeakJobTests {
    @Test
    fun testWeakJob() = runTest {
        var commonJobDone = false
        var weakJobStarted = false
        try {
            coroutineScope {
                launch {
                    delay(1000)
                    commonJobDone = true
                }
                asyncWeak {
                    weakJobStarted = true
                    delay(100500L)
                    error("This must never happen")
                }
            }.await()
        } catch (error: Throwable) {
            assertTrue(error is CancellationException)
            assertTrue(commonJobDone)
            assertTrue(weakJobStarted)
            return@runTest
        }
        error("Cancellation exception has not been thrown")
    }
    @Test
    fun testThatWeakJobsWorksCorrectly() = runTest {
        val scope = CoroutineScope(Dispatchers.Default)
        lateinit var weakLaunchJob: Job
        lateinit var weakAsyncJob: Job
        val completeDeferred = Job()
        coroutineScope {
            weakLaunchJob = launchWeak {
                while (isActive) {
                    delay(100L)
                }
            }
            weakAsyncJob = asyncWeak {
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

        assertTrue(!weakLaunchJob.isActive)
        assertTrue(!weakAsyncJob.isActive)
    }
}