package dev.inmo.micro_utils.ktor.common

import dev.inmo.micro_utils.common.MPPFile
import io.ktor.utils.io.core.Input

expect fun MPPFile.input(): Input
