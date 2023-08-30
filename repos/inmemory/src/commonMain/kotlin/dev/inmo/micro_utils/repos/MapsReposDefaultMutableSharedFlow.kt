package dev.inmo.micro_utils.repos

import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> MapsReposDefaultMutableSharedFlow() = MutableSharedFlow<T>(
    extraBufferCapacity = Int.MAX_VALUE
)