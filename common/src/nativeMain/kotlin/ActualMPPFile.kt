package dev.inmo.micro_utils.common

import kotlinx.io.RawSource
import kotlinx.io.buffered
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

actual typealias MPPFile = Path

actual val MPPFile.rawSource: RawSource
    get() = SystemFileSystem.source(this)
/**
 * @suppress
 */
actual val MPPFile.filename: FileName
    get() = FileName(toString())
/**
 * @suppress
 */
actual val MPPFile.filesize: Long
    get() = SystemFileSystem.metadataOrNull(this) ?.size ?: -1L