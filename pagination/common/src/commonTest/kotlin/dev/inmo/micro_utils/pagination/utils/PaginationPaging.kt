package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
}
