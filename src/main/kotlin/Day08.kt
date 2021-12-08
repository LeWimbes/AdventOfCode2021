fun main() {
    fun getInstances(input: String): List<Pair<List<String>, List<String>>> =
        input.lines().filter { it.isNotBlank() }.map { line ->
            val parts = line.split('|')
            Pair(
                parts[0].split(' ').filter { it.isNotBlank() }.map { it.trim() },
                parts[1].split(' ').filter { it.isNotBlank() }.map { it.trim() })
        }

    fun part1(input: String): Int {
        val instances = getInstances(input)
        return instances.fold(0) { acc, pair ->
            acc + pair.second.count { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7 }
        }
    }

    fun part2(input: String): Int {
        val instances = getInstances(input)
        val segmentsValueMap = mapOf(
            setOf('a', 'b', 'c', 'e', 'f', 'g') to 0,
            setOf('c', 'f') to 1,
            setOf('a', 'c', 'd', 'e', 'g') to 2,
            setOf('a', 'c', 'd', 'f', 'g') to 3,
            setOf('b', 'c', 'd', 'f') to 4,
            setOf('a', 'b', 'd', 'f', 'g') to 5,
            setOf('a', 'b', 'd', 'e', 'f', 'g') to 6,
            setOf('a', 'c', 'f') to 7,
            setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') to 8,
            setOf('a', 'b', 'c', 'd', 'f', 'g') to 9
        )

        val mappings = ('a'..'g').toList().permutations().map { it.zip(('a'..'g')).toMap() }

        return instances.sumOf { inst ->
            val rightMapping = mappings.first { mapping ->
                inst.first.all { word -> segmentsValueMap.containsKey(word.mapTo(mutableSetOf()) { seg -> mapping[seg]!! }) }
            }
            inst.second.joinToString("") { digit ->
                val realSegments = digit.mapTo(mutableSetOf()) { rightMapping[it]!! }
                segmentsValueMap[realSegments].toString()
            }.toInt()
        }
    }

    val input = readResource("day08") ?: ""
    println(part1(input))
    println(part2(input))
}
