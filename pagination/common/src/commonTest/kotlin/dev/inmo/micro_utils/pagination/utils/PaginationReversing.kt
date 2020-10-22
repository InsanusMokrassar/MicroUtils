package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PaginationReversingTests {
    @Test
    fun testThatCommonCaseWorksOk() {
        val pageSize = 3
        val collectionSize = 9

        assertEquals(Pagination(-1, pageSize).reverse(collectionSize), Pagination(0, 0))

        val middleFirstIndex = collectionSize / 2 - pageSize / 2
        val middleLastIndex = middleFirstIndex + pageSize - 1
        assertEquals(
            PaginationByIndexes(middleFirstIndex, middleLastIndex).reverse(collectionSize),
            PaginationByIndexes(middleFirstIndex, middleLastIndex)
        )

        assertEquals(Pagination(calculatePagesNumber(collectionSize, pageSize), pageSize).reverse(collectionSize), Pagination(0, 0))
    }
}
