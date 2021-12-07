import kotlin.math.abs

fun main() {
    val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
    fun getCraps(input: String) = intRegex.findAll(input).mapTo(ArrayList()) { it.value.toInt() }

    fun findBetterFuel(
        craps: ArrayList<Int>,
        startPos: Int,
        fuelFun: (ArrayList<Int>, Int) -> Int,
        fuel: Int,
        offset: Int,
        left: Boolean
    ): Int {
        val newFuel = fuelFun(craps, startPos + offset)
        if (newFuel > fuel)
            return fuel
        return findBetterFuel(craps, startPos, fuelFun, fuel, if (left) offset - 1 else offset + 1, left)
    }

    fun getOptimalFuel(craps: ArrayList<Int>, startPos: Int, fuelFun: (ArrayList<Int>, Int) -> Int): Int {
        var fuel = fuelFun(craps, startPos)
        fuel = findBetterFuel(craps, startPos, fuelFun, fuel, -1, true)
        fuel = findBetterFuel(craps, startPos, fuelFun, fuel, 1, false)

        return fuel
    }

    fun part1(input: String): Int {
        val craps = getCraps(input)
        craps.sort()
        val mid = craps[craps.size / 2]

        return getOptimalFuel(craps, mid) { craps, position ->
            craps.fold(0) { acc, crap -> acc + abs(crap - position) }
        }
    }

    fun part2(input: String): Int {
        val craps = getCraps(input)
        val avg = craps.average().toInt()

        return getOptimalFuel(craps, avg) { craps, position ->
            craps.fold(0) { acc, crap ->
                val n = abs(crap - position)
                acc + (n * (n + 1)) / 2
            }
        }
    }

    val input = readResource("day07") ?: ""
    println(part1(input))
    println(part2(input))
}
