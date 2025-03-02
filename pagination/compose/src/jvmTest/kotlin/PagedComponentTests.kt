import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.compose.PagedComponent
import org.jetbrains.annotations.TestOnly
import kotlin.test.Test
import kotlin.test.assertEquals

class PagedComponentTests {
    @OptIn(ExperimentalTestApi::class)
    @Test
    @TestOnly
    fun testSimpleLoad() = runComposeUiTest {
        var expectedPage = PaginationResult(
            page = 0,
            size = 1,
            results = listOf(0),
            objectsNumber = 3
        )
        var previousPage = expectedPage
        setContent {
            PagedComponent<Int>(
                initialPage = 0,
                size = 1,
                loader = {
                    previousPage = expectedPage
                    expectedPage = PaginationResult(
                        page = it.page,
                        size = it.size,
                        results = (it.firstIndex .. it.lastIndex).toList(),
                        objectsNumber = 3
                    )
                    expectedPage
                }
            ) {
                assertEquals(expectedPage, it)
                assertEquals(expectedPage.results, it.results)

                if (it.isLastPage || it.page < previousPage.page) {
                    if (it.isFirstPage) {
                        // do nothing - end of test
                    } else {
                        loadPrevious()
                    }
                } else {
                    loadNext()
                }
            }
        }

        waitForIdle()

        assertEquals(
            PaginationResult(
                page = 0,
                size = 1,
                results = listOf(0),
                objectsNumber = 3
            ),
            expectedPage
        )
    }
}