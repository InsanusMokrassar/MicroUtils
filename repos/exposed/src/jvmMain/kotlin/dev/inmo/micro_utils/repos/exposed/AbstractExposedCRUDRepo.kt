package dev.inmo.micro_utils.repos.exposed

abstract class AbstractExposedCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = 0,
    tableName: String = ""
) :
    AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
        flowsChannelsSize,
        tableName
    ),
    ExposedCRUDRepo<ObjectType, IdType>
