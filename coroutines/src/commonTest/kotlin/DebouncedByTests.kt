import dev.inmo.micro_utils.coroutines.debouncedBy
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DebouncedByTests {
    @Test
    fun testThatParallelDebouncingWorksCorrectly() = runTest {
        val dataToMarkerFactories = listOf(
            1 to 0,
            2 to 1,
            3 to 2,
            4 to 0,
            5 to 1,
            6 to 2,
            7 to 0,
            8 to 1,
            9 to 2,
        )

        val collected = mutableListOf<Int>()

        dataToMarkerFactories.asFlow().debouncedBy(10L) {
            it.second
        }.collect {
            when (it.second) {
                0 -> assertEquals(7, it.first)
                1 -> assertEquals(8, it.first)
                2 -> assertEquals(9, it.first)
                else -> error("wtf")
            }
            collected.add(it.first)
        }

        val expectedList = listOf(7, 8, 9)
        assertEquals(expectedList, collected)
        assertTrue { collected.containsAll(expectedList) }
        assertTrue { expectedList.containsAll(collected) }
    }
}