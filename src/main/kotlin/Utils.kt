fun readResource(path: String) = object {}.javaClass.getResource(path)?.readText()

inline fun <T, R> Iterable<T>.flatMapNotNull(transform: (T) -> Iterable<R>?): List<R> {
    return flatMapNotNullTo(ArrayList(), transform)
}

inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapNotNullTo(
    destination: C,
    transform: (T) -> Iterable<R>?
): C {
    for (element in this) {
        transform(element)?.let { destination.addAll(it) }
    }
    return destination
}
