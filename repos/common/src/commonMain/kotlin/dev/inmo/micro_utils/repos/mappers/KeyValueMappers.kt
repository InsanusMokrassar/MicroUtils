package dev.inmo.micro_utils.repos.mappers

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Read-only key-value repository adapter that applies type mapping via [MapperRepo].
 * Converts outer (From) key/value types to inner (To) types before delegating to [to],
 * and converts results back from inner to outer types.
 *
 * @param FromKey The outer key type exposed by this repo
 * @param FromValue The outer value type exposed by this repo
 * @param ToKey The inner key type used by the underlying [to] repo
 * @param ToValue The inner value type used by the underlying [to] repo
 * @param to The underlying [ReadKeyValueRepo] to delegate operations to
 * @param mapper The [MapperRepo] providing bidirectional key/value type conversions
 */
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
        it.changeResultsUnchecked(
            it.results.map { it.toInnerValue() }
        )
    }

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<FromKey> = to.keys(
        pagination,
        reversed
    ).let {
        it.changeResultsUnchecked(
            it.results.map { it.toInnerKey() }
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
        it.changeResultsUnchecked(
            it.results.map { it.toInnerKey() }
        )
    }

    override suspend fun contains(key: FromKey): Boolean = to.contains(
        key.toOutKey()
    )

    override suspend fun getAll(): Map<FromKey, FromValue> = to.getAll().map { (k, v) ->
        k.toInnerKey() to v.toInnerValue()
    }.toMap()

    override suspend fun count(): Long = to.count()
}

/**
 * Wraps this [ReadKeyValueRepo] with a [MapperRepo] to expose a mapped [ReadKeyValueRepo].
 *
 * @param FromKey The outer key type
 * @param FromValue The outer value type
 * @param ToKey The inner key type
 * @param ToValue The inner value type
 * @param mapper The [MapperRepo] providing bidirectional type conversions
 * @return [MapperReadKeyValueRepo] wrapping this repo
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> ReadKeyValueRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): ReadKeyValueRepo<FromKey, FromValue> = MapperReadKeyValueRepo(this, mapper)

/**
 * Wraps this [ReadKeyValueRepo] with inline conversion lambdas to expose a mapped [ReadKeyValueRepo].
 *
 * @param FromKey The outer key type
 * @param FromValue The outer value type
 * @param ToKey The inner key type
 * @param ToValue The inner value type
 * @param keyFromToTo Converts outer key to inner key; defaults to unchecked cast
 * @param valueFromToTo Converts outer value to inner value; defaults to unchecked cast
 * @param keyToToFrom Converts inner key to outer key; defaults to unchecked cast
 * @param valueToToFrom Converts inner value to outer value; defaults to unchecked cast
 * @return [MapperReadKeyValueRepo] wrapping this repo
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> ReadKeyValueRepo<ToKey, ToValue>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): ReadKeyValueRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)

/**
 * Write-only key-value repository adapter that applies type mapping via [MapperRepo].
 * Converts outer (From) key/value types to inner (To) types before delegating writes to [to],
 * and maps emitted flow values back from inner to outer types.
 *
 * @param FromKey The outer key type exposed by this repo
 * @param FromValue The outer value type exposed by this repo
 * @param ToKey The inner key type used by the underlying [to] repo
 * @param ToValue The inner value type used by the underlying [to] repo
 * @param to The underlying [WriteKeyValueRepo] to delegate operations to
 * @param mapper The [MapperRepo] providing bidirectional key/value type conversions
 */
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

/**
 * Wraps this [WriteKeyValueRepo] with a [MapperRepo] to expose a mapped [WriteKeyValueRepo].
 *
 * @param FromKey The outer key type
 * @param FromValue The outer value type
 * @param ToKey The inner key type
 * @param ToValue The inner value type
 * @param mapper The [MapperRepo] providing bidirectional type conversions
 * @return [MapperWriteKeyValueRepo] wrapping this repo
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> WriteKeyValueRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): WriteKeyValueRepo<FromKey, FromValue> = MapperWriteKeyValueRepo(this, mapper)

/**
 * Wraps this [WriteKeyValueRepo] with inline conversion lambdas to expose a mapped [WriteKeyValueRepo].
 *
 * @param FromKey The outer key type
 * @param FromValue The outer value type
 * @param ToKey The inner key type
 * @param ToValue The inner value type
 * @param keyFromToTo Converts outer key to inner key; defaults to unchecked cast
 * @param valueFromToTo Converts outer value to inner value; defaults to unchecked cast
 * @param keyToToFrom Converts inner key to outer key; defaults to unchecked cast
 * @param valueToToFrom Converts inner value to outer value; defaults to unchecked cast
 * @return [MapperWriteKeyValueRepo] wrapping this repo
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> WriteKeyValueRepo<ToKey, ToValue>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): WriteKeyValueRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)

/**
 * Full key-value repository adapter that applies type mapping via [MapperRepo].
 * Composes [MapperReadKeyValueRepo] and [MapperWriteKeyValueRepo] for read and write delegation.
 *
 * @param FromKey The outer key type exposed by this repo
 * @param FromValue The outer value type exposed by this repo
 * @param ToKey The inner key type used by the underlying [to] repo
 * @param ToValue The inner value type used by the underlying [to] repo
 * @param to The underlying [KeyValueRepo] to delegate operations to
 * @param mapper The [MapperRepo] providing bidirectional key/value type conversions
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MapperKeyValueRepo<FromKey, FromValue, ToKey, ToValue>(
    private val to: KeyValueRepo<ToKey, ToValue>,
    private val mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
) : KeyValueRepo<FromKey, FromValue>,
    MapperRepo<FromKey, FromValue, ToKey, ToValue> by mapper,
    ReadKeyValueRepo<FromKey, FromValue> by MapperReadKeyValueRepo(to, mapper),
    WriteKeyValueRepo<FromKey, FromValue> by MapperWriteKeyValueRepo(to, mapper) {
    override suspend fun clear() {
        to.clear()
    }
}

/**
 * Wraps this [KeyValueRepo] with a [MapperRepo] to expose a mapped [KeyValueRepo].
 *
 * @param FromKey The outer key type
 * @param FromValue The outer value type
 * @param ToKey The inner key type
 * @param ToValue The inner value type
 * @param mapper The [MapperRepo] providing bidirectional type conversions
 * @return [MapperKeyValueRepo] wrapping this repo
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <FromKey, FromValue, ToKey, ToValue> KeyValueRepo<ToKey, ToValue>.withMapper(
    mapper: MapperRepo<FromKey, FromValue, ToKey, ToValue>
): KeyValueRepo<FromKey, FromValue> = MapperKeyValueRepo(this, mapper)

/**
 * Wraps this [KeyValueRepo] with inline conversion lambdas to expose a mapped [KeyValueRepo].
 *
 * @param FromKey The outer key type
 * @param FromValue The outer value type
 * @param ToKey The inner key type
 * @param ToValue The inner value type
 * @param keyFromToTo Converts outer key to inner key; defaults to unchecked cast
 * @param valueFromToTo Converts outer value to inner value; defaults to unchecked cast
 * @param keyToToFrom Converts inner key to outer key; defaults to unchecked cast
 * @param valueToToFrom Converts inner value to outer value; defaults to unchecked cast
 * @return [MapperKeyValueRepo] wrapping this repo
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> KeyValueRepo<ToKey, ToValue>.withMapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
): KeyValueRepo<FromKey, FromValue> = withMapper(
    mapper(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
)
