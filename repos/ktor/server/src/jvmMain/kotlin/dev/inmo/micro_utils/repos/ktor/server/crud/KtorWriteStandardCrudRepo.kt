package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.http.ContentType
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

fun <ObjectType, IdType, InputValue> Route.configureWriteStandardCrudRepoRoutes(
    originalRepo: WriteStandardCRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>,
    unifiedRouter: UnifiedRouter
) {
    val listObjectsSerializer = ListSerializer(objectsSerializer)
    val listInputSerializer = ListSerializer(inputsSerializer)
    val listIdsSerializer = ListSerializer(idsSerializer)
    val inputUpdateSerializer = PairSerializer(
        idsSerializer,
        inputsSerializer
    )
    val listInputUpdateSerializer = ListSerializer(inputUpdateSerializer)

    unifiedRouter.apply {
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
    }

    post(createRouting) {
        unifiedRouter.apply {
            unianswer(
                listObjectsSerializer,
                originalRepo.create(
                    uniload(listInputSerializer)
                )
            )
        }
    }

    post(updateRouting) {
        unifiedRouter.apply {
            val (id, input) = uniload(inputUpdateSerializer)
            unianswer(
                objectsNullableSerializer,
                originalRepo.update(
                    id, input
                )
            )
        }
    }

    post(updateManyRouting) {
        unifiedRouter.apply {
            val updates = uniload(listInputUpdateSerializer)
            unianswer(
                listObjectsSerializer,
                originalRepo.update(
                    updates
                )
            )
        }
    }

    post(deleteByIdRouting) {
        unifiedRouter.apply {
            val ids = uniload(listIdsSerializer)
            unianswer(
                Unit.serializer(),
                originalRepo.deleteById(
                    ids
                )
            )
        }
    }
}

fun <ObjectType, IdType, InputValue> Route.configureWriteStandardCrudRepoRoutes(
    originalRepo: WriteStandardCRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureWriteStandardCrudRepoRoutes(
    originalRepo, objectsSerializer, objectsNullableSerializer, inputsSerializer, idsSerializer, UnifiedRouter(serialFormat, serialFormatContentType)
)
