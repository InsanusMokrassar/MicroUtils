package dev.inmo.micro_utils.repos.exposed

import kotlinx.coroutines.channels.Channel

abstract class AbstractExposedCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = Channel.BUFFERED,
    databaseName: String = ""
) :
    AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
        flowsChannelsSize,
        databaseName
    ),
    ExposedCRUDRepo<ObjectType, IdType>
