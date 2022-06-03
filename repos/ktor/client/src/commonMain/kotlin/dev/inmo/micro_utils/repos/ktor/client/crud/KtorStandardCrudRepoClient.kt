package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

class KtorStandardCrudRepoClient<ObjectType, IdType, InputValue> (
    readDelegate: ReadStandardCRUDRepo<ObjectType, IdType>,
    writeDelegate: WriteStandardCRUDRepo<ObjectType, IdType, InputValue>
) : StandardCRUDRepo<ObjectType, IdType, InputValue> by DelegateBasedStandardCRUDRepo(
    readDelegate,
    writeDelegate
) {
    companion object {
        inline operator fun <reified ObjectType, reified IdType, reified InputValue> invoke(
            baseUrl: String,
            httpClient: HttpClient,
            objectTypeInfo: TypeInfo,
            contentType: ContentType,
            noinline idSerializer: suspend (IdType) -> String
        ) = KtorStandardCrudRepoClient(
            KtorReadStandardCrudRepoClient(
                baseUrl,
                httpClient,
                objectTypeInfo,
                contentType,
                idSerializer
            ),
            KtorWriteStandardCrudRepoClient<ObjectType, IdType, InputValue>(
                baseUrl,
                httpClient,
                contentType
            )
        )

        inline operator fun <reified ObjectType, reified IdType, reified InputValue> invoke(
            baseUrl: String,
            subpart: String,
            httpClient: HttpClient,
            objectTypeInfo: TypeInfo,
            contentType: ContentType,
            noinline idSerializer: suspend (IdType) -> String
        ) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(
            buildStandardUrl(baseUrl, subpart),
            httpClient,
            objectTypeInfo,
            contentType,
            idSerializer
        )
    }
}


inline fun <reified ObjectType, reified IdType, reified InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    noinline idSerializer: suspend (IdType) -> String
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(
    baseUrl,
    httpClient,
    typeInfo<ObjectType>(),
    contentType,
    idSerializer
)

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat,
    contentType: ContentType,
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(baseUrl, httpClient, contentType) {
    serialFormat.encodeToString(idsSerializer, it)
}

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat,
    contentType: ContentType,
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(baseUrl, httpClient, contentType) {
    serialFormat.encodeHex(idsSerializer, it)
}


inline fun <reified ObjectType, reified IdType, reified InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    contentType: ContentType,
    noinline idSerializer: suspend (IdType) -> String
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(
    buildStandardUrl(baseUrl, subpart),
    httpClient,
    contentType,
    idSerializer
)

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat,
    contentType: ContentType,
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(buildStandardUrl(baseUrl, subpart), httpClient, idsSerializer, serialFormat, contentType)

inline fun <reified ObjectType, reified IdType, reified InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat,
    contentType: ContentType,
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(buildStandardUrl(baseUrl, subpart), httpClient, idsSerializer, serialFormat, contentType)
