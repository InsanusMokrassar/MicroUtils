import dev.inmo.micro_utils.coroutines.SmartSemaphore
import dev.inmo.micro_utils.coroutines.withAcquire
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class SmartSemaphoreTests {
    @Test
    fun cancelledHolderReleasesPermitUnderContention() = runTest(timeout = 5.seconds) {
        val semaphore = SmartSemaphore.Mutable(permits = 2)
        val holder = launch(Dispatchers.Unconfined) {
            semaphore.withAcquire {
                awaitCancellation()
            }
        }
        assertEquals(1, semaphore.freePermits)

        // The synchronous observer cancels the holder while the second
        // acquisition still owns the semaphore's internal changes mutex.
        val cancellation = launch(Dispatchers.Unconfined) {
            semaphore.permitsStateFlow.first { it == 0 }
            holder.cancel()
        }
        semaphore.withAcquire {
            cancellation.join()
            holder.join()
            assertTrue(holder.isCancelled)
            assertEquals(1, semaphore.freePermits, "The cancelled holder must return its permit")
        }
        assertEquals(2, semaphore.freePermits)
    }

    @Test
    fun cancelledAcquireReturnsPartialPermitsUnderContention() = runTest(timeout = 5.seconds) {
        // One permit belongs to another holder; the waiter can acquire two
        // permits immediately, but must wait for the third.
        val semaphore = SmartSemaphore.Mutable(permits = 3, acquiredPermits = 1)
        lateinit var waiter: kotlinx.coroutines.Job
        val cancellation = launch(Dispatchers.Unconfined) {
            semaphore.permitsStateFlow.first { it == 1 }
            waiter.cancel()
        }
        waiter = launch(Dispatchers.Unconfined) {
            semaphore.acquire(3)
        }
        assertEquals(0, semaphore.freePermits)
        assertFalse(waiter.isCompleted)

        // Publishing this release resumes the observer while the internal
        // mutex is held. The cancelled acquire must wait to roll back safely.
        semaphore.release()
        cancellation.join()
        waiter.join()
        assertTrue(waiter.isCancelled)
        assertEquals(3, semaphore.freePermits, "Cancellation must return both partially acquired permits")
        semaphore.withAcquire(3) {
            assertEquals(0, semaphore.freePermits)
        }
        assertEquals(3, semaphore.freePermits)
    }

    @Test
    fun cancelledAcquireWithoutPermitsDoesNotReleaseAnotherHoldersPermit() = runTest(timeout = 5.seconds) {
        val semaphore = SmartSemaphore.Mutable(permits = 1, acquiredPermits = 1)
        val waiter = launch(Dispatchers.Unconfined) {
            semaphore.acquire()
        }
        assertFalse(waiter.isCompleted)

        waiter.cancelAndJoin()
        assertEquals(0, semaphore.freePermits, "A cancelled waiter that acquired nothing must release nothing")
        assertTrue(semaphore.release())
        assertEquals(1, semaphore.freePermits)
    }

    @Test
    fun tryAcquireUsesAvailablePermits() = runTest {
        val semaphore = SmartSemaphore.Mutable(permits = 3)
        assertTrue(semaphore.tryAcquire(2))
        assertEquals(1, semaphore.freePermits)
        assertTrue(semaphore.tryAcquire())
        assertEquals(0, semaphore.freePermits)
        assertTrue(semaphore.release(3))
        assertEquals(3, semaphore.freePermits)
    }

    @Test
    fun tryAcquireWithInsufficientPermitsLeavesStateUnchanged() = runTest {
        val semaphore = SmartSemaphore.Mutable(permits = 3, acquiredPermits = 2)
        assertFalse(semaphore.tryAcquire(2))
        assertEquals(1, semaphore.freePermits)
        semaphore.acquire()
        assertFalse(semaphore.tryAcquire())
        assertEquals(0, semaphore.freePermits)
    }
}
