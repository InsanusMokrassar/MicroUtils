package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.KSTypeNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass

/**
 * Safely retrieves a [ClassName] from a [KClass] getter, handling cases where the type is not
 * yet available during annotation processing (KSP).
 *
 * When [KSTypeNotPresentException] is caught, it extracts the class name information from the
 * exception's [com.google.devtools.ksp.symbol.KSType] to construct a [ClassName].
 *
 * @param classnameGetter A lambda that returns the [KClass] to convert
 * @return A KotlinPoet [ClassName] representing the class
 * @throws Throwable If an exception other than [KSTypeNotPresentException] occurs
 */
@Suppress("NOTHING_TO_INLINE")
@OptIn(KspExperimental::class)
inline fun safeClassName(classnameGetter: () -> KClass<*>) = runCatching {
    classnameGetter().asTypeName()
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
