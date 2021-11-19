package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.ktor.server.configurators.KtorApplicationConfigurator
import io.ktor.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.*
import kotlin.random.Random

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> createKtorServer(
    engine: ApplicationEngineFactory<TEngine, TConfiguration>,
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    block: Application.() -> Unit
): TEngine {
    val env = applicationEngineEnvironment {
        module(block)
        connector {
            this@connector.host = host
            this@connector.port = port
        }
    }
    return embeddedServer(engine, env)
}

/**
 * Create server with [CIO] server engine without starting of it
 *
 * @see ApplicationEngine.start
 */
fun createKtorServer(
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    block: Application.() -> Unit
): ApplicationEngine = createKtorServer(CIO, host, port, block)

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> createKtorServer(
    engine: ApplicationEngineFactory<TEngine, TConfiguration>,
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    configurators: List<KtorApplicationConfigurator>
): TEngine = createKtorServer(
    engine,
    host,
    port
) {
    configurators.forEach { it.apply { configure() } }
}

/**
 * Create server with [CIO] server engine without starting of it
 *
 * @see ApplicationEngine.start
 */
fun createKtorServer(
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    configurators: List<KtorApplicationConfigurator>
): ApplicationEngine = createKtorServer(CIO, host, port, configurators)
