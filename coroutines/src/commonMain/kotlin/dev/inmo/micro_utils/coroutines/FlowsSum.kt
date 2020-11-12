@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

inline operator fun <T> Flow<T>.plus(other: Flow<T>) = merge(this, other)
