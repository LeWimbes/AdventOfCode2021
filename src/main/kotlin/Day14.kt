import java.util.*

fun main() {
    fun getInstance(input: String): Pair<String, Map<Pair<Char, Char>, Char>> {
        val lines = input.lines().filterTo(LinkedList()) { it.isNotBlank() }
        val polymer = lines.remove().trim()
        val rules = buildMap(lines.size - 1) {
            lines.forEach { line ->
                val parts = line.split(" -> ")
                val materials = parts[0].trimStart()
                val product = parts[1].trimStart()
                put(Pair(materials[0], materials[1]), product[0])
            }
        }
        return Pair(polymer, rules)
    }

    fun part1(input: String): Int {
        val instance = getInstance(input)
        val polymer = StringBuilder(instance.first)

        repeat(10) {
            var offset = 1
            polymer.zipWithNext().forEach { pair ->
                polymer.insert(offset, instance.second[pair]!!)

                offset += 2
            }
        }

        val occurrences = polymer.groupingBy { it }.eachCount()
        val min = occurrences.minByOrNull { it.value }!!
        val max = occurrences.maxByOrNull { it.value }!!

        return max.value - min.value
    }

    fun part2(input: String): Long {
        val instance = getInstance(input)
        val occurrences = LongArray(26)
        instance.first.forEach { char -> occurrences[char - 'A']++ }
        var polymer = instance.first.zipWithNext().eachCountLong()

        instance.first.groupingBy { it }.eachCount()

        repeat(40) {
            val newPolymer = HashMap<Pair<Char, Char>, Long>(polymer.size)
            polymer.forEach { entry ->
                val result = instance.second[entry.key]!!
                occurrences[result - 'A'] += entry.value

                val left = Pair(entry.key.first, result)
                val leftCount = newPolymer[left] ?: 0
                newPolymer[left] = leftCount + entry.value
                val right = Pair(result, entry.key.second)
                val rightCount = newPolymer[right] ?: 0
                newPolymer[right] = rightCount + entry.value
            }
            polymer = newPolymer
        }

        return occurrences.maxOf { it } - occurrences.filter { it != 0L }.minOf { it }
    }

    val input = readResource("day14") ?: ""
    println(part1(input))
    println(part2(input))
}
