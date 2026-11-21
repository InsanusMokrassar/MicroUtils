package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PaginationPaging {
    @Test
    fun testPaginateOnList() {
        val list = (0 until 7).toList()
        val startPagination = FirstPagePagination(2)

        var lastPageHappened = false
        doForAllWithNextPaging(startPagination) {
            val result = list.paginate(it)

            if (result.isLastPage) {
                lastPageHappened = true
                assertTrue(result.results.size == 1)
            }

            val testSublist = list.subList(it.firstIndex, minOf(it.lastIndexExclusive, list.size))
            assertEquals(result.results, testSublist)

            result
        }

        assertTrue(lastPageHappened)
    }
    @Test
    fun testEmptyPaginateOnList() {
        val list = listOf<Int>()
        val startPagination = FirstPagePagination(2)

        var paginationHappend = false
        doForAllWithNextPaging(startPagination) {
            val resultPagination = list.paginate(it)

            assertEquals(resultPagination, emptyPaginationResult(it, list.size))

            assertFalse(paginationHappend)

            paginationHappend = true

            resultPagination
        }

        assertTrue(paginationHappend)
    }
    @Test
    fun testRightOutPaginateOnList() {
        val list = (0 until 7).toList()
        val startPagination = SimplePagination(page = 4, size = 2)

        var paginationHappend = false
        doForAllWithNextPaging(startPagination) {
            val resultPagination = list.paginate(it)

            assertEquals(resultPagination, emptyPaginationResult(it, list.size))

            assertFalse(paginationHappend)

            paginationHappend = true

            resultPagination
        }

        assertTrue(paginationHappend)
    }

    @Test
    fun testReversedPaginationWalksList() {
        assertPaginationWalk(
            listOf(1, 2, 3, 4, 5),
            reversed = true,
            expectedPages = listOf(listOf(5, 4), listOf(3, 2), listOf(1))
        )
    }

    @Test
    fun testReversedPaginationBeyondLastPage() {
        val result = listOf(1, 2, 3, 4, 5).paginate(SimplePagination(3, 2), reversed = true)

        assertEquals(PaginationResult(3, 2, emptyList<Int>(), 5L), result)
        assertNull(result.nextPageIfNotEmptyOrLastPage())
    }

    @Test
    fun testReversedPaginationOfEmptyList() {
        assertPaginationWalk(emptyList(), reversed = true, expectedPages = listOf(emptyList()))
    }

    @Test
    fun testReversedPaginationStopsOnFullLastPage() {
        assertPaginationWalk(
            listOf(4, 1, 3, 2),
            reversed = true,
            expectedPages = listOf(listOf(2, 3), listOf(1, 4))
        )
    }

    @Test
    fun testForwardPaginationWalksList() {
        assertPaginationWalk(
            listOf(1, 2, 3, 4, 5),
            reversed = false,
            expectedPages = listOf(listOf(1, 2), listOf(3, 4), listOf(5))
        )
    }

    private fun assertPaginationWalk(list: List<Int>, reversed: Boolean, expectedPages: List<List<Int>>) {
        val pageSize = 2
        var request: Pagination? = FirstPagePagination(pageSize)
        for ((page, expectedItems) in expectedPages.withIndex()) {
            val result = list.paginate(assertNotNull(request, "Expected a request for page $page"), reversed)
            assertEquals(PaginationResult(page, pageSize, expectedItems, list.size.toLong()), result)
            request = result.nextPageIfNotEmptyOrLastPage()
        }
        assertNull(request)
    }
}
