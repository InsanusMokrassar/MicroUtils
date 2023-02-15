package dev.inmo.micro_utils.repos.transforms.crud

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import kotlin.js.JsName
import kotlin.jvm.JvmName

fun <K, V> ReadKeyValueRepo<K, V>.asReadCRUDRepo() = ReadCRUDFromKeyValueRepo(this)
