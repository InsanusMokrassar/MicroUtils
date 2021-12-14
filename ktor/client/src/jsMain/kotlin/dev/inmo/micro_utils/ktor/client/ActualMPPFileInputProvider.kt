package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.*
import io.ktor.client.request.forms.InputProvider
import io.ktor.utils.io.core.ByteReadPacket

actual suspend fun MPPFile.inputProvider(): InputProvider = bytes().let {
    InputProvider(it.size.toLong()) {
        ByteReadPacket(it)
    }
}
