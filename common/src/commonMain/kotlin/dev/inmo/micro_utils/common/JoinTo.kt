package dev.inmo.micro_utils.common

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

inline fun <I, R> Iterable<I>.joinTo(
    separator: R? = null,
    prefix: R? = null,
    postfix: R? = null,
    transform: (I) -> R?
): List<R> = joinTo({ separator }, prefix, postfix, transform)

inline fun <I> Iterable<I>.joinTo(
    separatorFun: (I) -> I?,
    prefix: I? = null,
    postfix: I? = null
): List<I> = joinTo<I, I>(separatorFun, prefix, postfix) { it }

inline fun <I> Iterable<I>.joinTo(
    separator: I? = null,
    prefix: I? = null,
    postfix: I? = null
): List<I> = joinTo<I>({ separator }, prefix, postfix)

inline fun <I, reified R> Array<I>.joinTo(
    separatorFun: (I) -> R?,
    prefix: R? = null,
    postfix: R? = null,
    transform: (I) -> R?
): Array<R> = asIterable().joinTo(separatorFun, prefix, postfix, transform).toTypedArray()

inline fun <I, reified R> Array<I>.joinTo(
    separator: R? = null,
    prefix: R? = null,
    postfix: R? = null,
    transform: (I) -> R?
): Array<R> = asIterable().joinTo(separator, prefix, postfix, transform).toTypedArray()
