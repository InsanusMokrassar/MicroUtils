package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.common.filesize
import dev.inmo.micro_utils.ktor.common.input
import io.ktor.client.request.forms.InputProvider

/**
 * Creates a Ktor [InputProvider] from this multiplatform file for use in HTTP client requests.
 * The input provider knows the file size and can create input streams on demand.
 *
 * @return An [InputProvider] for reading this file in HTTP requests
 */
fun MPPFile.inputProvider(): InputProvider = InputProvider(filesize) {
    input()
}
