import dev.inmo.micro_utils.coroutines.SmartMutex
import dev.inmo.micro_utils.coroutines.withLock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class SmartMutexTests {
    @Test
    fun cancelledUnlockCompletesUnderContention() = runTest(timeout = 5.seconds) {
        val mutex = SmartMutex.Mutable()

        // Delegate this acquisition's release to another coroutine. The
        // unconfined collector runs while lock() still holds its internal mutex.
        val releaser = launch(Dispatchers.Unconfined) {
            mutex.lockStateFlow.first { it }
            currentCoroutineContext().cancel()
            mutex.unlock()
        }

        // Keep acquisition on the normal test dispatcher so the collector
        // attempts cancelled cleanup before the internal mutex is released.
        mutex.lock()
        releaser.join()

        assertTrue(releaser.isCancelled)
        assertFalse(mutex.isLocked, "Cancellation must not prevent the delegated unlock")
        assertTrue(mutex.tryLock())
        assertTrue(mutex.unlock())
    }

    @Test
    fun cancellingWithLockBodyReleasesMutex() = runTest(timeout = 5.seconds) {
        val mutex = SmartMutex.Mutable()
        val holder = launch(Dispatchers.Unconfined) {
            mutex.withLock {
                awaitCancellation()
            }
        }
        assertTrue(mutex.isLocked)

        holder.cancelAndJoin()

        assertFalse(mutex.isLocked)
    }

    @Test
    fun cancelledWaiterDoesNotEnterOrReleaseHeldMutex() = runTest(timeout = 5.seconds) {
        val mutex = SmartMutex.Mutable()
        var entered = false
        mutex.withLock {
            val waiter = launch(Dispatchers.Unconfined) {
                mutex.withLock {
                    entered = true
                }
            }

            waiter.cancelAndJoin()

            assertFalse(entered)
            assertTrue(mutex.isLocked)
        }
        assertFalse(mutex.isLocked)
    }
}
