package dev.inmo.micro_utils.repos.mappers

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class MapperReadStandardKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: ReadStandardKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : ReadStandardKeyValueRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
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

    override suspend fun contains(key: FromKey): Boolean = to.contains(
        key.toOutKey()
    )

    override suspend fun count(): Long = to.count()
}

open class MapperWriteStandardKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: WriteStandardKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : WriteStandardKeyValueRepo<FromKey, FromValue>, MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper {
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
}

open class MapperStandardKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: StandardKeyValueRepo<ToKey, ToValue>,
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : StandardKeyValueRepo<FromKey, FromValue>,
    MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper,
    ReadStandardKeyValueRepo<FromKey, FromValue> by MapperReadStandardKeyValueRepo(to, mapper),
    WriteStandardKeyValueRepo<FromKey, FromValue> by MapperWriteStandardKeyValueRepo(to, mapper)
