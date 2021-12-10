import java.util.*

fun main() {
    fun getLines(input: String) = input.lines().mapNotNull { if (it.isBlank()) null else it.trim() }

    fun getMatch(char: Char): Char? = when (char) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> null
    }

    fun part1(input: String): Int {
        val lines = getLines(input)

        fun getPoints(char: Char): Int = when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> throw IllegalArgumentException()
        }

        return lines.sumOf { line ->
            run {
                val expected = Stack<Char>()
                line.forEach { c ->
                    getMatch(c)?.let { expected.push(it) }
                        ?: if (expected.peek() == c) expected.pop() else return@run getPoints(c)
                }
                0
            }
        }
    }

    fun part2(input: String): Long {
        val lines = getLines(input)

        fun getPoints(char: Char): Int = when (char) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> throw IllegalArgumentException()
        }

        val scores = lines.mapNotNull { line ->
            run {
                val expected = Stack<Char>()
                line.forEach { c ->
                    getMatch(c)?.let { expected.push(it) }
                        ?: if (expected.peek() == c) expected.pop() else return@run null
                }
                if (expected.isEmpty())
                    return@run null

                var score: Long = 0
                while (expected.isNotEmpty()) {
                    score = score * 5 + getPoints(expected.pop())
                }
                score
            }
        }.sorted()
        return scores[scores.size / 2]
    }

    val input = readResource("day10") ?: ""
    println(part1(input))
    println(part2(input))
}
