package dev.inmo.micro_utils.meta

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

/**
 * A polymorphic container for storing heterogeneous key-value pairs with type-safe retrieval.
 * Each key is bound to a specific type, enabling type-safe access to stored values.
 *
 * @property map The underlying map storing key-value pairs with polymorphic values.
 */
@Serializable
data class MetaContainer(
    @MetaContainerRootMapWarning
    val map: Map<Key<*>, @Polymorphic Any>
) {
    /**
     * A marker interface for type-safe keys in [MetaContainer].
     *
     * @param T The type of value associated with this key.
     */
    interface Key<T : Any>

    /**
     * Retrieves a value from the container by its key.
     *
     * @param key The type-safe key to look up.
     * @return The value associated with the key, or null if not present.
     */
    @Suppress("UNCHECKED_CAST", "OPT_IN_USAGE")
    operator fun <T : Any> get(key: Key<T>): T? = map[key] as? T?

    /**
     * Checks whether a value exists for the given key.
     *
     * @param key The type-safe key to check.
     * @return true if the key exists and has a non-null value, false otherwise.
     */
    operator fun <T : Any> contains(key: Key<T>): Boolean = get(key) != null

    /**
     * Builder for constructing [MetaContainer] instances with a fluent API.
     */
    class Builder(
        @MetaContainerRootMapWarning
        private val map: MutableMap<Key<*>, Any> = mutableMapOf<Key<*>, Any>()
    ) {


        /**
         * Puts a value associated with the given key into the builder.
         *
         * @param k The type-safe key.
         * @param v The value to store.
         */
        fun <T : Any> put(k: Key<T>, v: T) {
            map[k] = v
        }

        /**
         * Retrieves a value from the builder by its key.
         *
         * @param key The type-safe key to look up.
         * @return The value associated with the key, or null if not present.
         */
        @Suppress("UNCHECKED_CAST")
        operator fun <T : Any> get(key: Key<T>): T? = map[key] as T?

        /**
         * Checks whether a value exists for the given key in the builder.
         *
         * @param key The type-safe key to check.
         * @return true if the key exists and has a non-null value, false otherwise.
         */
        operator fun <T : Any> contains(key: Key<T>): Boolean = get(key) != null

        /**
         * Builds and returns the immutable [MetaContainer] instance.
         *
         * @return A new [MetaContainer] with the accumulated key-value pairs.
         */
        fun build(): MetaContainer = MetaContainer(map.toMap())
    }

    companion object {
        /**
         * An empty [MetaContainer] instance with no entries.
         */
        val EMPTY = MetaContainer(emptyMap())
    }
}



