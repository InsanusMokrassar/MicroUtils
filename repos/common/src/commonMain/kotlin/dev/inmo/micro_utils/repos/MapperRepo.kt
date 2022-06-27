package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.common.*

@Suppress("UNCHECKED_CAST")
interface MapperRepo<FromKey, FromValue, ToKey, ToValue> {
    val keyMapper: SimpleSuspendableMapper<FromKey, ToKey>
        get() = simpleSuspendableMapper(
            { it.toInnerKey() },
            { it.toOutKey() }
        )
    val valueMapper: SimpleSuspendableMapper<FromValue, ToValue>
        get() = simpleSuspendableMapper(
            { it.toInnerValue() },
            { it.toOutValue() }
        )

    suspend fun FromKey.toOutKey() = this as ToKey
    suspend fun FromValue.toOutValue() = this as ToValue

    suspend fun ToKey.toInnerKey() = this as FromKey
    suspend fun ToValue.toInnerValue() = this as FromValue
}

inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> mapper(
    crossinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    crossinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    crossinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    crossinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
) = object : MapperRepo<FromKey, FromValue, ToKey, ToValue> {
    override suspend fun FromKey.toOutKey(): ToKey = keyFromToTo()
    override suspend fun FromValue.toOutValue(): ToValue = valueFromToTo()
    override suspend fun ToKey.toInnerKey(): FromKey = keyToToFrom()
    override suspend fun ToValue.toInnerValue(): FromValue = valueToToFrom()
}
