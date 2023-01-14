package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow

class KtorWriteCrudRepoClient<ObjectType, IdType, InputValue> (
    private val baseUrl: String,
    private val httpClient: HttpClient,
    override val newObjectsFlow: Flow<ObjectType>,
    override val updatedObjectsFlow: Flow<ObjectType>,
    override val deletedObjectsIdsFlow: Flow<IdType>,
    private val createSetup: suspend HttpRequestBuilder.(List<InputValue>) -> Unit,
    private val updateSetup: suspend HttpRequestBuilder.(List<UpdatedValuePair<IdType, InputValue>>) -> Unit,
    private val deleteByIdSetup: suspend HttpRequestBuilder.(List<IdType>) -> Unit,
    private val createBodyGetter: suspend HttpResponse.() -> List<ObjectType>,
    private val updateBodyGetter: suspend HttpResponse.() -> List<ObjectType>
) : WriteCRUDRepo<ObjectType, IdType, InputValue> {
    override suspend fun create(values: List<InputValue>): List<ObjectType> = httpClient.post(
        buildStandardUrl(baseUrl, createRouting)
    ) {
        createSetup(values)
    }.createBodyGetter()

    override suspend fun update(
        values: List<UpdatedValuePair<IdType, InputValue>>
    ): List<ObjectType> = httpClient.post(
        buildStandardUrl(baseUrl, updateRouting)
    ) {
        updateSetup(values)
    }.updateBodyGetter()

    override suspend fun update(id: IdType, value: InputValue): ObjectType? = update(listOf(id to value)).firstOrNull()

    override suspend fun deleteById(ids: List<IdType>) {
        httpClient.post(
            buildStandardUrl(baseUrl, deleteByIdRouting)
        ) {
            deleteByIdSetup(ids)
        }.throwOnUnsuccess { "Unable to delete $ids" }
    }

    companion object {
        inline operator fun <reified ObjectType, reified IdType, reified InputValue> invoke(
            baseUrl: String,
            httpClient: HttpClient,
            contentType: ContentType,
            newObjectsFlow: Flow<ObjectType> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, newObjectsFlowRouting),
            ),
            updatedObjectsFlow: Flow<ObjectType> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, updatedObjectsFlowRouting),
            ),
            deletedObjectsIdsFlow: Flow<IdType> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, deletedObjectsIdsFlowRouting),
            ),
        ) = KtorWriteCrudRepoClient<ObjectType, IdType, InputValue>(
            baseUrl,
            httpClient,
            newObjectsFlow,
            updatedObjectsFlow,
            deletedObjectsIdsFlow,
            {
                contentType(contentType)
                setBody(it)
            },
            {
                contentType(contentType)
                setBody(it)
            },
            {
                contentType(contentType)
                setBody(it)
            },
            { body() },
            { body() }
        )
    }
}
