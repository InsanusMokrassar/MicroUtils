package dev.inmo.micro_utils.ktor.client

typealias OnUploadCallback = suspend (uploaded: Long, count: Long) -> Unit
