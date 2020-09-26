package dev.inmo.micro_utils.repos.exposed

import kotlinx.coroutines.channels.Channel

abstract class AbstractExposedCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = Channel.BUFFERED,
    tableName: String = ""
) :
    AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
        flowsChannelsSize,
        tableName
    ),
    ExposedCRUDRepo<ObjectType, IdType>
