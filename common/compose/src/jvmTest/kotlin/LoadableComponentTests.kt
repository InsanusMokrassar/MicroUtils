import androidx.compose.runtime.remember
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import dev.inmo.micro_utils.common.compose.LoadableComponent
import dev.inmo.micro_utils.coroutines.MutableRedeliverStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import org.jetbrains.annotations.TestOnly
import kotlin.test.Test
import kotlin.test.assertTrue

class LoadableComponentTests {
    @OptIn(ExperimentalTestApi::class)
    @Test
    @TestOnly
    fun testSimpleLoad() = runComposeUiTest {
        val loadingFlow = MutableRedeliverStateFlow<Int>(0)
        val loadedFlow = MutableRedeliverStateFlow<Int>(0)
        setContent {
            LoadableComponent<Int>({
                loadingFlow.filter { it == 1 }.first()
            }) {
                assert(dataState.value.data == 1)
                remember {
                    loadedFlow.value = 2
                }
            }
        }

        waitForIdle()

        assertTrue(loadedFlow.value == 0)

        loadingFlow.value = 1

        waitForIdle()

        assertTrue(loadedFlow.value == 2)
    }
}