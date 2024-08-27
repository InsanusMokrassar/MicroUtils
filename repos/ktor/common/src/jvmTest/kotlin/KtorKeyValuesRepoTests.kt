package dev.inmo.micro_utils.repos.ktor

import dev.inmo.micro_utils.pagination.firstPageWithOneElementPagination
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.common.tests.CommonKeyValuesRepoTests
import dev.inmo.micro_utils.repos.ktor.client.key.values.KtorKeyValuesRepoClient
import dev.inmo.micro_utils.repos.ktor.server.key.values.configureKeyValuesRepoRoutes
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.*

class KtorKeyValuesRepoTests : CommonKeyValuesRepoTests() {
    private var engine: EmbeddedServer<*, *>? = null
    override val testSequencesSize: Int
        get() = 100

    override val repoCreator: suspend () -> KeyValuesRepo<String, String> = {
        KtorKeyValuesRepoClient(
            "http://127.0.0.1:23456",
            KtorRepoTestsHelper.client(),
            ContentType.Application.Json,
            String.serializer(),
            String.serializer(),
            Json
        )
    }

    @BeforeTest
    fun beforeTest() {
        engine = KtorRepoTestsHelper.beforeTest {
            configureKeyValuesRepoRoutes(
                MapKeyValuesRepo(),
                String.serializer(),
                String.serializer(),
                Json
            )
        }
    }
    @AfterTest
    fun afterTest() {
        engine ?.let(KtorRepoTestsHelper::afterTest)
    }

    @Test
    fun testKVsFunctions() {
        runTest {
            val map = mutableMapOf<Int, MutableList<ComplexData>>()
            val repo = MapKeyValuesRepo(map)
            val server = io.ktor.server.engine.embeddedServer(
                CIO,
                34567,
                "127.0.0.1"
            ) {
                install(ContentNegotiation) {
                    json()
                }
                install(WebSockets) {
                    contentConverter = KotlinxWebsocketSerializationConverter(Json)
                }
                routing {
                    configureKeyValuesRepoRoutes(
                        repo,
                        Int.serializer(),
                        ComplexData.serializer(),
                        Json
                    )
                }
            }.start(false)
            val client = HttpClient {
                install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                    json()
                }
                install(Logging)
                install(io.ktor.client.plugins.websocket.WebSockets) {
                    contentConverter = KotlinxWebsocketSerializationConverter(Json)
                }
            }
            val crudClient = KtorKeyValuesRepoClient(
                "http://127.0.0.1:34567",
                client,
                ContentType.Application.Json,
                Int.serializer(),
                ComplexData.serializer(),
                Json
            )

            val dataInOneKey = ComplexData(1, title = "Example1")
            val dataInMultipleKeys = ComplexData(2, title = "Example2")
            val repeatCount = 3

            val dataList = listOf(
                1 to listOf(dataInOneKey)
            ) + (0 until repeatCount).map {
                (it + 2) to listOf(dataInMultipleKeys)
            }

            dataList.forEachIndexed { i, (id, data) ->
                crudClient.set(id, data)
                assertEquals(i + 1, map.size)
                assertEquals(map.size.toLong(), crudClient.count())
                assertEquals(i + 1L, crudClient.count())
                dataList.take(i + 1).forEach { (id, data) ->
                    assertContentEquals(data, map[id])
                    assertContentEquals(data, crudClient.getAll(id))
                    assertContentEquals(map[id], crudClient.getAll(id))
                }
            }

            dataList.forEach { (key, data) ->
                assertTrue(crudClient.contains(key))
                assertContentEquals(data, crudClient.getAll(key))
            }

            assertEquals(
                dataList.mapNotNull { if (it.second.contains(dataInMultipleKeys)) it.first else null },
                getAllWithNextPaging(firstPageWithOneElementPagination) {
                    crudClient.keys(dataInMultipleKeys, it)
                }
            )

            assertEquals(
                dataList.mapNotNull { if (it.second.contains(dataInOneKey)) it.first else null },
                getAllWithNextPaging(firstPageWithOneElementPagination) {
                    crudClient.keys(dataInOneKey, it)
                }
            )

            assertEquals(
                dataList.map { it.first },
                getAllWithNextPaging(firstPageWithOneElementPagination) {
                    crudClient.keys(it)
                }
            )

            assertEquals(
                dataList.map { it.first },
                getAllWithNextPaging(firstPageWithOneElementPagination) {
                    crudClient.keys(it)
                }
            )

            assertEquals(dataList.size.toLong(), crudClient.count())

            crudClient.remove(dataList.filter { it.second.contains(dataInMultipleKeys) })
            println(map)
            assertEquals(
                dataList.filter { it.second.contains(dataInOneKey) }.size.toLong(),
                crudClient.count()
            )

            crudClient.remove(dataList.filter { it.second.contains(dataInOneKey) })
            assertEquals(
                0,
                crudClient.count()
            )

            server.stop()
        }
    }
}
