package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

class KtorWriteStandardCrudRepoClient<ObjectType, IdType, InputValue> (
    private val baseUrl: String,
    private val httpClient: HttpClient
) : WriteStandardCRUDRepo<ObjectType, IdType, InputValue> {

    override val newObjectsFlow: Flow<ObjectType> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, newObjectsFlowRouting),
    )
    override val updatedObjectsFlow: Flow<ObjectType> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, updatedObjectsFlowRouting)
    )
    override val deletedObjectsIdsFlow: Flow<IdType> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, deletedObjectsIdsFlowRouting)
    )


    override suspend fun create(values: List<InputValue>): List<ObjectType> = httpClient.post {
        url(buildStandardUrl(baseUrl, createRouting))
        setBody(values)
    }.body()

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValue>>): List<ObjectType> = httpClient.post {
        url(buildStandardUrl(baseUrl, updateManyRouting))
        setBody(values)
    }.body()

    override suspend fun update(id: IdType, value: InputValue): ObjectType? = update(listOf(id to value)).firstOrNull()

    override suspend fun deleteById(ids: List<IdType>) {
        httpClient.post {
            url(buildStandardUrl(baseUrl, deleteByIdRouting))
            setBody(ids)
        }
    }
}
