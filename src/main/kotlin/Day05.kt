import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    fun part1(input: String): Int {
        val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
        return input.lines().map { line -> intRegex.findAll(line).toList().map { it.value.toInt() } }
            .flatMapNotNull { line ->
                if (line.size != 4) {
                    null
                } else if (line[0] == line[2]) {
                    (min(line[1], line[3])..max(line[1], line[3])).map { Pair(line[0], it) }
                } else if (line[1] == line[3]) {
                    (min(line[0], line[2])..max(line[0], line[2])).map { Pair(it, line[1]) }
                } else {
                    null
                }
            }
            .groupingBy { it }.eachCount().count { it.value >= 2 }
    }

    fun part2(input: String): Int {
        val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
        return input.lines().map { line -> intRegex.findAll(line).toList().map { it.value.toInt() } }
            .flatMapNotNull { line ->
                if (line.size != 4) {
                    null
                } else if (line[0] == line[2]) {
                    (min(line[1], line[3])..max(line[1], line[3])).map { Pair(line[0], it) }
                } else if (line[1] == line[3]) {
                    (min(line[0], line[2])..max(line[0], line[2])).map { Pair(it, line[1]) }
                } else {
                    val x1: Int
                    val y1: Int
                    val x2: Int
                    val y2: Int
                    if (line[0] < line[2] || (line[0] == line[2] && line[1] < line[3])) {
                        x1 = line[0]
                        y1 = line[1]
                        x2 = line[2]
                        y2 = line[3]
                    } else {
                        x1 = line[2]
                        y1 = line[3]
                        x2 = line[0]
                        y2 = line[1]
                    }

                    val dir = if (y1 < y2) 1 else -1
                    (0..x2 - x1).map { Pair(x1 + it, y1 + dir * it) }
                }
            }
            .groupingBy { it }.eachCount().count { it.value >= 2 }
    }

    val input = readResource("day05") ?: ""
    println(part1(input))
    println(part2(input))
}
