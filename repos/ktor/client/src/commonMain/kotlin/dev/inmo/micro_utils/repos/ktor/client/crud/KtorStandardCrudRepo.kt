package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlin.js.JsExport

@JsExport
class KtorStandardCrudRepo<ObjectType, IdType, InputValue> (
    baseUrl: String,
    baseSubpart: String,
    client: HttpClient,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>
) : StandardCRUDRepo<ObjectType, IdType, InputValue>,
    ReadStandardCRUDRepo<ObjectType, IdType> by KtorReadStandardCrudRepo(
        "$baseUrl/$baseSubpart",
        client,
        objectsSerializer,
        objectsNullableSerializer,
        idsSerializer
    ),
    WriteStandardCRUDRepo<ObjectType, IdType, InputValue> by KtorWriteStandardCrudRepo(
        "$baseUrl/$baseSubpart",
        client,
        objectsSerializer,
        objectsNullableSerializer,
        inputsSerializer,
        idsSerializer
    )
