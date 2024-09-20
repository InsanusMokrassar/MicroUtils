package dev.inmo.micro_utils.common.compose

import org.jetbrains.compose.web.dom.AttrBuilderContext

fun tagClasses(vararg classnames: String): AttrBuilderContext<*> = {
    classes(*classnames)
}

fun tagId(id: String): AttrBuilderContext<*> = {
    id(id)
}
