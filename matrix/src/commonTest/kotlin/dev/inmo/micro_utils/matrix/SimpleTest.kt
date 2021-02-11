package dev.inmo.micro_utils.matrix

import kotlin.test.Test
import kotlin.test.assertEquals

class SimpleTest {
    @Test
    fun simpleTest() {
        val expected = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6)
        )

        val fromMatrixWithVarargs = matrix<Int> {
            row(1, 2, 3)
            row(4, 5, 6)
        }

        val fromMatrixWithColumnsVarargs = matrix<Int> {
            row {
                columns(1, 2, 3)
            }
            row {
                columns(4, 5, 6)
            }
        }

        val fromMatrixWithSimpleAdd = matrix<Int> {
            row {
                column(1)
                column(2)
                column(3)
            }
            row {
                column(4)
                column(5)
                column(6)
            }
        }

        assertEquals(expected, fromMatrixWithVarargs)
        assertEquals(expected, fromMatrixWithColumnsVarargs)
        assertEquals(expected, fromMatrixWithSimpleAdd)
    }
}