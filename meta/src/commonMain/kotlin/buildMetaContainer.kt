package dev.inmo.micro_utils.meta

/**
 * DSL builder function for creating a [MetaContainer] with a lambda block.
 *
 * @param block A lambda with receiver ([MetaContainer.Builder]) to configure the container.
 * @return A new [MetaContainer] instance built from the DSL block.
 */
fun buildMetaContainer(block: MetaContainer.Builder.() -> Unit): MetaContainer {
    val builder = MetaContainer.Builder()
    builder.block()
    return builder.build()
}