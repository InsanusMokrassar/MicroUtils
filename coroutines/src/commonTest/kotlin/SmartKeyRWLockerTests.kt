import dev.inmo.micro_utils.coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class SmartKeyRWLockerTests {
    private lateinit var locker: SmartKeyRWLocker<String>

    @BeforeTest
    fun setup() {
        locker = SmartKeyRWLocker()
    }

    // ==================== Global Read Tests ====================

    @Test
    fun testGlobalReadAllowsMultipleConcurrentReads() = runTest {
        val results = mutableListOf<Boolean>()

        locker.acquireRead()

        val jobs = List(5) {
            launch {
                locker.acquireRead()
                delay(100.milliseconds)
                results.add(true)
                locker.releaseRead()
            }
        }

        jobs.joinAll()
        locker.releaseRead()

        assertEquals(5, results.size)
    }

    @Test
    fun testGlobalReadBlocksGlobalWrite() = runTest {
        locker.acquireRead()

        var writeAcquired = false
        val writeJob = launch {
            locker.lockWrite()
            writeAcquired = true
            locker.unlockWrite()
        }

        delay(200.milliseconds)
        assertFalse(writeAcquired, "Write should be blocked by global read")

        locker.releaseRead()
        writeJob.join()

        assertTrue(writeAcquired, "Write should succeed after read released")
    }

    @Test
    fun testGlobalReadBlocksAllKeyWrites() = runTest {
        locker.acquireRead()

        val writeFlags = mutableMapOf<String, Boolean>()
        val keys = listOf("key1", "key2", "key3")

        val jobs = keys.map { key ->
            launch {
                locker.lockWrite(key)
                writeFlags[key] = true
                locker.unlockWrite(key)
            }
        }

        delay(200.milliseconds)
        assertTrue(writeFlags.isEmpty(), "No writes should succeed while global read active")

        locker.releaseRead()
        jobs.joinAll()

        assertEquals(keys.size, writeFlags.size, "All writes should succeed after global read released")
    }

    // ==================== Global Write Tests ====================

    @Test
    fun testGlobalWriteBlocksAllOperations() = runTest {
        locker.lockWrite()

        var globalReadAcquired = false
        var keyReadAcquired = false
        var keyWriteAcquired = false

        val jobs = listOf(
            launch {
                locker.acquireRead()
                globalReadAcquired = true
                locker.releaseRead()
            },
            launch {
                locker.acquireRead("key1")
                keyReadAcquired = true
                locker.releaseRead("key1")
            },
            launch {
                locker.lockWrite("key2")
                keyWriteAcquired = true
                locker.unlockWrite("key2")
            }
        )

        delay(200.milliseconds)
        assertFalse(globalReadAcquired, "Global read should be blocked")
        assertFalse(keyReadAcquired, "Key read should be blocked")
        assertFalse(keyWriteAcquired, "Key write should be blocked")

        locker.unlockWrite()
        jobs.joinAll()

        assertTrue(globalReadAcquired)
        assertTrue(keyReadAcquired)
        assertTrue(keyWriteAcquired)
    }

    @Test
    fun testGlobalWriteIsExclusive() = runTest {
        locker.lockWrite()

        var secondWriteAcquired = false
        val job = launch {
            locker.lockWrite()
            secondWriteAcquired = true
            locker.unlockWrite()
        }

        delay(200.milliseconds)
        assertFalse(secondWriteAcquired, "Second global write should be blocked")

        locker.unlockWrite()
        job.join()

        assertTrue(secondWriteAcquired)
    }

    // ==================== Key Read Tests ====================

    @Test
    fun testKeyReadAllowsMultipleConcurrentReadsForSameKey() = runTest {
        val key = "testKey"
        val results = mutableListOf<Boolean>()

        locker.acquireRead(key)

        val jobs = List(5) {
            launch {
                locker.acquireRead(key)
                delay(50.milliseconds)
                results.add(true)
                locker.releaseRead(key)
            }
        }

        jobs.joinAll()
        locker.releaseRead(key)

        assertEquals(5, results.size)
    }

    @Test
    fun testKeyReadAllowsReadsForDifferentKeys() = runTest {
        val results = mutableMapOf<String, Boolean>()

        locker.acquireRead("key1")

        val jobs = listOf("key2", "key3", "key4").map { key ->
            launch {
                locker.acquireRead(key)
                delay(50.milliseconds)
                results[key] = true
                locker.releaseRead(key)
            }
        }

        jobs.joinAll()
        locker.releaseRead("key1")

        assertEquals(3, results.size)
    }

    @Test
    fun testKeyReadBlocksWriteForSameKey() = runTest {
        val key = "testKey"
        locker.acquireRead(key)

        var writeAcquired = false
        val job = launch {
            locker.lockWrite(key)
            writeAcquired = true
            locker.unlockWrite(key)
        }

        delay(200.milliseconds)
        assertFalse(writeAcquired, "Write for same key should be blocked")

        locker.releaseRead(key)
        job.join()

        assertTrue(writeAcquired)
    }

    @Test
    fun testKeyReadBlocksGlobalWrite() = runTest {
        locker.acquireRead("key1")

        var globalWriteAcquired = false
        val job = launch {
            locker.lockWrite()
            globalWriteAcquired = true
            locker.unlockWrite()
        }

        delay(200.milliseconds)
        assertFalse(globalWriteAcquired, "Global write should be blocked by key read")

        locker.releaseRead("key1")
        job.join()

        assertTrue(globalWriteAcquired)
    }

    @Test
    fun testKeyReadAllowsWriteForDifferentKey() = runTest {
        locker.acquireRead("key1")

        var writeAcquired = false
        val job = launch {
            locker.lockWrite("key2")
            writeAcquired = true
            locker.unlockWrite("key2")
        }

        job.join()
        assertTrue(writeAcquired, "Write for different key should succeed")

        locker.releaseRead("key1")
    }

    // ==================== Key Write Tests ====================

    @Test
    fun testKeyWriteBlocksReadForSameKey() = runTest {
        val key = "testKey"
        locker.lockWrite(key)

        var readAcquired = false
        val job = launch {
            locker.acquireRead(key)
            readAcquired = true
            locker.releaseRead(key)
        }

        delay(200.milliseconds)
        assertFalse(readAcquired, "Read for same key should be blocked")

        locker.unlockWrite(key)
        job.join()

        assertTrue(readAcquired)
    }

    @Test
    fun testKeyWriteBlocksGlobalRead() = runTest {
        locker.lockWrite("key1")

        var globalReadAcquired = false
        val job = launch {
            locker.acquireRead()
            globalReadAcquired = true
            locker.releaseRead()
        }

        delay(200.milliseconds)
        assertFalse(globalReadAcquired, "Global read should be blocked by key write")

        locker.unlockWrite("key1")
        job.join()

        assertTrue(globalReadAcquired)
    }

    @Test
    fun testKeyWriteIsExclusiveForSameKey() = runTest {
        val key = "testKey"
        locker.lockWrite(key)

        var secondWriteAcquired = false
        val job = launch {
            locker.lockWrite(key)
            secondWriteAcquired = true
            locker.unlockWrite(key)
        }

        delay(200.milliseconds)
        assertFalse(secondWriteAcquired, "Second write for same key should be blocked")

        locker.unlockWrite(key)
        job.join()

        assertTrue(secondWriteAcquired)
    }

    @Test
    fun testKeyWriteAllowsOperationsOnDifferentKeys() = runTest {
        locker.lockWrite("key1")

        val results = mutableMapOf<String, Boolean>()

        val jobs = listOf(
            launch {
                locker.acquireRead("key2")
                results["read-key2"] = true
                locker.releaseRead("key2")
            },
            launch {
                locker.lockWrite("key3")
                results["write-key3"] = true
                locker.unlockWrite("key3")
            }
        )

        jobs.joinAll()
        assertEquals(2, results.size, "Operations on different keys should succeed")

        locker.unlockWrite("key1")
    }

    // ==================== Complex Scenarios ====================

    @Test
    fun testMultipleReadersThenWriter() = runTest {
        val key = "testKey"
        val readCount = 5
        val readers = mutableListOf<Job>()

        repeat(readCount) {
            readers.add(launch {
                locker.acquireRead(key)
                delay(100.milliseconds)
                locker.releaseRead(key)
            })
        }

        delay(50.milliseconds) // Let readers acquire

        var writerExecuted = false
        val writer = launch {
            locker.lockWrite(key)
            writerExecuted = true
            locker.unlockWrite(key)
        }

        delay(50.milliseconds)
        assertFalse(writerExecuted, "Writer should wait for all readers")

        readers.joinAll()
        writer.join()

        assertTrue(writerExecuted, "Writer should execute after all readers done")
    }

    @Test
    fun testWriterThenMultipleReaders() = runTest {
        val key = "testKey"

        locker.lockWrite(key)

        val readerFlags = mutableListOf<Boolean>()
        val readers = List(5) {
            launch {
                locker.acquireRead(key)
                readerFlags.add(true)
                locker.releaseRead(key)
            }
        }

        delay(200.milliseconds)
        assertTrue(readerFlags.isEmpty(), "Readers should be blocked by writer")

        locker.unlockWrite(key)
        readers.joinAll()

        assertEquals(5, readerFlags.size, "All readers should succeed after writer")
    }

    @Test
    fun testCascadingLocksWithDifferentKeys() = runTest {
        val executed = mutableMapOf<String, Boolean>()

        launch {
            locker.lockWrite("key1")
            executed["write-key1-start"] = true
            delay(100.milliseconds)
            locker.unlockWrite("key1")
            executed["write-key1-end"] = true
        }

        delay(50.milliseconds)

        launch {
            locker.acquireRead("key2")
            executed["read-key2"] = true
            delay(100.milliseconds)
            locker.releaseRead("key2")
        }

        delay(200.milliseconds)

        assertTrue(executed["write-key1-start"] == true)
        assertTrue(executed["read-key2"] == true)
        assertTrue(executed["write-key1-end"] == true)
    }

    @Test
    fun testReleaseWithoutAcquireReturnsFalse() = runTest {
        assertFalse(locker.releaseRead(), "Release without acquire should return false")
        assertFalse(locker.releaseRead("key1"), "Release without acquire should return false")
    }

    @Test
    fun testUnlockWithoutLockReturnsFalse() = runTest {
        assertFalse(locker.unlockWrite(), "Unlock without lock should return false")
        assertFalse(locker.unlockWrite("key1"), "Unlock without lock should return false")
    }

    @Test
    fun testProperReleaseReturnsTrue() = runTest {
        locker.acquireRead()
        assertTrue(locker.releaseRead(), "Release after acquire should return true")

        locker.acquireRead("key1")
        assertTrue(locker.releaseRead("key1"), "Release after acquire should return true")
    }

    @Test
    fun testProperUnlockReturnsTrue() = runTest {
        locker.lockWrite()
        assertTrue(locker.unlockWrite(), "Unlock after lock should return true")

        locker.lockWrite("key1")
        assertTrue(locker.unlockWrite("key1"), "Unlock after lock should return true")
    }

    // ==================== Stress Tests ====================

    @Test
    fun stressTestWithMixedOperations() = runTest(timeout = 10.seconds) {
        val operations = 100
        val keys = listOf("key1", "key2", "key3", "key4", "key5")
        val jobs = mutableListOf<Job>()

        repeat(operations) { i ->
            val key = keys[i % keys.size]

            when (i % 4) {
                0 -> jobs.add(launch {
                    locker.acquireRead(key)
                    delay(10.milliseconds)
                    locker.releaseRead(key)
                })
                1 -> jobs.add(launch {
                    locker.lockWrite(key)
                    delay(10.milliseconds)
                    locker.unlockWrite(key)
                })
                2 -> jobs.add(launch {
                    locker.acquireRead()
                    delay(10.milliseconds)
                    locker.releaseRead()
                })
                3 -> jobs.add(launch {
                    locker.lockWrite()
                    delay(10.milliseconds)
                    locker.unlockWrite()
                })
            }
        }

        jobs.joinAll()
        // If we reach here without deadlock or exceptions, test passes
    }

    @Test
    fun testFairnessReadersDontStarveWriters() = runTest(timeout = 5.seconds) {
        val key = "testKey"
        var writerExecuted = false

        // Start continuous readers
        val readers = List(10) {
            launch {
                repeat(5) {
                    locker.acquireRead(key)
                    delay(50.milliseconds)
                    locker.releaseRead(key)
                    delay(10.milliseconds)
                }
            }
        }

        delay(100.milliseconds)

        // Try to acquire write lock
        val writer = launch {
            locker.lockWrite(key)
            writerExecuted = true
            locker.unlockWrite(key)
        }

        readers.joinAll()
        writer.join()

        assertTrue(writerExecuted, "Writer should eventually execute")
    }
}
