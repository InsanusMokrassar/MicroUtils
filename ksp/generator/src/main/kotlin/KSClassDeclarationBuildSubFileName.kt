package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration

val KSClassDeclaration.buildSubFileName: String
    get() {
        val parentDeclarationCaptured = parentDeclaration
        val simpleNameString = simpleName.asString()
        return when (parentDeclarationCaptured) {
            is KSClassDeclaration -> parentDeclarationCaptured.buildSubFileName
            else -> ""
        } + simpleNameString
    }
