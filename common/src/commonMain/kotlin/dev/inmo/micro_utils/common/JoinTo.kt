package dev.inmo.micro_utils.common

/**
 * Joins elements of this iterable into a list with separators between elements.
 * Each element is transformed using [transform], and separators are generated using [separatorFun].
 * Optional [prefix] and [postfix] can be added to the result.
 * Null values from transformations or separator function are skipped.
 *
 * @param I The type of elements in the input iterable
 * @param R The type of elements in the result list
 * @param separatorFun A function that generates a separator based on the current element
 * @param prefix Optional prefix to add at the beginning of the result
 * @param postfix Optional postfix to add at the end of the result
 * @param transform A function to transform each element
 * @return A list of transformed elements with separators
 */
inline fun <I, R> Iterable<I>.joinTo(
    separatorFun: (I) -> R?,
    prefix: R? = null,
    postfix: R? = null,
    transform: (I) -> R?
): List<R> {
    val result = mutableListOf<R>()
    val iterator = iterator()

    prefix ?.let(result::add)

    while (iterator.hasNext()) {
        val element = iterator.next()
        result.add(transform(element) ?: continue)

        if (iterator.hasNext()) {
            result.add(separatorFun(element) ?: continue)
        }
    }

    postfix ?.let(result::add)

    return result
}

/**
 * Joins elements of this iterable into a list with a constant separator between elements.
 * Each element is transformed using [transform].
 * Optional [prefix] and [postfix] can be added to the result.
 * Null values from transformations or separators are skipped.
 *
 * @param I The type of elements in the input iterable
 * @param R The type of elements in the result list
 * @param separator The separator to insert between elements
 * @param prefix Optional prefix to add at the beginning of the result
 * @param postfix Optional postfix to add at the end of the result
 * @param transform A function to transform each element
 * @return A list of transformed elements with separators
 */
inline fun <I, R> Iterable<I>.joinTo(
    separator: R? = null,
    prefix: R? = null,
    postfix: R? = null,
    transform: (I) -> R?
): List<R> = joinTo({ separator }, prefix, postfix, transform)

/**
 * Joins elements of this iterable into a list with separators between elements.
 * Separators are generated using [separatorFun].
 * Optional [prefix] and [postfix] can be added to the result.
 * Null values from separator function are skipped.
 *
 * @param I The type of elements
 * @param separatorFun A function that generates a separator based on the current element
 * @param prefix Optional prefix to add at the beginning of the result
 * @param postfix Optional postfix to add at the end of the result
 * @return A list of elements with separators
 */
inline fun <I> Iterable<I>.joinTo(
    separatorFun: (I) -> I?,
    prefix: I? = null,
    postfix: I? = null
): List<I> = joinTo<I, I>(separatorFun, prefix, postfix) { it }

/**
 * Joins elements of this iterable into a list with a constant separator between elements.
 * Optional [prefix] and [postfix] can be added to the result.
 * Null separators are skipped.
 *
 * @param I The type of elements
 * @param separator The separator to insert between elements
 * @param prefix Optional prefix to add at the beginning of the result
 * @param postfix Optional postfix to add at the end of the result
 * @return A list of elements with separators
 */
inline fun <I> Iterable<I>.joinTo(
    separator: I? = null,
    prefix: I? = null,
    postfix: I? = null
): List<I> = joinTo<I>({ separator }, prefix, postfix)

/**
 * Joins elements of this array into an array with separators between elements.
 * Each element is transformed using [transform], and separators are generated using [separatorFun].
 * Optional [prefix] and [postfix] can be added to the result.
 * Null values from transformations or separator function are skipped.
 *
 * @param I The type of elements in the input array
 * @param R The type of elements in the result array
 * @param separatorFun A function that generates a separator based on the current element
 * @param prefix Optional prefix to add at the beginning of the result
 * @param postfix Optional postfix to add at the end of the result
 * @param transform A function to transform each element
 * @return An array of transformed elements with separators
 */
inline fun <I, reified R> Array<I>.joinTo(
    separatorFun: (I) -> R?,
    prefix: R? = null,
    postfix: R? = null,
    transform: (I) -> R?
): Array<R> = asIterable().joinTo(separatorFun, prefix, postfix, transform).toTypedArray()

/**
 * Joins elements of this array into an array with a constant separator between elements.
 * Each element is transformed using [transform].
 * Optional [prefix] and [postfix] can be added to the result.
 * Null values from transformations or separators are skipped.
 *
 * @param I The type of elements in the input array
 * @param R The type of elements in the result array
 * @param separator The separator to insert between elements
 * @param prefix Optional prefix to add at the beginning of the result
 * @param postfix Optional postfix to add at the end of the result
 * @param transform A function to transform each element
 * @return An array of transformed elements with separators
 */
inline fun <I, reified R> Array<I>.joinTo(
    separator: R? = null,
    prefix: R? = null,
    postfix: R? = null,
    transform: (I) -> R?
): Array<R> = asIterable().joinTo(separator, prefix, postfix, transform).toTypedArray()
