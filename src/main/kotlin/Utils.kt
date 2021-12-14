fun readResource(path: String) = object {}.javaClass.getResource(path)?.readText()

inline fun <T, R> Iterable<T>.flatMapNotNull(transform: (T) -> Iterable<R>?): List<R> =
    flatMapNotNullTo(ArrayList(), transform)

inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapNotNullTo(
    destination: C,
    transform: (T) -> Iterable<R>?
): C {
    for (element in this) {
        transform(element)?.let { destination.addAll(it) }
    }
    return destination
}

fun <T> Collection<T>.permutations(): Set<List<T>> {
    if (this.isEmpty()) return emptySet()
    val elem = this.first()
    if (this.size == 1) return setOf(listOf(elem))

    return this.drop(1).permutations().flatMapTo(HashSet()) { sub ->
        buildList {
            add(listOf(elem) + sub)
            (1..sub.lastIndex).forEach { i ->
                add(sub.dropLast(sub.size - i) + elem + sub.drop(i))
            }
            add(sub + elem)
        }
    }
}

fun <T> Iterable<T>.eachCountLong(): Map<T, Long> = this.eachCountLongTo(LinkedHashMap())

fun <T, M : MutableMap<in T, Long>> Iterable<T>.eachCountLongTo(destination: M): M {
    this.forEach { key ->
        val count = destination[key] ?: 0
        destination[key] = count + 1
    }
    return destination
}
