import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.*
import dev.inmo.micro_utils.coroutines.SpecialMutableStateFlow
import org.jetbrains.annotations.TestOnly
import kotlin.test.Test

class FlowStateTests {
    @OptIn(ExperimentalTestApi::class)
    @Test
    @TestOnly
    fun simpleTest() = runComposeUiTest {
        val flowState = SpecialMutableStateFlow(0)
        setContent {
            Button({ flowState.value++ }) { Text("Click") }
            Text(flowState.collectAsState().value.toString())
        }

        onNodeWithText(0.toString()).assertExists()
        onNodeWithText("Click").performClick()
        onNodeWithText(1.toString()).assertExists()
    }
}