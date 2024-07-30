package dev.inmo.micro_utils.ktor.common

import io.ktor.utils.io.core.Input
import kotlinx.io.asSink
import java.io.File
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
        this@downloadToTempFile.transferTo(it.asSink())
    }
    deleteOnExit()
}
