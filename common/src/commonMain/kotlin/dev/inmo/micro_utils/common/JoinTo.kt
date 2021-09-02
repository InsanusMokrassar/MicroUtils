package dev.inmo.micro_utils.common

inline fun <I, R> Iterable<I>.joinTo(
    crossinline separatorFun: (I) -> R?,
    prefix: R?,
    postfix: R?,
    crossinline transform: (I) -> R?
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

inline fun <I, reified R> Array<I>.joinTo(
    crossinline separatorFun: (I) -> R?,
    prefix: R?,
    postfix: R?,
    crossinline transform: (I) -> R?
): Array<R> = asIterable().joinTo(separatorFun, prefix, postfix, transform).toTypedArray()
