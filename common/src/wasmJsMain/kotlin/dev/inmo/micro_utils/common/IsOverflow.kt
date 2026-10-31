package dev.inmo.micro_utils.common

import org.w3c.dom.Element

inline val Element.isOverflowWidth
    get() = scrollWidth > clientWidth

inline val Element.isOverflowHeight
    get() = scrollHeight > clientHeight

inline val Element.isOverflow
    get() = isOverflowHeight || isOverflowWidth
