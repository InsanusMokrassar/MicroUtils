package dev.inmo.micro_utils.common

import dev.inmo.micro_utils.common.MPPFile
import kotlin.test.Test
import kotlin.test.assertEquals

fun createTestFile(content: String): MPPFile = MPPFile(
    MPPFile.temporalDirectory!!,
    "tmp.file.txt"
).apply {
    val channel = fileChannel("wc")
    runCatching {
        channel.write(content.encodeToByteArray())
    }
    channel.close()
}
fun MPPFile.removeTestFile() {
    delete()
}

class FileTests {
    @Test
    fun testReadFromFile() {
        val testContent = "Test"
        val file = createTestFile(testContent)

        try {
            val content = runCatching {
                file.fileChannel().readFully().contentToString()
            }.getOrThrow()
            assertEquals(testContent, content)
        } finally {
            file.removeTestFile()
        }
    }
}
