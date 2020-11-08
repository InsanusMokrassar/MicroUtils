package dev.inmo.micro_utils.repos

interface MapperRepo<FromKey, FromValue, ToKey, ToValue> {
    suspend fun FromKey.toOutKey() = this as ToKey
    suspend fun FromValue.toOutValue() = this as ToValue

    suspend fun ToKey.toInnerKey() = this as FromKey
    suspend fun ToValue.toInnerValue() = this as FromValue
}