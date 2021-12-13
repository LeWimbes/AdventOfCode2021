fun main() {
    val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
    fun getInstance(input: String): Pair<Set<Pair<Int, Int>>, List<Pair<Boolean, Int>>> { // first: Set of Points; second: x true, y false
        val points = mutableSetOf<Pair<Int, Int>>()
        val folds = mutableListOf<Pair<Boolean, Int>>()
        input.lines().forEach { line ->
            if (line.isBlank()) return@forEach
            val temp = line.trim()
            if (temp.startsWith("fold along ")) {
                val parts = temp.split('=')
                folds.add(Pair(parts[0].last() == 'x', parts[1].toInt()))
            } else {
                val ints = intRegex.findAll(temp).mapTo(mutableListOf()) { it.value.toInt() }
                if (ints.size == 2) {
                    points.add(Pair(ints[0], ints[1]))
                }
            }
        }
        return Pair(points, folds)
    }

    fun part1(input: String): Int {
        val instance = getInstance(input)
        val fold = instance.second.first()

        val points = mutableSetOf<Pair<Int, Int>>()
        if (fold.first) {
            instance.first.forEach { point ->
                if (point.first < fold.second) points.add(point)
                else points.add(Pair(fold.second - (point.first - fold.second), point.second))
            }
        } else {
            instance.first.forEach { point ->
                if (point.second < fold.second) points.add(point)
                else points.add(Pair(point.first, fold.second - (point.second - fold.second)))
            }
        }

        return points.size
    }

    fun part2(input: String) {
        val instance = getInstance(input)
        var points = instance.first.toMutableSet()
        instance.second.forEach { fold ->
            val newPoints = mutableSetOf<Pair<Int, Int>>()
            if (fold.first) {
                points.forEach { point ->
                    if (point.first < fold.second) newPoints.add(point)
                    else newPoints.add(Pair(fold.second - (point.first - fold.second), point.second))
                }
            } else {
                points.forEach { point ->
                    if (point.second < fold.second) newPoints.add(point)
                    else newPoints.add(Pair(point.first, fold.second - (point.second - fold.second)))
                }
            }
            points = newPoints
        }

        val maxX = points.maxOf { it.first }
        val maxY = points.maxOf { it.second }
        val field = Array(maxY + 1) { IntArray(maxX + 1) }
        points.forEach { point ->
            field[point.second][point.first] = 4
        }
        println(field.joinToString("\n") { line -> line.joinToString(" ") { if (it == 0) " " else "#" } })
    }

    val input = readResource("day13") ?: ""
    println(part1(input))
    part2(input)
}
