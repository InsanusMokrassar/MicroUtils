package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

class KtorWriteStandardCrudRepo<ObjectType, IdType, InputValue> (
    private val baseUrl: String,
    private val client: HttpClient = HttpClient(),
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

    override val newObjectsFlow: Flow<ObjectType> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, newObjectsFlowRouting),
        deserializer = objectsSerializer
    )
    override val updatedObjectsFlow: Flow<ObjectType> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, updatedObjectsFlowRouting),
        deserializer = objectsSerializer
    )
    override val deletedObjectsIdsFlow: Flow<IdType> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, deletedObjectsIdsFlowRouting),
        deserializer = idsSerializer
    )


    override suspend fun create(values: List<InputValue>): List<ObjectType> = client.unipost(
        buildStandardUrl(baseUrl, createRouting),
        BodyPair(listInputSerializer, values),
        listObjectsSerializer
    )

    override suspend fun update(id: IdType, value: InputValue): ObjectType? = client.unipost(
        buildStandardUrl(baseUrl, updateRouting),
        BodyPair(inputUpdateSerializer, id to value),
        objectsNullableSerializer
    )

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValue>>): List<ObjectType> = client.unipost(
        buildStandardUrl(baseUrl, updateManyRouting),
        BodyPair(listInputUpdateSerializer, values),
        listObjectsSerializer
    )

    override suspend fun deleteById(ids: List<IdType>) = client.unipost(
        buildStandardUrl(baseUrl, deleteByIdRouting),
        BodyPair(listIdsSerializer, ids),
        Unit.serializer()
    )
}
