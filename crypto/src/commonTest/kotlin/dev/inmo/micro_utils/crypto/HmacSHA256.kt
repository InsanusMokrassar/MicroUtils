package dev.inmo.micro_utils.crypto

import kotlin.test.*

class HmacSHA256 {
    @Test
    fun testSimpleHmacSHA256Message() {
        val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        val resultSha = text.hmacSha256("Example")
        assertEquals("5A481D59329EF862B158EEDC95392EBB22492BA3014661A3379D8201DB992484", resultSha)
    }
}
