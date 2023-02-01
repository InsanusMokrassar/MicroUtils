package dev.inmo.micro_utils.ktor.server

import com.benasher44.uuid.uuid4
import io.ktor.http.content.PartData
import io.ktor.utils.io.copyTo
import io.ktor.utils.io.core.copyTo
import io.ktor.utils.io.jvm.javaio.copyTo
import io.ktor.utils.io.streams.asOutput
import java.io.File

fun PartData.FileItem.download(target: File) {
    provider().use { input ->
        target.outputStream().asOutput().use {
            input.copyTo(it)
        }
    }
}

fun PartData.FileItem.downloadToTemporalFile(): File {
    val outputFile = File.createTempFile(uuid4().toString(), ".temp").apply {
        deleteOnExit()
    }
    download(outputFile)
    return outputFile
}

fun PartData.BinaryItem.download(target: File) {
    provider().use { input ->
        target.outputStream().use {
            input.copyTo(it.asOutput())
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
