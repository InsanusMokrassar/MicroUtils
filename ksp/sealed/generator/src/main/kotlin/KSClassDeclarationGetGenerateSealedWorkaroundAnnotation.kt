package dev.inmo.micro_utils.ksp.sealed.generator

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSClassDeclaration
import dev.inmo.micro_utils.ksp.sealed.GenerateSealedTypesWorkaround
import dev.inmo.micro_utils.ksp.sealed.GenerateSealedWorkaround
import dev.inmo.microutils.kps.sealed.GenerateSealedWorkaround as OldGenerateSealedWorkaround

@OptIn(KspExperimental::class)
val KSClassDeclaration.getGenerateSealedWorkaroundAnnotation
    get() = (getAnnotationsByType(GenerateSealedWorkaround::class).firstOrNull() ?: getAnnotationsByType(OldGenerateSealedWorkaround::class).firstOrNull())


@OptIn(KspExperimental::class)
val KSClassDeclaration.getGenerateSealedTypesWorkaroundAnnotation
    get() = getAnnotationsByType(GenerateSealedTypesWorkaround::class).firstOrNull()
