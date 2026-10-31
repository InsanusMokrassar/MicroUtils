import dev.inmo.micro_utils.coroutines.MutableRedeliverStateFlow
import dev.inmo.micro_utils.coroutines.subscribe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecialMutableStateFlowTests {
    @Test
    fun simpleTest() = runTest {
        val mutableRedeliverStateFlow = MutableRedeliverStateFlow(0)
        mutableRedeliverStateFlow.value = 1
        mutableRedeliverStateFlow.first { it == 1 }
        assertEquals(1, mutableRedeliverStateFlow.value)
    }
    @Test
    fun specialTest() = runTest {
        val mutableRedeliverStateFlow = MutableRedeliverStateFlow(0)
        lateinit var subscriberJob: Job
        subscriberJob = mutableRedeliverStateFlow.subscribe(this) {
            when (it) {
                1 -> mutableRedeliverStateFlow.value = 2
                2 -> subscriberJob.cancel()
            }
        }
        mutableRedeliverStateFlow.value = 1
        subscriberJob.join()
        assertEquals(2, mutableRedeliverStateFlow.value)
    }
}