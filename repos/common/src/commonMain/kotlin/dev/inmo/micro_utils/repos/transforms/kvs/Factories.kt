package dev.inmo.micro_utils.repos.transforms.kvs

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import kotlin.js.JsName
import kotlin.jvm.JvmName


fun <K, V, VI : Iterable<V>> ReadKeyValueRepo<K, VI>.asReadKeyValuesRepo() = ReadKeyValuesFromKeyValueRepo(this)

fun <K, V, VI : Iterable<V>> KeyValueRepo<K, VI>.asKeyValuesRepo(
    listToValuesIterable: suspend (List<V>) -> VI
): KeyValuesFromKeyValueRepo<K, V, VI> = KeyValuesFromKeyValueRepo(this, listToValuesIterable)

@JvmName("asListKeyValuesRepo")
@JsName("asListKeyValuesRepo")
fun <K, V> KeyValueRepo<K, List<V>>.asKeyValuesRepo(): KeyValuesFromKeyValueRepo<K, V, List<V>> = asKeyValuesRepo { it }

@JvmName("asSetKeyValuesRepo")
@JsName("asSetKeyValuesRepo")
fun <K, V> KeyValueRepo<K, Set<V>>.asKeyValuesRepo(): KeyValuesFromKeyValueRepo<K, V, Set<V>> = asKeyValuesRepo { it.toSet() }
