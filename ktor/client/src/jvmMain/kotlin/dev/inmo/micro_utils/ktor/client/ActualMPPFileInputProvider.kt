package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import io.ktor.client.request.forms.InputProvider
import io.ktor.utils.io.streams.asInput

actual suspend fun MPPFile.inputProvider(): InputProvider = InputProvider(length()) {
    inputStream().asInput()
}
