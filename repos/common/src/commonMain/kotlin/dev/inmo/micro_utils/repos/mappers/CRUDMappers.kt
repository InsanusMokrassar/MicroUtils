package dev.inmo.micro_utils.repos.mappers

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): ReadCRUDRepo<FromValue, FromKey> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)

open class MapperWriteCRUDRepo<FromId, FromRegistered, FromInput, ToId, ToRegistered, ToInput>(
    private val to: WriteCRUDRepo<ToRegistered, ToId, ToInput>,
    mapper: MapperRepo<FromId, FromRegistered, ToId, ToRegistered>,
    inputMapper: SimpleSuspendableMapper<FromInput, ToInput>
) : WriteCRUDRepo<FromRegistered, FromId, FromInput>,
    MapperRepo<FromId, FromRegistered, ToId, ToRegistered> by mapper,
    SimpleSuspendableMapper<FromInput, ToInput> by inputMapper {
    override val newObjectsFlow: Flow<FromRegistered> = to.newObjectsFlow.map { it.toInnerValue() }
    override val updatedObjectsFlow: Flow<FromRegistered> = to.updatedObjectsFlow.map { it.toInnerValue() }
    override val deletedObjectsIdsFlow: Flow<FromId> = to.deletedObjectsIdsFlow.map { it.toInnerKey() }

    override suspend fun deleteById(ids: List<FromId>) = to.deleteById(ids.map { it.toOutKey() })

    override suspend fun update(
        values: List<UpdatedValuePair<FromId, FromInput>>
    ): List<FromRegistered> = to.update(
        values.map {
            it.first.toOutKey() to convert(it.second)
        }
    ).map { it.toInnerValue() }

    override suspend fun update(
        id: FromId,
        value: FromInput
    ): FromRegistered? = to.update(id.toOutKey(), convert(value)) ?.toInnerValue()

    override suspend fun create(values: List<FromInput>): List<FromRegistered> = to.create(
        values.map {
            convert(it)
        }
    ).map {
        it.toInnerValue()
    }

}

@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, FromInput, ToKey, ToValue, ToInput> WriteCRUDRepo<ToValue, ToKey, ToInput>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>,
    simpleSuspendableMapper: SimpleSuspendableMapper<FromInput, ToInput>
): WriteCRUDRepo<FromValue, FromKey, FromInput> = MapperWriteCRUDRepo(this, mapper, simpleSuspendableMapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified FromInput, reified ToKey, reified ToValue, reified ToInput> WriteCRUDRepo<ToValue, ToKey, ToInput>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline inputFromToTo: suspend FromInput.() -> ToInput = { this as ToInput },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
    noinline inputToToFrom: suspend ToInput.() -> FromInput = { this as FromInput },
): WriteCRUDRepo<FromValue, FromKey, FromInput> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom),
    simpleSuspendableMapper({ inputToToFrom(it) }, { inputFromToTo(it) })
)

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MapperCRUDRepo<FromId, FromRegistered, FromInput, ToId, ToRegistered, ToInput>(
    private val to: CRUDRepo<ToRegistered, ToId, ToInput>,
    private val mapper: MapperRepo<FromId, FromRegistered, ToId, ToRegistered>,
    private val inputMapper: SimpleSuspendableMapper<FromInput, ToInput>
) : CRUDRepo<FromRegistered, FromId, FromInput>,
    MapperRepo<FromId, FromRegistered, ToId, ToRegistered> by mapper,
    ReadCRUDRepo<FromRegistered, FromId> by MapperReadCRUDRepo(to, mapper),
    WriteCRUDRepo<FromRegistered, FromId, FromInput> by MapperWriteCRUDRepo(to, mapper, inputMapper)


@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, FromInput, ToKey, ToValue, ToInput> CRUDRepo<ToValue, ToKey, ToInput>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>,
    simpleSuspendableMapper: SimpleSuspendableMapper<FromInput, ToInput>
): CRUDRepo<FromValue, FromKey, FromInput> = MapperCRUDRepo(this, mapper, simpleSuspendableMapper)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified FromInput, reified ToKey, reified ToValue, reified ToInput> CRUDRepo<ToValue, ToKey, ToInput>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline inputFromToTo: suspend FromInput.() -> ToInput = { this as ToInput },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
    noinline inputToToFrom: suspend ToInput.() -> FromInput = { this as FromInput },
): CRUDRepo<FromValue, FromKey, FromInput> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom),
    simpleSuspendableMapper({ inputToToFrom(it) }, { inputFromToTo(it) })
)
