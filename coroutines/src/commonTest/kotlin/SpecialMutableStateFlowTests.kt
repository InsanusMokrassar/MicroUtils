import dev.inmo.micro_utils.coroutines.SpecialMutableStateFlow
import dev.inmo.micro_utils.coroutines.asDeferred
import dev.inmo.micro_utils.coroutines.subscribe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SpecialMutableStateFlowTests {
    @Test
    fun simpleTest() = runTest {
        val specialMutableStateFlow = SpecialMutableStateFlow(0)
        specialMutableStateFlow.value = 1
        specialMutableStateFlow.first { it == 1 }
        assertEquals(1, specialMutableStateFlow.value)
    }
    @Test
    fun specialTest() = runTest {
        val specialMutableStateFlow = SpecialMutableStateFlow(0)
        lateinit var subscriberJob: Job
        subscriberJob = specialMutableStateFlow.subscribe(this) {
            when (it) {
                1 -> specialMutableStateFlow.value = 2
                2 -> subscriberJob.cancel()
            }
        }
        specialMutableStateFlow.value = 1
        subscriberJob.join()
        assertEquals(2, specialMutableStateFlow.value)
    }
}