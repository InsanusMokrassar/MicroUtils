package dev.inmo.micro_utils.ktor.common

import io.ktor.utils.io.core.*
import kotlinx.io.asSink
import java.io.File
import java.util.UUID
import kotlin.io.use

fun Input.downloadToTempFile(
    fileName: String = UUID.randomUUID().toString(),
    fileExtension: String? = ".temp",
    folder: File? = null
) = File.createTempFile(
    fileName,
    fileExtension,
    folder
).apply {
    outputStream().use { output ->
        this@downloadToTempFile.transferTo(output.asSink())
    }
    deleteOnExit()
}
