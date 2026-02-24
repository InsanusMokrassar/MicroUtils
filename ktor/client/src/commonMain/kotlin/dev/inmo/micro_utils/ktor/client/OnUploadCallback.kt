package dev.inmo.micro_utils.ktor.client

/**
 * A callback function type for tracking upload progress.
 *
 * @param uploaded The number of bytes uploaded so far
 * @param count The total number of bytes to be uploaded
 */
typealias OnUploadCallback = suspend (uploaded: Long, count: Long) -> Unit
