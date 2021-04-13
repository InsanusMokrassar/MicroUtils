package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.StandardCRUDRepo

abstract class AbstractExposedCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = 0,
    tableName: String = ""
) :
    AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
        flowsChannelsSize,
        tableName
    ),
    ExposedCRUDRepo<ObjectType, IdType>,
    StandardCRUDRepo<ObjectType, IdType, InputValueType>
