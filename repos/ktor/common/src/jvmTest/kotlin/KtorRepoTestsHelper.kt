package dev.inmo.micro_utils.repos.ktor

import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json

object KtorRepoTestsHelper {
    fun beforeTest(routingConfigurator: Routing.() -> Unit): ApplicationEngine {
        return embeddedServer(
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
            routing(routingConfigurator)
        }.start(false)
    }
    fun afterTest(engine: ApplicationEngine) {
        engine.stop()
    }
    fun client(): HttpClient = HttpClient {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json()
        }
        install(Logging)
        install(io.ktor.client.plugins.websocket.WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

}
