import dev.inmo.micro_utils.coroutines.asyncWeak
import dev.inmo.micro_utils.coroutines.launchWeak
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
}