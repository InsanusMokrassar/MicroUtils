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
    forceUppercase: Boolean = true,
    fileSpecBuilder: () -> FileSpec
) {
    val containingFile = containingFile!!
    val simpleName = if (forceUppercase) {
        val rawSimpleName = simpleName.asString()
        rawSimpleName.replaceFirst(rawSimpleName.first().toString(), rawSimpleName.first().uppercase())
    } else {
        simpleName.asString()
    }
    File(
        File(
            File(containingFile.filePath).parent,
            relatedPath
        ),
        "$prefix${simpleName}$suffix.kt"
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
