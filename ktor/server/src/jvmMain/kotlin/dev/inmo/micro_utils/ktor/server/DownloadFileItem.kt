package dev.inmo.micro_utils.ktor.server

import com.benasher44.uuid.uuid4
import io.ktor.http.content.PartData
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.copyTo
import kotlinx.io.asSink
import java.io.File

suspend fun PartData.FileItem.download(target: File) {
    provider().copyAndClose(
        target.writeChannel()
    )
}

suspend fun PartData.FileItem.downloadToTemporalFile(): File {
    val outputFile = File.createTempFile(uuid4().toString(), ".temp").apply {
        deleteOnExit()
    }
    download(outputFile)
    return outputFile
}

fun PartData.BinaryItem.download(target: File) {
    provider().use { input ->
        target.outputStream().use {
            input.transferTo(it.asSink())
        }
    }
}

fun PartData.BinaryItem.downloadToTemporalFile(): File {
    val outputFile = File.createTempFile(uuid4().toString(), ".temp").apply {
        deleteOnExit()
    }
    download(outputFile)
    return outputFile
}

suspend fun PartData.BinaryChannelItem.download(target: File) {
    val input = provider()
    target.outputStream().use {
        input.copyTo(it)
    }
}

suspend fun PartData.BinaryChannelItem.downloadToTemporalFile(): File {
    val outputFile = File.createTempFile(uuid4().toString(), ".temp").apply {
        deleteOnExit()
    }
    download(outputFile)
    return outputFile
}
