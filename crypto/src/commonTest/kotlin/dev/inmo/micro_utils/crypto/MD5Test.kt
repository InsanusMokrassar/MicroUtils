package dev.inmo.micro_utils.crypto

import kotlin.test.Test
import kotlin.test.assertEquals

class MD5Test {
    val source = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    val expectedMD5 = "db89bb5ceab87f9c0fcc2ab36c189c2c"
    @Test
    fun testMD5() {
        var i = 0
        source.md5().forEach {
            assertEquals(expectedMD5[i], it)
            i++
        }
    }
}
