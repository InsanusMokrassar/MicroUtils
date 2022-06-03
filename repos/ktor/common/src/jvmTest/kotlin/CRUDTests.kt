import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.ktor.client.crud.KtorStandardCrudRepoClient
import dev.inmo.micro_utils.repos.ktor.server.crud.configureStandardCrudRepoRoutes
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
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class CRUDTests {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testCRUDFunctions() {
        runTest() {
            val map = mutableMapOf<Int, ComplexData>()
            val repo = MapCRUDRepo<ComplexData, Int, SimpleData>(
                map,
                { newValue, id, oldValue ->
                    oldValue.copy(title = newValue.title)
                }
            ) {
                size to ComplexData(size, title = it.title)
            }
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
                    configureStandardCrudRepoRoutes(
                        repo
                    ) {
                        it.toInt()
                    }
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
            val crudClient = KtorStandardCrudRepoClient<ComplexData, Int, SimpleData>(
                "http://127.0.0.1:23456",
                client,
                ContentType.Application.Json
            ) {
                it.toString()
            }

            val created = crudClient.create(SimpleData("Example")).single()
            assertEquals(map.size, 1)
            assertEquals(map.size.toLong(), crudClient.count())
            assertEquals(1, crudClient.count())
            assertEquals(map.getValue(map.keys.first()), created)

            val updated = crudClient.update(created.id, SimpleData("Example2"))
            assertEquals(map.size, 1)
            assertEquals(map.size.toLong(), crudClient.count())
            assertEquals(1, crudClient.count())
            assertEquals(map.getValue(map.keys.first()), updated)

            crudClient.deleteById(created.id)
            assertEquals(map.size, 0)
            assertEquals(map.size.toLong(), crudClient.count())
            assertEquals(0, crudClient.count())
            server.stop()
        }
    }
}
