package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.await
import org.khronos.webgl.Int8Array
import org.w3c.fetch.Response
import org.w3c.files.Blob

suspend fun Blob.toByteArray() = Int8Array(
    Response(this).arrayBuffer().await()
) as ByteArray
