package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.server.includeWebsocketHandling
import dev.inmo.micro_utils.ktor.server.unianswer
import dev.inmo.micro_utils.ktor.server.uniload
import dev.inmo.micro_utils.repos.WriteStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.post
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

fun <ObjectType, IdType, InputValue> Route.configureWriteStandardCrudRepoRoutes(
    originalRepo: WriteStandardCRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>
) {
    val listObjectsSerializer = ListSerializer(objectsSerializer)
    val listInputSerializer = ListSerializer(inputsSerializer)
    val listIdsSerializer = ListSerializer(idsSerializer)
    val inputUpdateSerializer = PairSerializer(
        idsSerializer,
        inputsSerializer
    )
    val listInputUpdateSerializer = ListSerializer(inputUpdateSerializer)

    includeWebsocketHandling(
        newObjectsFlowRouting,
        originalRepo.newObjectsFlow,
        objectsSerializer
    )
    includeWebsocketHandling(
        updatedObjectsFlowRouting,
        originalRepo.updatedObjectsFlow,
        objectsSerializer
    )
    includeWebsocketHandling(
        deletedObjectsIdsFlowRouting,
        originalRepo.deletedObjectsIdsFlow,
        idsSerializer
    )

    post(createRouting) {
        call.unianswer(
            listObjectsSerializer,
            originalRepo.create(
                call.uniload(listInputSerializer)
            )
        )
    }

    post(updateRouting) {
        val (id, input) = call.uniload(inputUpdateSerializer)
        call.unianswer(
            objectsNullableSerializer,
            originalRepo.update(
                id, input
            )
        )
    }

    post(updateManyRouting) {
        val updates = call.uniload(listInputUpdateSerializer)
        call.unianswer(
            listObjectsSerializer,
            originalRepo.update(
                updates
            )
        )
    }

    post(deleteByIdRouting) {
        val ids = call.uniload(listIdsSerializer)
        call.unianswer(
            Unit.serializer(),
            originalRepo.deleteById(
                ids
            )
        )
    }
}
