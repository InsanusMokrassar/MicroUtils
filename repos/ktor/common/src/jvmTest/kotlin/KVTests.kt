import dev.inmo.micro_utils.pagination.firstPageWithOneElementPagination
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.ktor.client.key.value.KtorKeyValueRepoClient
import dev.inmo.micro_utils.repos.ktor.server.key_value.configureKeyValueRepoRoutes
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

class KVTests {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testKVFunctions() {
        runTest {
            val map = mutableMapOf<Int, ComplexData>()
            val repo = MapKeyValueRepo<Int, ComplexData>(map)
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
                    configureKeyValueRepoRoutes(
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
            val crudClient = KtorKeyValueRepoClient<Int, ComplexData>(
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
                1 to dataInOneKey
            ) + (0 until repeatCount).map {
                (it + 2) to dataInMultipleKeys
            }

            dataList.forEachIndexed { i, (id, data) ->
                crudClient.set(id, data)
                assertEquals(map.size, i + 1)
                assertEquals(map.size.toLong(), crudClient.count())
                assertEquals(i + 1L, crudClient.count())
                dataList.take(i + 1).forEach { (id, data) ->
                    assertEquals(data, map[id])
                    assertEquals(data, crudClient.get(id))
                    assertEquals(map[id], crudClient.get(id))
                }
            }

            dataList.forEach { (id, data) ->
                assertTrue(crudClient.contains(id))
                assertEquals(data, crudClient.get(id))
            }

            assertEquals(
                dataList.mapNotNull { if (it.second == dataInMultipleKeys) it.first else null },
                getAllWithNextPaging(firstPageWithOneElementPagination) {
                    crudClient.keys(dataInMultipleKeys, it)
                }
            )

            assertEquals(
                dataList.mapNotNull { if (it.second == dataInOneKey) it.first else null },
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
                dataList.map { it.second },
                getAllWithNextPaging(firstPageWithOneElementPagination) {
                    crudClient.values(it)
                }
            )

            assertEquals(dataList.size.toLong(), crudClient.count())

            crudClient.unsetWithValues(dataInMultipleKeys)
            assertEquals(
                dataList.filter { it.second == dataInOneKey }.size.toLong(),
                crudClient.count()
            )

            crudClient.unset(dataList.first { it.second == dataInOneKey }.first)
            assertEquals(
                0,
                crudClient.count()
            )

            server.stop()
        }
    }
}
