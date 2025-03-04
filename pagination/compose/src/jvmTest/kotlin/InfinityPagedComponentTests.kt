import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.compose.InfinityPagedComponent
import dev.inmo.micro_utils.pagination.compose.PagedComponent
import org.jetbrains.annotations.TestOnly
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class InfinityPagedComponentTests {
    @OptIn(ExperimentalTestApi::class)
    @Test
    @TestOnly
    fun testSimpleLoad() = runComposeUiTest {
        var expectedList = listOf<Int>()
        setContent {
            InfinityPagedComponent<Int>(
                size = 1,
                loader = {
                    PaginationResult(
                        page = it.page,
                        size = it.size,
                        results = (it.firstIndex .. it.lastIndex).toList(),
                        objectsNumber = 3
                    ).also {
                        expectedList += it.results
                    }
                }
            ) {
                if (it == null) {
                    assertEquals(0, this.currentlyLoadingPage.value ?.page)
                } else {
                    assertEquals(expectedList, it)
                }

                LaunchedEffect(it ?.size) {
                    loadNext()
                }
            }
        }

        waitForIdle()

        assertContentEquals(
            listOf(0, 1, 2),
            expectedList
        )
    }
}