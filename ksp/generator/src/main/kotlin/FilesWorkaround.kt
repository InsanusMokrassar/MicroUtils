package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import java.io.File

fun KSDeclaration.writeFile(
    prefix: String = "",
    suffix: String = "",
    relatedPath: String = "",
    force: Boolean = false,
    fileSpecBuilder: () -> FileSpec
) {
    val containingFile = containingFile!!
    File(
        File(
            File(containingFile.filePath).parent,
            relatedPath
        ),
        "$prefix${simpleName.asString()}$suffix.kt"
    ).takeIf { force || !it.exists() } ?.apply {
        parentFile.mkdirs()
        val fileSpec = fileSpecBuilder()
        writer().use { writer ->
            fileSpec.writeTo(writer)
        }
    }
}

fun KSFile.writeFile(
    prefix: String = "",
    suffix: String = "",
    relatedPath: String = "",
    force: Boolean = false,
    fileSpecBuilder: () -> FileSpec
) {
    File(
        File(
            File(filePath).parent,
            relatedPath
        ),
        "$prefix${fileName.dropLastWhile { it != '.' }.removeSuffix(".")}$suffix.kt"
    ).takeIf { force || !it.exists() } ?.apply {
        parentFile.mkdirs()
        val fileSpec = fileSpecBuilder()
        writer().use { writer ->
            fileSpec.writeTo(writer)
        }
    }
}
