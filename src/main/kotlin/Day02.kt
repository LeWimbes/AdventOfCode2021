fun main() {
    fun part1(input: String): Int {
        return input.lines()
            .fold(Pair(0, 0)) { pos, inst ->
                when {
                    inst.startsWith("forward ") -> Pair(pos.first + inst.substring(8).trim().toInt(), pos.second)
                    inst.startsWith("up ") -> Pair(pos.first, pos.second - inst.substring(3).trim().toInt())
                    inst.startsWith("down ") -> Pair(pos.first, pos.second + inst.substring(5).trim().toInt())
                    else -> pos
                }
            }.run { first * second }
    }

    fun part2(input: String): Int {
        return input.lines()
            .fold(Triple(0, 0, 0)) { pos, inst ->
                when {
                    inst.startsWith("forward ") -> Triple(
                        pos.first + inst.substring(8).trim().toInt(),
                        pos.second + pos.third * inst.substring(8).trim().toInt(),
                        pos.third
                    )
                    inst.startsWith("up ") -> Triple(
                        pos.first,
                        pos.second,
                        pos.third - inst.substring(3).trim().toInt()
                    )
                    inst.startsWith("down ") -> Triple(
                        pos.first,
                        pos.second,
                        pos.third + inst.substring(5).trim().toInt()
                    )
                    else -> pos
                }
            }.run { first * second }
    }

    val input = readResource("day02") ?: ""
    println(part1(input))
    println(part2(input))
}
