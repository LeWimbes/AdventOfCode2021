import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun getDumboField(input: String) = input.lines().mapNotNull { if (it.isBlank()) null else it.trim() }
        .map { it.mapNotNull { c -> c.digitToIntOrNull() }.toIntArray() }.toTypedArray()

    fun simulateStep(dumboField: Array<IntArray>): Int {
        val flashed = mutableSetOf<Pair<Int, Int>>()
        val toFlash: Queue<Pair<Int, Int>> = LinkedList()

        (0..dumboField.lastIndex).forEach { y ->
            (0..dumboField[0].lastIndex).forEach { x ->
                dumboField[y][x] += 1
                if (dumboField[y][x] == 10) toFlash.add(Pair(x, y))
            }
        }

        while (toFlash.isNotEmpty()) {
            val dumbo = toFlash.poll()
            flashed.add(dumbo)

            (max(0, dumbo.second - 1)..min(dumboField.lastIndex, dumbo.second + 1)).forEach { y ->
                (max(0, dumbo.first - 1)..min(dumboField[0].lastIndex, dumbo.first + 1)).forEach { x ->
                    dumboField[y][x] += 1
                    if (dumboField[y][x] == 10) toFlash.add(Pair(x, y))
                }
            }
        }

        flashed.forEach { dumbo ->
            dumboField[dumbo.second][dumbo.first] = 0
        }
        return flashed.size
    }

    fun part1(input: String): Int {
        val dumboField = getDumboField(input)

        var flashes = 0
        repeat(100) {
            flashes += simulateStep(dumboField)
        }

        return flashes
    }

    fun part2(input: String): Int {
        val dumboField = getDumboField(input)

        repeat(Int.MAX_VALUE) { step ->
            if (simulateStep(dumboField) == dumboField.size * dumboField[0].size) return step + 1
        }
        return -1
    }

    val input = readResource("day11") ?: ""
    println(part1(input))
    println(part2(input))
}
