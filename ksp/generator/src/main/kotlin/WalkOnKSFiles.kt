package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclarationContainer
import com.google.devtools.ksp.symbol.KSFile

fun KSClassDeclaration.findSubClasses(subSymbol: KSAnnotated): Sequence<KSClassDeclaration> {
    return when (subSymbol) {
        is KSClassDeclaration -> if (subSymbol.getAllSuperTypes().map { it.declaration }.contains(this)) {
            sequenceOf(subSymbol)
        } else {
            sequenceOf()
        }
        else -> sequenceOf()
    } + if (subSymbol is KSDeclarationContainer) {
        subSymbol.declarations.flatMap {
            findSubClasses(it)
        }
    } else {
        sequenceOf()
    }
}

fun KSClassDeclaration.findSubClasses(files: Sequence<KSAnnotated>): Sequence<KSClassDeclaration> {
    return files.flatMap {
        findSubClasses(it)
    }
}
