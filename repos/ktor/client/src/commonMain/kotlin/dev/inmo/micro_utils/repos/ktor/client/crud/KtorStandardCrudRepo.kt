package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.client.UnifiedRequester
import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

@Deprecated("Use KtorStandardCrudRepoClient instead")
class KtorStandardCrudRepo<ObjectType, IdType, InputValue> (
    baseUrl: String,
    baseSubpart: String,
    unifiedRequester: UnifiedRequester,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>
) : StandardCRUDRepo<ObjectType, IdType, InputValue>,
    ReadStandardCRUDRepo<ObjectType, IdType> by KtorReadStandardCrudRepo(
        "$baseUrl/$baseSubpart",
        unifiedRequester,
        objectsSerializer,
        objectsNullableSerializer,
        idsSerializer
    ),
    WriteStandardCRUDRepo<ObjectType, IdType, InputValue> by KtorWriteStandardCrudRepo(
        "$baseUrl/$baseSubpart",
        unifiedRequester,
        objectsSerializer,
        objectsNullableSerializer,
        inputsSerializer,
        idsSerializer
    ) {
    constructor(
        baseUrl: String,
        baseSubpart: String,
        client: HttpClient,
        objectsSerializer: KSerializer<ObjectType>,
        objectsNullableSerializer: KSerializer<ObjectType?>,
        inputsSerializer: KSerializer<InputValue>,
        idsSerializer: KSerializer<IdType>,
        serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
    ) : this(
        baseUrl, baseSubpart, UnifiedRequester(client, serialFormat), objectsSerializer, objectsNullableSerializer, inputsSerializer, idsSerializer
    )
}
