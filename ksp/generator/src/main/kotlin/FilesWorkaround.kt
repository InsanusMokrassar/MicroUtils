package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.FileSpec
import java.io.File

fun KSClassDeclaration.writeFile(
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
        writer().use { writer ->
            fileSpecBuilder().writeTo(writer)
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
        writer().use { writer ->
            fileSpecBuilder().writeTo(writer)
        }
    }
}