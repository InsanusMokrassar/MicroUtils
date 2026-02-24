package dev.inmo.micro_ksp.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration

/**
 * Recursively resolves all subclasses of this sealed class declaration.
 * For sealed classes, it traverses the entire hierarchy of sealed subclasses.
 * For non-sealed classes (leaf nodes), it returns the class itself.
 *
 * @return A list of all concrete (non-sealed) subclass declarations in the hierarchy
 */
fun KSClassDeclaration.resolveSubclasses(): List<KSClassDeclaration> {
    return (getSealedSubclasses().flatMap {
        it.resolveSubclasses()
    }.ifEmpty {
        sequenceOf(this)
    }).toList()
}
