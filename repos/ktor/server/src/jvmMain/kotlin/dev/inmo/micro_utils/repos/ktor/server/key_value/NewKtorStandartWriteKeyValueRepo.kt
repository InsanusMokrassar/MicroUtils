package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteStandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.util.reflect.typeInfo

inline fun <reified Key : Any, reified Value : Any> Route.configureWriteStandardKeyValueRepoRoutes (
    originalRepo: WriteStandardKeyValueRepo<Key, Value>
) {
    includeWebsocketHandling(
        onNewValueRoute,
        originalRepo.onNewValue
    )

    includeWebsocketHandling(
        onValueRemovedRoute,
        originalRepo.onValueRemoved
    )

    val mapType = typeInfo<Map<Key, Value>>()
    val listKeysType = typeInfo<List<Key>>()
    val listValuesType = typeInfo<List<Value>>()

    post(setRoute) {
        originalRepo.set(call.receive(mapType))
        call.respond(HttpStatusCode.OK)
    }

    post(unsetRoute) {
        originalRepo.unset(call.receive(listKeysType))
        call.respond(HttpStatusCode.OK)
    }

    post(unsetWithValuesRoute) {
        originalRepo.unsetWithValues(call.receive(listValuesType))
        call.respond(HttpStatusCode.OK)
    }
}
