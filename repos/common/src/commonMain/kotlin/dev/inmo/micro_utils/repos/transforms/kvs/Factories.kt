package dev.inmo.micro_utils.repos.transforms.kvs

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import kotlin.js.JsName
import kotlin.jvm.JvmName

/**
 * Wraps this [ReadKeyValueRepo] (mapping keys to iterables) as a [ReadKeyValuesFromKeyValueRepo],
 * exposing a one-to-many read interface.
 *
 * @param K The type of keys
 * @param V The type of individual values within each iterable
 * @param VI The iterable type storing multiple values per key
 * @return [ReadKeyValuesFromKeyValueRepo] delegating to this repo
 */
fun <K, V, VI : Iterable<V>> ReadKeyValueRepo<K, VI>.asReadKeyValuesRepo() = ReadKeyValuesFromKeyValueRepo(this)

/**
 * Wraps this [KeyValueRepo] (mapping keys to iterables) as a [KeyValuesFromKeyValueRepo],
 * exposing a full one-to-many read-write interface.
 *
 * @param K The type of keys
 * @param V The type of individual values within each iterable
 * @param VI The iterable type storing multiple values per key
 * @param listToValuesIterable Converter from [List] of values to [VI] used when persisting changes
 * @return [KeyValuesFromKeyValueRepo] delegating to this repo
 */
fun <K, V, VI : Iterable<V>> KeyValueRepo<K, VI>.asKeyValuesRepo(
    listToValuesIterable: suspend (List<V>) -> VI
): KeyValuesFromKeyValueRepo<K, V, VI> = KeyValuesFromKeyValueRepo(this, listToValuesIterable)

/**
 * Wraps this [KeyValueRepo] (mapping keys to [List]s) as a [KeyValuesFromKeyValueRepo].
 * Uses identity conversion for the list iterable.
 *
 * @param K The type of keys
 * @param V The type of individual values
 * @return [KeyValuesFromKeyValueRepo] delegating to this repo with [List] as the iterable type
 */
@JvmName("asListKeyValuesRepo")
@JsName("asListKeyValuesRepo")
fun <K, V> KeyValueRepo<K, List<V>>.asKeyValuesRepo(): KeyValuesFromKeyValueRepo<K, V, List<V>> = asKeyValuesRepo { it }

/**
 * Wraps this [KeyValueRepo] (mapping keys to [Set]s) as a [KeyValuesFromKeyValueRepo].
 * Converts lists to sets when persisting changes, ensuring value uniqueness per key.
 *
 * @param K The type of keys
 * @param V The type of individual values
 * @return [KeyValuesFromKeyValueRepo] delegating to this repo with [Set] as the iterable type
 */
@JvmName("asSetKeyValuesRepo")
@JsName("asSetKeyValuesRepo")
fun <K, V> KeyValueRepo<K, Set<V>>.asKeyValuesRepo(): KeyValuesFromKeyValueRepo<K, V, Set<V>> = asKeyValuesRepo { it.toSet() }
