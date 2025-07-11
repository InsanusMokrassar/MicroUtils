package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.KSTypeNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass

@OptIn(KspExperimental::class)
fun KClass<*>.safeClassName() = runCatching {
    asTypeName()
}.getOrElse { e ->
    if (e is KSTypeNotPresentException) {
        ClassName(
            e.ksType.declaration.packageName.asString(),
            e.ksType.declaration.qualifiedName ?.asString() ?.replaceFirst(e.ksType.declaration.packageName.asString(), "")
                ?: e.ksType.declaration.simpleName.asString()
        )
    } else {
        throw e
    }
}
