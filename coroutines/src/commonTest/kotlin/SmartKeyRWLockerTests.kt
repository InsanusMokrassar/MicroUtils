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
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class SmartKeyRWLockerTests {
    @Test
    fun lockKeyFailedOnGlobalLockTest() = runTest {
        val locker = SmartKeyRWLocker<String>()
        val testKey = "test"
        locker.lockWrite()

        assertTrue { locker.isWriteLocked() }

        assertFails {
            realWithTimeout(1.seconds) {
                locker.lockWrite(testKey)
            }
        }
        assertFalse { locker.isWriteLocked(testKey) }

        locker.unlockWrite()
        assertFalse { locker.isWriteLocked() }

        realWithTimeout(1.seconds) {
            locker.lockWrite(testKey)
        }
        assertTrue { locker.isWriteLocked(testKey) }
        assertTrue { locker.unlockWrite(testKey) }
        assertFalse { locker.isWriteLocked(testKey) }
    }
    @Test
    fun readsBlockingGlobalWrite() = runTest {
        val locker = SmartKeyRWLocker<String>()

        val testKeys = (0 until 100).map { "test$it" }

        for (i in testKeys.indices) {
            val it = testKeys[i]
            locker.acquireRead(it)
            val previous = testKeys.take(i)
            val next = testKeys.drop(i + 1)

            previous.forEach {
                assertTrue { locker.readSemaphoreOrNull(it) ?.freePermits == Int.MAX_VALUE - 1 }
            }
            next.forEach {
                assertTrue { locker.readSemaphoreOrNull(it) ?.freePermits == null }
            }
        }

        for (i in testKeys.indices) {
            val it = testKeys[i]
            assertFails {
                realWithTimeout(13.milliseconds) { locker.lockWrite() }
            }
            realWithTimeout(1.seconds) { locker.acquireRead() }
            locker.releaseRead()
            assertTrue { locker.readSemaphore().freePermits == Int.MAX_VALUE }

            locker.releaseRead(it)
        }

        realWithTimeout(1.seconds) { locker.lockWrite() }
        assertFails {
            realWithTimeout(13.milliseconds) { locker.acquireRead() }
        }
        assertTrue { locker.unlockWrite() }
        assertTrue { locker.readSemaphore().freePermits == Int.MAX_VALUE }
    }
}
