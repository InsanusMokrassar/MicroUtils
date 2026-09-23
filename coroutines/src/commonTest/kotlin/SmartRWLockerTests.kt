import dev.inmo.micro_utils.coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds

class SmartRWLockerTests {
    @Test
    fun compositeTest() {
        val locker = SmartRWLocker()

        val readAndWriteWorkers = 10
        runTest {
            var started = 0
            var done = 0
            val doneMutex = Mutex()
            val readWorkers = (0 until readAndWriteWorkers).map {
                launch(start = CoroutineStart.LAZY) {
                    locker.withReadAcquire {
                        doneMutex.withLock {
                            started++
                        }
                        delay(100L)
                        doneMutex.withLock {
                            done++
                        }
                    }
                }
            }

            var doneWrites = 0

            val writeWorkers = (0 until readAndWriteWorkers).map {
                launch(start = CoroutineStart.LAZY) {
                    locker.withWriteLock {
                        assertTrue(done == readAndWriteWorkers || started == 0)
                        delay(10L)
                        doneWrites++
                    }
                }
            }
            readWorkers.forEach { it.start() }
            writeWorkers.forEach { it.start() }

            readWorkers.joinAll()
            writeWorkers.joinAll()

            assertEquals(expected = readAndWriteWorkers, actual = done)
            assertEquals(expected = readAndWriteWorkers, actual = doneWrites)
        }
    }

    @Test
    fun simpleWithWriteLockTest() {
        val locker = SmartRWLocker()

        runTest {
            locker.withWriteLock {
                assertEquals(0, locker.readSemaphore.freePermits)
                assertEquals(true, locker.writeMutex.isLocked)
            }
            assertEquals(Int.MAX_VALUE, locker.readSemaphore.freePermits)
            assertEquals(false, locker.writeMutex.isLocked)
        }
    }

    @Test
    fun failureWithWriteLockTest() {
        val locker = SmartRWLocker()

        val exception = IllegalArgumentException()
        try {
            runTest {
                val subscope = kotlinx.coroutines.CoroutineScope(this.coroutineContext)
                var happenException: Throwable? = null
                try {
                    locker.withWriteLock {
                        val checkFunction = fun (): Deferred<Unit> {
                            return subscope.async {
                                assertEquals(0, locker.readSemaphore.freePermits)
                                assertEquals(true, locker.writeMutex.isLocked)
                                throw exception
                            }
                        }
                        doInDefault {
                            assertEquals(0, locker.readSemaphore.freePermits)
                            assertEquals(true, locker.writeMutex.isLocked)
                            checkFunction().await()
                        }
                    }
                } catch (e: Exception) {
                    happenException = e
                }
                if (exception != happenException) {
                    assertEquals(exception, happenException ?.cause)
                }
                assertEquals(Int.MAX_VALUE, locker.readSemaphore.freePermits)
                assertEquals(false, locker.writeMutex.isLocked)
            }
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }

    @Test
    fun failureOnReadFreeingRead() = runTest {
        val locker = SmartRWLocker()
        val job = launch {
            locker.withReadAcquire {
                while (isActive) {
                    delay(1.days)
                }
            }
        }

        locker.readSemaphore.permitsStateFlow.first {
            it == locker.readSemaphore.maxPermits - 1
        }
        job.cancelAndJoin()
        locker.readSemaphore.permitsStateFlow.first {
            it == locker.readSemaphore.maxPermits
        }
    }

    @Test
    fun cancelledReaderReleasesPermitUnderContention() = runTest(timeout = 5.seconds) {
        val locker = SmartRWLocker(readPermits = 2)
        val reader = launch(Dispatchers.Unconfined) {
            locker.withReadAcquire {
                awaitCancellation()
            }
        }
        assertEquals(1, locker.readSemaphore.freePermits)

        // Observe the second acquisition synchronously while it still holds the
        // semaphore's internal mutex. Cancelling the unconfined reader makes its
        // cleanup contend for that mutex before the acquisition can release it.
        val cancellation = launch(Dispatchers.Unconfined) {
            locker.readSemaphore.permitsStateFlow.first { it == 0 }
            reader.cancel()
        }

        // Keep this acquisition on the normal test dispatcher: making it
        // unconfined would change the ordering that forces cleanup contention.
        locker.withReadAcquire {
            cancellation.join()
            reader.join()
            assertTrue(reader.isCancelled)
            assertEquals(
                1,
                locker.readSemaphore.freePermits,
                "The cancelled reader must release its permit while the other reader still holds one"
            )
        }
        assertEquals(2, locker.readSemaphore.freePermits)
    }

    @Test
    fun simpleWithReadAcquireTest() {
        val locker = SmartRWLocker()

        runTest {
            locker.withReadAcquire {
                assertEquals(Int.MAX_VALUE - 1, locker.readSemaphore.freePermits)
                assertEquals(false, locker.writeMutex.isLocked)
                locker.withReadAcquire {
                    assertEquals(Int.MAX_VALUE - 2, locker.readSemaphore.freePermits)
                    assertEquals(false, locker.writeMutex.isLocked)
                }
            }
            assertEquals(Int.MAX_VALUE, locker.readSemaphore.freePermits)
            assertEquals(false, locker.writeMutex.isLocked)
        }
    }

    @Test
    fun simple2WithWriteLockTest() {
        val locker = SmartRWLocker()

        val unlockDelay = 1000L // 1 sec
        var unlocked: Boolean = false
        runTest {
            launch {
                locker.withReadAcquire {
                    delay(unlockDelay)
                }
                unlocked = true
            }
            locker.readSemaphore.permitsStateFlow.first { it == Int.MAX_VALUE - 1 }
            assertEquals(false, unlocked)
            locker.withWriteLock {
                assertEquals(true, unlocked)
                assertEquals(0, locker.readSemaphore.freePermits)
                assertEquals(true, locker.writeMutex.isLocked)
            }
            assertEquals(Int.MAX_VALUE, locker.readSemaphore.freePermits)
            assertEquals(false, locker.writeMutex.isLocked)
        }
    }

    @Test
    fun exceptionOnLockingWillNotLockLocker() = runTest {
        val locker = SmartRWLocker()

        locker.acquireRead()
        assertFails {
            realWithTimeout(1.seconds) {
                locker.lockWrite()
            }
        }
        assertFalse { locker.writeMutex.isLocked }
    }
}
