package dev.inmo.micro_utils.repos.mappers

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.*

open class MapperReadCRUDRepo<FromId, FromRegistered, ToId, ToRegistered>(
    private val to: ReadCRUDRepo<ToRegistered, ToId>,
    mapper: MapperRepo<FromId, FromRegistered, ToId, ToRegistered>
) : ReadCRUDRepo<FromRegistered, FromId>, MapperRepo<FromId, FromRegistered, ToId, ToRegistered> by mapper {
    override suspend fun getByPagination(
        pagination: Pagination
    ): PaginationResult<FromRegistered> = to.getByPagination(
        pagination
    ).let {
        it.changeResultsUnchecked(
            it.results.map { it.toInnerValue() }
        )
    }

    override suspend fun count(): Long = to.count()

    override suspend fun contains(id: FromId): Boolean = to.contains(id.toOutKey())

    override suspend fun getById(id: FromId): FromRegistered? = to.getById(
        id.toOutKey()
    ) ?.toInnerValue()
}

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> ReadCRUDRepo<ToValue, ToKey>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): ReadCRUDRepo<FromValue, FromKey> = MapperReadCRUDRepo(this, mapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> ReadCRUDRepo<ToValue, ToKey>.withMapper(
    crossinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    crossinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    crossinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    crossinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): ReadCRUDRepo<FromValue, FromKey> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)
