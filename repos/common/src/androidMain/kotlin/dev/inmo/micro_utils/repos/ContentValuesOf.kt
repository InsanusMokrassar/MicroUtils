package dev.inmo.micro_utils.repos

import androidx.core.content.contentValuesOf

@Suppress("UNCHECKED_CAST", "SimplifiableCall")
fun contentValuesOfNotNull(vararg pairs: Pair<String, Any?>?) = contentValuesOf(
    *(pairs.filter { it != null } as List<Pair<String, Any?>>).toTypedArray()
)
