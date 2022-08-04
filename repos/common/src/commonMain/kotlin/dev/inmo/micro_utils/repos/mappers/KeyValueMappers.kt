package dev.inmo.micro_utils.repos.mappers

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class MapperReadKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: ReadKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : ReadKeyValueRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
    override suspend fun get(k: FromKey): FromValue? = to.get(
        k.toOutKey()
    ) ?.toInnerValue()

    override suspend fun values(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<FromValue> = to.values(
        pagination,
        reversed
    ).let {
        PaginationResult(
            it.page,
            it.pagesNumber,
            it.results.map { it.toInnerValue() },
            it.size
        )
    }

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<FromKey> = to.keys(
        pagination,
        reversed
    ).let {
        PaginationResult(
            it.page,
            it.pagesNumber,
            it.results.map { it.toInnerKey() },
            it.size
        )
    }

    override suspend fun keys(
        v: FromValue,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<FromKey> = to.keys(
        v.toOutValue(),
        pagination,
        reversed
    ).let {
        PaginationResult(
            it.page,
            it.pagesNumber,
            it.results.map { it.toInnerKey() },
            it.size
        )
    }

    override suspend fun contains(key: FromKey): Boolean = to.contains(
        key.toOutKey()
    )

    override suspend fun count(): Long = to.count()
}

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> ReadKeyValueRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): ReadKeyValueRepo<FromKey, FromValue> = MapperReadKeyValueRepo(this, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> ReadKeyValueRepo<ToKey, ToValue>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): ReadKeyValueRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)

open class MapperWriteKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: WriteKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : WriteKeyValueRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
    override val onNewValue: Flow<Pair<FromKey, FromValue>> = to.onNewValue.map { (k, v) ->
        k.toInnerKey() to v.toInnerValue()
    }
    override val onValueRemoved: Flow<FromKey> = to.onValueRemoved.map { k ->
        k.toInnerKey()
    }

    override suspend fun set(toSet: Map<FromKey, FromValue>) = to.set(
        toSet.map { (k, v) ->
            k.toOutKey() to v.toOutValue()
        }.toMap()
    )

    override suspend fun unset(toUnset: List<FromKey>) = to.unset(
        toUnset.map { k ->
            k.toOutKey()
        }
    )

    override suspend fun unsetWithValues(toUnset: List<FromValue>) = to.unsetWithValues(
        toUnset.map { it.toOutValue() }
    )
}

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> WriteKeyValueRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): WriteKeyValueRepo<FromKey, FromValue> = MapperWriteKeyValueRepo(this, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> WriteKeyValueRepo<ToKey, ToValue>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): WriteKeyValueRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MapperKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: KeyValueRepo<ToKey, ToValue>,
    private val mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : KeyValueRepo<FromKey, FromValue>,
    MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper,
    ReadKeyValueRepo<FromKey, FromValue> by MapperReadKeyValueRepo(to, mapper),
    WriteKeyValueRepo<FromKey, FromValue> by MapperWriteKeyValueRepo(to, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> KeyValueRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): KeyValueRepo<FromKey, FromValue> = MapperKeyValueRepo(this, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> KeyValueRepo<ToKey, ToValue>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): KeyValueRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)
