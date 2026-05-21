package dev.inmo.micro_utils.repos.transforms.kv

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import kotlin.js.JsName
import kotlin.jvm.JvmName

/**
 * Wraps this [ReadKeyValuesRepo] as a [ReadKeyValueFromKeyValuesRepo],
 * exposing each key mapped to a [List] of all associated values.
 *
 * @param K The type of keys
 * @param V The type of individual values
 * @return [ReadKeyValueFromKeyValuesRepo] delegating to this repo
 */
fun <K, V> ReadKeyValuesRepo<K, V>.asReadKeyValueRepo() = ReadKeyValueFromKeyValuesRepo(this)

/**
 * Wraps this [KeyValuesRepo] as a [KeyValueFromKeyValuesRepo],
 * exposing a full read-write key-value interface where each key maps to a [List] of values.
 *
 * @param K The type of keys
 * @param V The type of individual values
 * @return [KeyValueFromKeyValuesRepo] delegating to this repo
 */
fun <K, V> KeyValuesRepo<K, V>.asKeyValueRepo() = KeyValueFromKeyValuesRepo(this)

/**
 * Wraps this [ReadCRUDRepo] as a [ReadKeyValueFromCRUDRepo],
 * exposing CRUD IDs as keys and CRUD objects as values in a [ReadKeyValueRepo].
 *
 * @param K The type of CRUD IDs (used as keys)
 * @param V The type of CRUD objects (used as values)
 * @return [ReadKeyValueFromCRUDRepo] delegating to this repo
 */
fun <K, V> ReadCRUDRepo<K, V>.asReadKeyValueRepo() = ReadKeyValueFromCRUDRepo(this)
