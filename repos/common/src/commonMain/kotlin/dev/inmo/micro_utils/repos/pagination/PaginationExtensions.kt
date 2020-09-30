package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.doWithPagination
import dev.inmo.micro_utils.pagination.nextPageIfNotEmpty
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo

suspend inline fun <T, ID, REPO : ReadStandardCRUDRepo<T, ID>> REPO.doWithAll(
    block: (List<T>) -> Unit
) {
    doWithPagination {
        getByPagination(it).also {
            block(it.results)
        }.nextPageIfNotEmpty()
    }
}
