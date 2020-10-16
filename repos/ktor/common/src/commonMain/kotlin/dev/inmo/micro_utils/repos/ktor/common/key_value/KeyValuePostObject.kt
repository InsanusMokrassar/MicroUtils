package dev.inmo.micro_utils.repos.ktor.common.key_value

import kotlinx.serialization.Serializable

@Serializable
data class KeyValuePostObject<K, V> (
    val key: K,
    val value: V,
)