package dev.inmo.micro_utils.repos.ktor.common.one_to_many

import kotlin.js.JsExport

@JsExport
const val getRoute = "get"
@JsExport
const val keysRoute = "keys"
@JsExport
const val containsByKeyRoute = "containsByKey"
@JsExport
const val containsByKeyValueRoute = "containsByKeyValue"
@JsExport
const val countByKeyRoute = "countByKey"
@JsExport
const val countRoute = "count"

@JsExport
const val onNewValueRoute = "onNewValue"
@JsExport
const val onValueRemovedRoute = "onValueRemoved"
@JsExport
const val onDataClearedRoute = "onDataCleared"

@JsExport
const val addRoute = "add"
@JsExport
const val removeRoute = "remove"
@JsExport
const val clearRoute = "clear"