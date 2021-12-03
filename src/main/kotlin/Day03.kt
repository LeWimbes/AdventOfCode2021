fun main() {
    fun part1(input: String): Int {
        val bitLines = input.lines().mapNotNull { line ->
            if (line.isNotBlank()) line.trim().toCharArray().map { if (it == '1') 1 else 0 } else null
        }
        val ones = IntArray(bitLines[0].size)
        bitLines.forEach { bits ->
            ones.indices.forEach { ones[it] += bits[it] }
        }
        var gamma = ""
        var epsilon = ""
        ones.forEach {
            if (it > bitLines.size - it) {
                gamma += '1'
                epsilon += '0'
            } else {
                gamma += '0'
                epsilon += '1'
            }
        }
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: String): Int {
        val bitLines = input.lines().mapNotNull { line ->
            if (line.isNotBlank()) line.trim() else null
        }

        val oxy = bitLines.toMutableList()
        var col = 0
        while (oxy.size > 1) {
            var ones = 0
            oxy.forEach { bits ->
                ones += bits[col].digitToInt()
            }
            val zeros = oxy.size - ones
            if (ones >= zeros) {
                oxy.removeAll { it[col] == '0' }
            } else {
                oxy.removeAll { it[col] == '1' }
            }
            col++
        }

        val co2 = bitLines.toMutableList()
        col = 0
        while (co2.size > 1) {
            var ones = 0
            co2.forEach { bits ->
                ones += bits[col].digitToInt()
            }
            val zeros = co2.size - ones
            if (zeros > ones) {
                co2.removeAll { it[col] == '0' }
            } else {
                co2.removeAll { it[col] == '1' }
            }
            col++
        }

        return oxy[0].toInt(2) * co2[0].toInt(2)
    }

    val input = readResource("day03") ?: ""
    println(part1(input))
    println(part2(input))
}
