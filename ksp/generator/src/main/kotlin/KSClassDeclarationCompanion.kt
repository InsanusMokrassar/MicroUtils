package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration

val KSClassDeclaration.companion
    get() = declarations.firstNotNullOfOrNull {
        (it as? KSClassDeclaration)?.takeIf { it.isCompanionObject }
    }
