package dev.inmo.micro_utils.repos.mappers

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Deprecated("Renamed", ReplaceWith("MapperReadKeyValuesRepo", "dev.inmo.micro_utils.repos.mappers.MapperReadKeyValuesRepo"))
typealias MapperReadOneToManyKeyValueRepo<FromKey, FromValue, ToKey, ToValue> = MapperReadKeyValuesRepo<FromKey, FromValue, ToKey, ToValue>
open class MapperReadKeyValuesRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: ReadKeyValuesRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : ReadKeyValuesRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
    override suspend fun get(
        k: FromKey,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<FromValue> = to.get(
        k.toOutKey(),
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

    override suspend fun contains(k: FromKey): Boolean = to.contains(k.toOutKey())
    override suspend fun contains(k: FromKey, v: FromValue): Boolean = to.contains(k.toOutKey(), v.toOutValue())

    override suspend fun count(): Long = to.count()
    override suspend fun count(k: FromKey): Long = to.count(k.toOutKey())
}

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> ReadKeyValuesRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): ReadKeyValuesRepo<FromKey, FromValue> = MapperReadKeyValuesRepo(this, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> ReadKeyValuesRepo<ToKey, ToValue>.withMapper(
    crossinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    crossinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    crossinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    crossinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): ReadKeyValuesRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)

@Deprecated("Renamed", ReplaceWith("MapperWriteKeyValuesRepo", "dev.inmo.micro_utils.repos.mappers.MapperWriteKeyValuesRepo"))
typealias MapperWriteOneToManyKeyValueRepo<FromKey, FromValue, ToKey, ToValue> = MapperWriteKeyValuesRepo<FromKey, FromValue, ToKey, ToValue>
open class MapperWriteKeyValuesRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: WriteKeyValuesRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : WriteKeyValuesRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
    override val onNewValue: Flow<Pair<FromKey, FromValue>> = to.onNewValue.map { (k, v) ->
        k.toInnerKey() to v.toInnerValue()
    }
    override val onValueRemoved: Flow<Pair<FromKey, FromValue>> = to.onValueRemoved.map { (k, v) ->
        k.toInnerKey() to v.toInnerValue()
    }
    override val onDataCleared: Flow<FromKey> = to.onDataCleared.map { k ->
        k.toInnerKey()
    }

    override suspend fun add(toAdd: Map<FromKey, List<FromValue>>) = to.add(
        toAdd.map { (k, v) ->
            k.toOutKey() to v.map { it.toOutValue() }
        }.toMap()
    )

    override suspend fun remove(toRemove: Map<FromKey, List<FromValue>>) = to.remove(
        toRemove.map { (k, v) ->
            k.toOutKey() to v.map { it.toOutValue() }
        }.toMap()
    )

    override suspend fun set(toSet: Map<FromKey, List<FromValue>>) {
        to.set(
            toSet.map { (k, vs) -> k.toOutKey() to vs.map { v -> v.toOutValue() } }.toMap()
        )
    }

    override suspend fun clear(k: FromKey) = to.clear(k.toOutKey())
    override suspend fun clearWithValue(v: FromValue) = to.clearWithValue(v.toOutValue())
}

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> WriteKeyValuesRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): WriteKeyValuesRepo<FromKey, FromValue> = MapperWriteKeyValuesRepo(this, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> WriteKeyValuesRepo<ToKey, ToValue>.withMapper(
    crossinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    crossinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    crossinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    crossinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): WriteKeyValuesRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)

@Deprecated("Renamed", ReplaceWith("MapperKeyValuesRepo", "dev.inmo.micro_utils.repos.mappers.MapperKeyValuesRepo"))
typealias MapperOneToManyKeyValueRepo<FromKey, FromValue, ToKey, ToValue> = MapperKeyValuesRepo<FromKey, FromValue, ToKey, ToValue>
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MapperKeyValuesRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: KeyValuesRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : KeyValuesRepo<FromKey, FromValue>,
    MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper,
    ReadKeyValuesRepo<FromKey, FromValue> by MapperReadKeyValuesRepo(to, mapper),
    WriteKeyValuesRepo<FromKey, FromValue> by MapperWriteKeyValuesRepo(to, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> KeyValuesRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): KeyValuesRepo<FromKey, FromValue> = MapperKeyValuesRepo(this, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> KeyValuesRepo<ToKey, ToValue>.withMapper(
    crossinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    crossinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    crossinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    crossinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): KeyValuesRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)
