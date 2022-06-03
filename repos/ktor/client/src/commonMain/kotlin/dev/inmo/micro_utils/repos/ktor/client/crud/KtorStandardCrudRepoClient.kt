package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

class KtorStandardCrudRepoClient<ObjectType, IdType, InputValue> (
    baseUrl: String,
    httpClient: HttpClient,
    objectTypeInfo: TypeInfo,
    idSerializer: suspend (IdType) -> String
) : StandardCRUDRepo<ObjectType, IdType, InputValue>,
    ReadStandardCRUDRepo<ObjectType, IdType> by KtorReadStandardCrudRepoClient(
        baseUrl,
        httpClient,
        objectTypeInfo,
        idSerializer
    ),
    WriteStandardCRUDRepo<ObjectType, IdType, InputValue> by KtorWriteStandardCrudRepoClient(
        baseUrl,
        httpClient
    ) {
    constructor(
        baseUrl: String,
        subpart: String,
        httpClient: HttpClient,
        objectTypeInfo: TypeInfo,
        idSerializer: suspend (IdType) -> String
    ) : this(
        buildStandardUrl(baseUrl, subpart), httpClient, objectTypeInfo, idSerializer
    )
}


inline fun <reified ObjectType, IdType, InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    noinline idSerializer: suspend (IdType) -> String
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(
    baseUrl,
    httpClient,
    typeInfo<ObjectType>(),
    idSerializer
)

inline fun <reified ObjectType, IdType, InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(baseUrl, httpClient) {
    serialFormat.encodeToString(idsSerializer, it)
}

inline fun <reified ObjectType, IdType, InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(baseUrl, httpClient) {
    serialFormat.encodeHex(idsSerializer, it)
}


inline fun <reified ObjectType, IdType, InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    noinline idSerializer: suspend (IdType) -> String
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(
    buildStandardUrl(baseUrl, subpart),
    httpClient,
    idSerializer
)

inline fun <reified ObjectType, IdType, InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(buildStandardUrl(baseUrl, subpart), httpClient, idsSerializer, serialFormat)

inline fun <reified ObjectType, IdType, InputValue> KtorStandardCrudRepoClient(
    baseUrl: String,
    subpart: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat
) = KtorStandardCrudRepoClient<ObjectType, IdType, InputValue>(buildStandardUrl(baseUrl, subpart), httpClient, idsSerializer, serialFormat)
