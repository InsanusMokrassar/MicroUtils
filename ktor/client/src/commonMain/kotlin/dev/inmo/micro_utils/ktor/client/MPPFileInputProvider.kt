package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.common.filesize
import dev.inmo.micro_utils.ktor.common.input
import io.ktor.client.request.forms.InputProvider

fun MPPFile.inputProvider(): InputProvider = InputProvider(filesize) {
    input()
}
