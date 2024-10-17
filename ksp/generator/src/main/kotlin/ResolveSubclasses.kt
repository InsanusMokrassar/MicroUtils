package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration

fun KSClassDeclaration.resolveSubclasses(): List<KSClassDeclaration> {
    return (getSealedSubclasses().flatMap {
        it.resolveSubclasses()
    }.ifEmpty {
        sequenceOf(this)
    }).toList()
}
