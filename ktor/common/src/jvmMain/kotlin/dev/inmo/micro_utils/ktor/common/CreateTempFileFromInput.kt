package dev.inmo.micro_utils.ktor.common

import io.ktor.utils.io.streams.*
import kotlinx.io.Source
import kotlinx.io.readTo
import java.io.File
import java.util.UUID

fun Source.downloadToTempFile(
    fileName: String = UUID.randomUUID().toString(),
    fileExtension: String? = ".temp",
    folder: File? = null
) = File.createTempFile(
    fileName,
    fileExtension,
    folder
).apply {
    outputStream().use {
        it.writePacket(this@downloadToTempFile)
    }
    deleteOnExit()
}
