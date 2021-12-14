package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import io.ktor.client.request.forms.InputProvider

expect suspend fun MPPFile.inputProvider(): InputProvider
