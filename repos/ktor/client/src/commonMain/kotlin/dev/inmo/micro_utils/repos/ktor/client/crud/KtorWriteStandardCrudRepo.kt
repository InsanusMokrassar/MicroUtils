package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.buildStandardUrl
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

class KtorWriteStandardCrudRepo<ObjectType, IdType, InputValue> (
    private val baseUrl: String,
    private val unifiedRequester: UnifiedRequester,
    private val objectsSerializer: KSerializer<ObjectType>,
    private val objectsNullableSerializer: KSerializer<ObjectType?>,
    private val inputsSerializer: KSerializer<InputValue>,
    private val idsSerializer: KSerializer<IdType>
) : WriteStandardCRUDRepo<ObjectType, IdType, InputValue> {
    private val listObjectsSerializer = ListSerializer(objectsSerializer)
    private val listInputSerializer = ListSerializer(inputsSerializer)
    private val listIdsSerializer = ListSerializer(idsSerializer)
    private val inputUpdateSerializer = PairSerializer(
        idsSerializer,
        inputsSerializer
    )
    private val listInputUpdateSerializer = ListSerializer(inputUpdateSerializer)

    constructor(
        baseUrl: String,
        client: HttpClient,
        objectsSerializer: KSerializer<ObjectType>,
        objectsSerializerNullable: KSerializer<ObjectType?>,
        inputsSerializer: KSerializer<InputValue>,
        idsSerializer: KSerializer<IdType>,
        serialFormat: BinaryFormat = standardKtorSerialFormat
    ) : this (
        baseUrl, UnifiedRequester(client, serialFormat), objectsSerializer, objectsSerializerNullable, inputsSerializer, idsSerializer
    )

    override val newObjectsFlow: Flow<ObjectType> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, newObjectsFlowRouting),
        deserializer = objectsSerializer
    )
    override val updatedObjectsFlow: Flow<ObjectType> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, updatedObjectsFlowRouting),
        deserializer = objectsSerializer
    )
    override val deletedObjectsIdsFlow: Flow<IdType> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, deletedObjectsIdsFlowRouting),
        deserializer = idsSerializer
    )


    override suspend fun create(values: List<InputValue>): List<ObjectType> = unifiedRequester.unipost(
        buildStandardUrl(baseUrl, createRouting),
        BodyPair(listInputSerializer, values),
        listObjectsSerializer
    )

    override suspend fun update(id: IdType, value: InputValue): ObjectType? = unifiedRequester.unipost(
        buildStandardUrl(baseUrl, updateRouting),
        BodyPair(inputUpdateSerializer, id to value),
        objectsNullableSerializer
    )

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValue>>): List<ObjectType> = unifiedRequester.unipost(
        buildStandardUrl(baseUrl, updateManyRouting),
        BodyPair(listInputUpdateSerializer, values),
        listObjectsSerializer
    )

    override suspend fun deleteById(ids: List<IdType>) = unifiedRequester.unipost(
        buildStandardUrl(baseUrl, deleteByIdRouting),
        BodyPair(listIdsSerializer, ids),
        Unit.serializer()
    )
}
