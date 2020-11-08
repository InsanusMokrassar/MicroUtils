package dev.inmo.micro_utils.repos.mappers

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class MapperReadOneToManyKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: ReadOneToManyKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : ReadOneToManyKeyValueRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
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

    override suspend fun contains(k: FromKey): Boolean = to.contains(k.toOutKey())
    override suspend fun contains(k: FromKey, v: FromValue): Boolean = to.contains(k.toOutKey(), v.toOutValue())

    override suspend fun count(): Long = to.count()
    override suspend fun count(k: FromKey): Long = to.count(k.toOutKey())
}

open class MapperWriteOneToManyKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: WriteOneToManyKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : WriteOneToManyKeyValueRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
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

    override suspend fun clear(k: FromKey) = to.clear(k.toOutKey())
}

open class MapperOneToManyKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: OneToManyKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : OneToManyKeyValueRepo<FromKey, FromValue>,
    MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper,
    ReadOneToManyKeyValueRepo<FromKey, FromValue> by MapperReadOneToManyKeyValueRepo(to, mapper),
    WriteOneToManyKeyValueRepo<FromKey, FromValue> by MapperWriteOneToManyKeyValueRepo(to, mapper)
