package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.util.reflect.typeInfo

inline fun <reified Key : Any, reified Value : Any> Route.configureWriteStandardKeyValuesRepoRoutes (
    originalRepo: WriteOneToManyKeyValueRepo<Key, Value>
) {
    includeWebsocketHandling(
        onNewValueRoute,
        originalRepo.onNewValue
    )

    includeWebsocketHandling(
        onValueRemovedRoute,
        originalRepo.onValueRemoved
    )

    includeWebsocketHandling(
        onDataClearedRoute,
        originalRepo.onDataCleared
    )

    val mapType = typeInfo<Map<Key, List<Value>>>()

    post(addRoute) {
        originalRepo.add(call.receive(mapType))
        call.respond(HttpStatusCode.OK)
    }

    post(setRoute) {
        originalRepo.set(call.receive(mapType))
        call.respond(HttpStatusCode.OK)
    }

    post(removeRoute) {
        originalRepo.remove(call.receive(mapType))
        call.respond(HttpStatusCode.OK)
    }

    post(clearRoute) {
        originalRepo.clear(call.receive())
        call.respond(HttpStatusCode.OK)
    }

    post(clearWithValueRoute) {
        originalRepo.clearWithValue(call.receive())
        call.respond(HttpStatusCode.OK)
    }
}
