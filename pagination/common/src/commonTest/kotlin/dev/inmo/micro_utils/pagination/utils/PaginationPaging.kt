package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.firstIndex
import dev.inmo.micro_utils.pagination.isLastPage
import dev.inmo.micro_utils.pagination.lastIndexExclusive
import kotlin.test.Test
import kotlin.test.assertEquals
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
}
