package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.common.toCoercedInt
import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo

suspend inline fun ReadCRUDRepo<*, *>.maxPagePagination() = FirstPagePagination(count().toCoercedInt())
suspend inline fun ReadKeyValueRepo<*, *>.maxPagePagination() = FirstPagePagination(count().toCoercedInt())
suspend inline fun ReadKeyValuesRepo<*, *>.maxPagePagination() = FirstPagePagination(count().toCoercedInt())
