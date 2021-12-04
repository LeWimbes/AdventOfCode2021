fun main() {
    fun part1(input: String): Int {
        val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
        val lines = input.lines().map { line -> intRegex.findAll(line).toList().map { it.value.toInt() } }
            .filter { it.isNotEmpty() }
        val drawnNumbers = lines[0]
        val boards = buildList {
            lines.subList(1, lines.size).windowed(5, 5).forEach { board ->
                add(board.map { it.toCollection(ArrayList<Int?>(it.size)).toTypedArray() }.toTypedArray())
            }
        }

        drawnNumbers.forEach { drawn ->
            boards.forEach { board ->
                (0..4).forEach { y ->
                    (0..4).forEach { x ->
                        if (board[y][x] == drawn) {
                            board[y][x] = null
                            if ((0..4).all { board[it][x] == null } || (0..4).all { board[y][it] == null }) {
                                return board.flatten().sumOf { it ?: 0 } * drawn
                            }
                        }
                    }
                }
            }
        }

        return 0
    }

    fun part2(input: String): Int {
        val intRegex = Regex("(?<=^|\\s|,)[0-9]+(?=\$|\\s|,)")
        val lines = input.lines().map { line -> intRegex.findAll(line).toList().map { it.value.toInt() } }
            .filter { it.isNotEmpty() }
        val drawnNumbers = lines[0]
        val boards = ArrayList<Array<Array<Int?>>?>()
        boards.apply {
            lines.subList(1, lines.size).windowed(5, 5).forEach { board ->
                add(board.map { it.toCollection(ArrayList<Int?>(it.size)).toTypedArray() }.toTypedArray())
            }
        }

        drawnNumbers.forEach { drawn ->
            boards.indices.forEach boardsLoop@{ bi ->
                val board = boards[bi]
                if (board != null) {
                    (0..4).forEach { y ->
                        (0..4).forEach { x ->
                            if (board[y][x] == drawn) {
                                board[y][x] = null
                                if ((0..4).all { board[it][x] == null } || (0..4).all { board[y][it] == null }) {
                                    boards[bi] = null
                                    val boardsLeft = boards.filterNotNull()
                                    if (boardsLeft.isEmpty())
                                        return board.flatten().sumOf { it ?: 0 } * drawn
                                    return@boardsLoop
                                }
                            }
                        }
                    }
                }
            }
        }

        return 0
    }

    val input = readResource("day04") ?: ""
    println(part1(input))
    println(part2(input))
}
