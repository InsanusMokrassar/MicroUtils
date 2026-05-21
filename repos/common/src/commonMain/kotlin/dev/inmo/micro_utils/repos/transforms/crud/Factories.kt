package dev.inmo.micro_utils.repos.transforms.crud

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import kotlin.js.JsName
import kotlin.jvm.JvmName

/**
 * Wraps this [ReadKeyValueRepo] as a [ReadCRUDFromKeyValueRepo], exposing CRUD read operations.
 *
 * @param K The type of keys (used as IDs in the CRUD repo)
 * @param V The type of values (used as objects in the CRUD repo)
 * @return [ReadCRUDFromKeyValueRepo] delegating to this repo
 */
fun <K, V> ReadKeyValueRepo<K, V>.asReadCRUDRepo() = ReadCRUDFromKeyValueRepo(this)
