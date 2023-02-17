package dev.inmo.micro_utils.repos.transforms.kv

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import kotlin.js.JsName
import kotlin.jvm.JvmName

fun <K, V> ReadKeyValuesRepo<K, V>.asReadKeyValueRepo() = ReadKeyValueFromKeyValuesRepo(this)

fun <K, V> KeyValuesRepo<K, V>.asKeyValueRepo() = KeyValueFromKeyValuesRepo(this)

fun <K, V> ReadCRUDRepo<K, V>.asReadKeyValueRepo() = ReadKeyValueFromCRUDRepo(this)
