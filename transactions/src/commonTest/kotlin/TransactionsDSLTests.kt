import dev.inmo.micro_utils.transactions.doSuspendTransaction
import dev.inmo.micro_utils.transactions.rollableBackOperation
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TransactionsDSLTests {
    @Test
    fun successfulTest() = runTest {
        val dataCollections = Array(100) {
            Triple(
                it, // expected data
                false, // has rollback happen or not
                -1 // actual data
            )
        }

        val actionResult = doSuspendTransaction {
            dataCollections.forEachIndexed { i, _ ->
                val resultData = rollableBackOperation({
                    dataCollections[i] = actionResult.copy(second = true)
                }) {
                    val result = dataCollections[i]
                    dataCollections[i] = result.copy(
                        third = i
                    )
                    dataCollections[i]
                }
                assertEquals(dataCollections[i], resultData)
                assertTrue(dataCollections[i] === resultData)
            }
            true
        }.getOrThrow()

        dataCollections.forEachIndexed { i, triple ->
            assertFalse(triple.second)
            assertEquals(triple.first, i)
            assertEquals(i, triple.third)
        }
        assertTrue(actionResult)
    }
    @Test
    fun fullTest() = runTest {
        val testsCount = 100
        for (testNumber in 0 until testsCount) {
            val error = IllegalStateException("Test must fail at $testNumber")
            val dataCollections = Array(testsCount) {
                Triple(
                    it, // expected data
                    false, // has rollback happen or not
                    -1 // actual data
                )
            }

            val actionResult = doSuspendTransaction {
                dataCollections.forEachIndexed { i, _ ->
                    val resultData = rollableBackOperation({
                        assertTrue(error === this.error)
                        dataCollections[i] = actionResult.copy(second = true)
                    }) {
                        if (i == testNumber) throw error
                        val result = dataCollections[i]
                        dataCollections[i] = result.copy(
                            third = i
                        )
                        dataCollections[i]
                    }
                    assertEquals(dataCollections[i], resultData)
                    assertTrue(dataCollections[i] === resultData)
                }
                true
            }.getOrElse {
                assertTrue(it === error)
                true
            }

            dataCollections.forEachIndexed { i, triple ->
                if (i < testNumber) {
                    assertTrue(triple.second)
                    assertEquals(triple.first, i)
                    assertEquals(i, triple.third)
                } else {
                    assertFalse(triple.second)
                    assertEquals(triple.first, i)
                    assertEquals(-1, triple.third)
                }
            }
            assertTrue(actionResult)
        }
    }
}