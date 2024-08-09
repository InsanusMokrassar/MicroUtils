package dev.inmo.micro_utils.repos.ktor

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.common.tests.CommonCRUDRepoTests
import dev.inmo.micro_utils.repos.ktor.client.crud.KtorCRUDRepoClient
import dev.inmo.micro_utils.repos.ktor.server.crud.configureCRUDRepoRoutes
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorCRUDRepoTests : CommonCRUDRepoTests() {
    private var engine: ApplicationEngine? = null

    @BeforeTest
    fun beforeTest() {
        engine = KtorRepoTestsHelper.beforeTest {
            configureCRUDRepoRoutes(
                MapCRUDRepo<Registered, String, New>(
                    { new, id, old ->
                        Registered(id, new.data)
                    }
                ) {
                    val id = uuid4().toString()
                    id to Registered(id, it.data)
                }
            ) {
                it
            }
        }
    }
    @AfterTest
    fun afterTest() {
        engine ?.let(KtorRepoTestsHelper::afterTest)
    }

    override val repoCreator: suspend () -> CRUDRepo<Registered, String, New> = {
        KtorCRUDRepoClient<Registered, String, New>(
            "http://127.0.0.1:23456",
            KtorRepoTestsHelper.client(),
            ContentType.Application.Json
        ) {
            it
        }
    }

    @Test
    fun testCRUDFunctions() {
        runTest {
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
                    configureCRUDRepoRoutes(
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
            val crudClient = KtorCRUDRepoClient<ComplexData, Int, SimpleData>(
                "http://127.0.0.1:34567",
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

            assertEquals(map, crudClient.getAll())

            server.stop()
        }
    }
}
