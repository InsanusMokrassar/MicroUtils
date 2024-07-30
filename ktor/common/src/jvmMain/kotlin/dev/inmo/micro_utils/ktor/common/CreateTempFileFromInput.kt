package dev.inmo.micro_utils.ktor.common

import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.copyTo
import io.ktor.utils.io.streams.asOutput
import java.io.File
import java.io.InputStream
import java.util.UUID

fun Input.downloadToTempFile(
    fileName: String = UUID.randomUUID().toString(),
    fileExtension: String? = ".temp",
    folder: File? = null
) = File.createTempFile(
    fileName,
    fileExtension,
    folder
).apply {
    outputStream().use {
        copyTo(it.asOutput())
    }
    deleteOnExit()
}
