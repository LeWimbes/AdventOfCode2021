fun main() {
    fun part1(input: String): Int {
        return input.lines().mapNotNull { it.trim().toIntOrNull() }
            .zipWithNext()
            .count { (i1, i2) -> i2 > i1 }
    }

    fun part2(input: String): Int {
        return input.lines().mapNotNull { it.trim().toIntOrNull() }
            .windowed(3, 1, false) { it.sum() }
            .zipWithNext()
            .count { (i1, i2) -> i2 > i1 }
    }

    val input = readResource("day01") ?: ""
    println(part1(input))
    println(part2(input))
}
