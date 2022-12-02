package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

class KtorCRUDRepoClient<ObjectType, IdType, InputValue> (
    readDelegate: ReadCRUDRepo<ObjectType, IdType>,
    writeDelegate: WriteCRUDRepo<ObjectType, IdType, InputValue>
) : CRUDRepo<ObjectType, IdType, InputValue> by DelegateBasedCRUDRepo(
    readDelegate,
    writeDelegate
) {
    companion object {
        inline operator fun <reified ObjectType, reified IdType, reified InputValue> invoke(
            baseUrl: String,
            httpClient: HttpClient,
            contentType: ContentType,
            noinline idSerializer: suspend (IdType) -> String
        ) = KtorCRUDRepoClient(
            KtorReadCRUDRepoClient(
                baseUrl,
                httpClient,
                typeInfo<ObjectType>(),
                typeInfo<PaginationResult<ObjectType>>(),
                typeInfo<PaginationResult<IdType>>(),
                contentType,
                idSerializer
            ),
            KtorWriteCrudRepoClient<ObjectType, IdType, InputValue>(
                baseUrl,
                httpClient,
                contentType
            )
        )

        inline operator fun <reified ObjectType, reified IdType, reified InputValue> invoke(
            baseUrl: String,
            subpart: String,
            httpClient: HttpClient,
            contentType: ContentType,
            noinline idSerializer: suspend (IdType) -> String
        ) = KtorCRUDRepoClient<ObjectType, IdType, InputValue>(
            buildStandardUrl(baseUrl, subpart),
            httpClient,
            contentType,
            idSerializer
        )
    }
}

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorCRUDRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat,
    contentType: ContentType,
) = KtorCRUDRepoClient<ObjectType, IdType, InputValue>(baseUrl, httpClient, contentType) {
    serialFormat.encodeToString(idsSerializer, it)
}

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorCRUDRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat,
    contentType: ContentType,
) = KtorCRUDRepoClient<ObjectType, IdType, InputValue>(baseUrl, httpClient, contentType) {
    serialFormat.encodeHex(idsSerializer, it)
}


inline fun <reified ObjectType, reified IdType, reified InputValue> KtorCRUDRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    contentType: ContentType,
    noinline idSerializer: suspend (IdType) -> String
) = KtorCRUDRepoClient<ObjectType, IdType, InputValue>(
    buildStandardUrl(baseUrl, subpart),
    httpClient,
    contentType,
    idSerializer
)

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorCRUDRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat,
    contentType: ContentType,
) = KtorCRUDRepoClient<ObjectType, IdType, InputValue>(buildStandardUrl(baseUrl, subpart), httpClient, idsSerializer, serialFormat, contentType)

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorCRUDRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat,
    contentType: ContentType,
) = KtorCRUDRepoClient<ObjectType, IdType, InputValue>(buildStandardUrl(baseUrl, subpart), httpClient, idsSerializer, serialFormat, contentType)
