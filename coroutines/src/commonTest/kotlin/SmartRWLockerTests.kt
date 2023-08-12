import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
}
