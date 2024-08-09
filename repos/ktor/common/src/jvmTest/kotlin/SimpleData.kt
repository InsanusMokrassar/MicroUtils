package dev.inmo.micro_utils.repos.ktor

import com.benasher44.uuid.uuid4
import kotlinx.serialization.Serializable

@Serializable
data class SimpleData(
    val title: String = uuid4().toString()
)
