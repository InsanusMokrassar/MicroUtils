package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.CRUDRepo
import kotlinx.coroutines.channels.BufferOverflow

abstract class AbstractExposedCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = 0,
    tableName: String = "",
    replyCacheInFlows: Int = 0,
    onBufferOverflowBehaviour: BufferOverflow = BufferOverflow.SUSPEND
) :
    AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
        flowsChannelsSize,
        tableName,
        replyCacheInFlows,
        onBufferOverflowBehaviour
    ),
    ExposedCRUDRepo<ObjectType, IdType>,
    CRUDRepo<ObjectType, IdType, InputValueType>
