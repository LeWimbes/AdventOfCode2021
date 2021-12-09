import java.util.*

fun main() {
    fun getHeightMap(input: String): Array<IntArray> {
        val lines = input.lines().filter { it.isNotBlank() }
        return Array(lines.size) { i ->
            lines[i].toCharArray().map { it.digitToIntOrNull() }.filterNotNull().toIntArray()
        }
    }

    fun part1(input: String): Int {
        val heightMap = getHeightMap(input)
        var riskSum = 0
        heightMap.indices.forEach { y ->
            heightMap[0].indices.forEach { x ->
                val height = heightMap[y][x]
                if ((x == 0 || height < heightMap[y][x - 1]) &&
                    (x == heightMap[0].lastIndex || height < heightMap[y][x + 1]) &&
                    (y == 0 || height < heightMap[y - 1][x]) &&
                    (y == heightMap.lastIndex || height < heightMap[y + 1][x])
                )
                    riskSum += height + 1
            }
        }
        return riskSum
    }

    fun part2(input: String): Int {
        val heightMap = getHeightMap(input)
        val basins = mutableListOf<Int>()
        heightMap.indices.forEach { y ->
            heightMap[0].indices.forEach { x ->
                val height = heightMap[y][x]
                if ((x == 0 || height < heightMap[y][x - 1]) &&
                    (x == heightMap[0].lastIndex || height < heightMap[y][x + 1]) &&
                    (y == 0 || height < heightMap[y - 1][x]) &&
                    (y == heightMap.lastIndex || height < heightMap[y + 1][x])
                ) {
                    val lowPoint = Pair(x, y)
                    val basin = mutableSetOf<Pair<Int, Int>>()
                    val queue = LinkedList<Pair<Int, Int>>()
                    queue.add(lowPoint)
                    while (queue.isNotEmpty()) {
                        val v = queue.pop()
                        if (!basin.add(v)) continue
                        val xV = v.first
                        val yV = v.second
                        val heightV = heightMap[yV][xV]

                        if (xV != 0 && heightMap[yV][xV - 1] > heightV && heightMap[yV][xV - 1] != 9 && Pair(
                                xV - 1,
                                yV
                            ) !in basin
                        )
                            queue.add(Pair(xV - 1, yV))
                        if (xV != heightMap[0].lastIndex && heightMap[yV][xV + 1] > heightV && heightMap[yV][xV + 1] != 9 && Pair(
                                xV + 1,
                                yV
                            ) !in basin
                        )
                            queue.add(Pair(xV + 1, yV))
                        if (yV != 0 && heightMap[yV - 1][xV] > heightV && heightMap[yV - 1][xV] != 9 && Pair(
                                xV,
                                yV - 1
                            ) !in basin
                        )
                            queue.add(Pair(xV, yV - 1))
                        if (yV != heightMap.lastIndex && heightMap[yV + 1][xV] > heightV && heightMap[yV + 1][xV] != 9 && Pair(
                                xV,
                                yV + 1
                            ) !in basin
                        )
                            queue.add(Pair(xV, yV + 1))
                    }
                    basins.add(basin.size)
                }
            }
        }
        basins.sortDescending()

        return basins[0] * basins[1] * basins[2]
    }

    val input = readResource("day09") ?: ""
    println(part1(input))
    println(part2(input))
}
