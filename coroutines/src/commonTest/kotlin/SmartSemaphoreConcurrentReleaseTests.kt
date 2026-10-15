import dev.inmo.micro_utils.coroutines.SmartSemaphore
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class SmartSemaphoreConcurrentReleaseTests {
    @Test
    fun queuedReleasesRestoreEveryAcquiredPermit() = runTest(timeout = 5.seconds) {
        val semaphore = SmartSemaphore.Mutable(permits = 4, acquiredPermits = 4)

        val results = releaseWithQueuedContenders(semaphore, contenderCount = 3)

        assertEquals(listOf(true, true, true), results)
        assertEquals(4, semaphore.freePermits)
        assertTrue(semaphore.tryAcquire(4))
        assertFalse(semaphore.tryAcquire())
        assertEquals(0, semaphore.freePermits)
    }

    @Test
    fun queuedReleasesRecheckCapacityAfterAcquiringTheMutex() = runTest(timeout = 5.seconds) {
        val semaphore = SmartSemaphore.Mutable(permits = 2, acquiredPermits = 2)

        val results = releaseWithQueuedContenders(semaphore, contenderCount = 3)

        assertEquals(1, results.count { released -> released })
        assertEquals(2, results.count { released -> !released })
        assertEquals(2, semaphore.freePermits)
        assertFalse(semaphore.release())
    }

    private suspend fun releaseWithQueuedContenders(
        semaphore: SmartSemaphore.Mutable,
        contenderCount: Int
    ): List<Boolean> = coroutineScope {
        val contenders = mutableListOf<Deferred<Boolean>>()
        val observer = launch(Dispatchers.Unconfined) {
            semaphore.permitsStateFlow.first { freePermits ->
                if (freePermits == 1) {
                    // The first release still holds the changes mutex while publishing.
                    // Every contender observes spare capacity before waiting for the mutex.
                    repeat(contenderCount) {
                        val contender = async(Dispatchers.Unconfined, start = CoroutineStart.UNDISPATCHED) {
                            semaphore.release()
                        }
                        contenders.add(contender)
                        assertFalse(contender.isCompleted, "Competing releases must wait for the publishing release")
                    }
                    true
                } else {
                    false
                }
            }
        }

        assertTrue(semaphore.release())
        observer.join()
        assertEquals(contenderCount, contenders.size)
        contenders.awaitAll()
    }
}
