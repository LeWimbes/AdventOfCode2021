fun main() {
    fun part1(input: String): Int {
        val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
        val fish = intRegex.findAll(input).mapTo(ArrayList()) { it.value.toInt() }
        repeat(80) {
            for (i in 0 until fish.size) {
                if (fish[i] == 0) {
                    fish[i] = 6
                    fish.add(8)
                } else {
                    fish[i] -= 1
                }
            }
        }
        return fish.size
    }

    fun part2(input: String): Long {
        val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
        var fishMap = intRegex.findAll(input).mapTo(ArrayList()) { it.value.toInt() }.groupBy { it }
            .mapValues { it.value.size.toLong() }
        repeat(256) {
            val newMap = buildMap<Int, Long> {
                fishMap.forEach { entry ->
                    if (entry.key == 0) {
                        if (containsKey(6))
                            this[6] = this[6]!! + entry.value
                        else
                            put(6, entry.value)
                        put(8, entry.value)
                    } else {
                        if (containsKey(entry.key - 1))
                            this[entry.key - 1] = this[entry.key - 1]!! + entry.value
                        else
                            put(entry.key - 1, entry.value)
                    }
                }
            }
            fishMap = newMap
        }
        return fishMap.values.sum()
    }

    val input = readResource("day06") ?: ""
    println(part1(input))
    println(part2(input))
}
