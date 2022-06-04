import dev.inmo.micro_utils.pagination.firstPageWithOneElementPagination
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.ktor.client.key.values.KtorKeyValuesRepoClient
import dev.inmo.micro_utils.repos.ktor.server.one_to_many.configureKeyValuesRepoRoutes
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.*

class KVsTests {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testKVsFunctions() {
        runTest {
            val map = mutableMapOf<Int, MutableList<ComplexData>>()
            val repo = MapKeyValuesRepo(map)
            val server = io.ktor.server.engine.embeddedServer(
                CIO,
                23456,
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
                        Json {}
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
                "http://127.0.0.1:23456",
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
