package dev.inmo.micro_utils.ktor.common

import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer

val setIdsSerializer = SetSerializer(String.serializer())
