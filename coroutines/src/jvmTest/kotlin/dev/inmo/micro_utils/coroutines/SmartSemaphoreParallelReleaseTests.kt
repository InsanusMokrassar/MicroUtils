package dev.inmo.micro_utils.coroutines

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class SmartSemaphoreParallelReleaseTests {
    @Test
    fun parallelSinglePermitReleasesRestoreEveryPermit() = runTest(timeout = 30.seconds) {
        assertParallelReleases(permits = 8, releasePermits = List(8) { 1 }, expectedSuccessfulReleases = 8)
    }

    @Test
    fun parallelBulkReleasesRestoreEveryPermit() = runTest(timeout = 30.seconds) {
        val releasePermits = (1 .. 8).toList()
        assertParallelReleases(
            permits = releasePermits.sum(),
            releasePermits = releasePermits,
            expectedSuccessfulReleases = releasePermits.size
        )
    }

    @Test
    fun parallelExcessReleasesStopAtCapacity() = runTest(timeout = 30.seconds) {
        assertParallelReleases(permits = 3, releasePermits = List(8) { 1 }, expectedSuccessfulReleases = 3)
        assertParallelReleases(permits = 5, releasePermits = List(8) { 3 }, expectedSuccessfulReleases = 2)
    }

    private suspend fun assertParallelReleases(
        permits: Int,
        releasePermits: List<Int>,
        expectedSuccessfulReleases: Int
    ) = coroutineScope {
        val semaphore = SmartSemaphore.Mutable(permits = permits, acquiredPermits = permits)
        val barrier = CyclicBarrier(releasePermits.size)
        val results = BooleanArray(releasePermits.size)
        val rounds = 100

        Executors.newFixedThreadPool(releasePermits.size).asCoroutineDispatcher().use { dispatcher ->
            releasePermits.mapIndexed { worker, count ->
                launch(dispatcher) {
                    repeat(rounds) { round ->
                        // Blocking barriers on dedicated JVM threads produce actual parallel releases.
                        barrier.await(5, TimeUnit.SECONDS)
                        results[worker] = semaphore.release(count)
                        barrier.await(5, TimeUnit.SECONDS)

                        if (worker == 0) {
                            assertEquals(permits, semaphore.freePermits, "Round $round must preserve released permits")
                            assertEquals(expectedSuccessfulReleases, results.count { released -> released })
                            assertFalse(semaphore.release(), "A full semaphore must reject another release")
                            assertEquals(permits, semaphore.freePermits)
                            if (round < rounds - 1) {
                                assertTrue(semaphore.tryAcquire(permits))
                                assertEquals(0, semaphore.freePermits)
                            }
                        }

                        // Finish validation and reset before starting the next release wave.
                        barrier.await(5, TimeUnit.SECONDS)
                    }
                }
            }.joinAll()
        }

        assertEquals(permits, semaphore.freePermits)
    }
}
