package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.KSTypeNotPresentException
import com.google.devtools.ksp.KSTypesNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toClassName
import kotlin.reflect.KClass

@OptIn(KspExperimental::class)
inline fun convertToClassName(getter: () -> KClass<*>) = try {
    getter().asClassName()
} catch (e: KSTypeNotPresentException) {
    e.ksType.toClassName()
}

@OptIn(KspExperimental::class)
inline fun convertToClassNames(getter: () -> List<KClass<*>>) = try {
    getter().map { it.asClassName() }
} catch (e: KSTypesNotPresentException) {
    e.ksTypes.map {
        it.toClassName()
    }
}
