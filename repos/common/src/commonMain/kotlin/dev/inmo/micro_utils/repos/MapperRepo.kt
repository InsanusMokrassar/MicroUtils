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

    companion object
}

class SimpleMapperRepo<FromKey, FromValue, ToKey, ToValue>(
    private val keyFromToTo: suspend FromKey.() -> ToKey,
    private val valueFromToTo: suspend FromValue.() -> ToValue,
    private val keyToToFrom: suspend ToKey.() -> FromKey,
    private val valueToToFrom: suspend ToValue.() -> FromValue
) : MapperRepo<FromKey, FromValue, ToKey, ToValue> {
    override suspend fun FromKey.toOutKey(): ToKey = keyFromToTo()
    override suspend fun FromValue.toOutValue(): ToValue = valueFromToTo()
    override suspend fun ToKey.toInnerKey(): FromKey = keyToToFrom()
    override suspend fun ToValue.toInnerValue(): FromValue = valueToToFrom()
}

operator fun <FromKey, FromValue, ToKey, ToValue> MapperRepo.Companion.invoke(
    keyFromToTo: suspend FromKey.() -> ToKey,
    valueFromToTo: suspend FromValue.() -> ToValue,
    keyToToFrom: suspend ToKey.() -> FromKey,
    valueToToFrom: suspend ToValue.() -> FromValue
) = SimpleMapperRepo(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)

inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> mapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
) = MapperRepo(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
