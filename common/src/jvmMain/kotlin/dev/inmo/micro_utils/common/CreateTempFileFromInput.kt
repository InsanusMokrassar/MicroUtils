package dev.inmo.micro_utils.common

import java.io.File
import java.io.InputStream
import java.util.UUID

fun InputStream.downloadToTempFile(
    fileName: String = UUID.randomUUID().toString(),
    fileExtension: String? = ".temp",
    folder: File? = null
) = File.createTempFile(
    fileName,
    fileExtension,
    folder
).apply {
    outputStream().use {
        copyTo(it)
    }
    deleteOnExit()
}
