package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PaginationReversingTests {
    @Test
    fun testThatCommonCaseWorksOk() {
        val pageSize = 2
        val collectionSize = 8
        val pages = calculatePage(collectionSize, pageSize)

        doWithPagination(FirstPagePagination(pageSize)) {
            val reversed = it.reverse(collectionSize.toLong())
            assertEquals(Pagination(calculatePage(collectionSize - it.firstIndex - it.size, it.size), it.size), reversed)
            if (it.page < pages) {
                it.nextPage()
            } else {
                null
            }
        }
    }
}
