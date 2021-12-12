fun main() {
    fun String.isBigCave(): Boolean = this.first() in ('A'..'Z')

    fun getGraph(input: String): Map<String, Set<String>> {
        val graph = mutableMapOf<String, MutableSet<String>>()
        val edges = input.lines().filter { it.isNotBlank() }.mapNotNull {
            val parts = it.split('-')
            if (parts.size != 2) null
            else Pair(parts[0].trim(), parts[1].trim())
        }
        edges.forEach { edge ->
            if (graph.containsKey(edge.first)) graph[edge.first]!!.add(edge.second)
            else graph[edge.first] = mutableSetOf(edge.second)

            if (graph.containsKey(edge.second)) graph[edge.second]!!.add(edge.first)
            else graph[edge.second] = mutableSetOf(edge.first)
        }

        return graph
    }

    fun depthSearch(
        graph: Map<String, Set<String>>,
        specialCaves: MutableMap<String, Int>,
        current: String,
        end: String,
        visited: List<String>,
        result: MutableSet<List<String>>
    ) {
        val newVisited = visited + current
        if (current == end) {
            result.add(newVisited.toList())
            return
        }
        graph[current]!!.forEach { child ->
            if (specialCaves.containsKey(child) && specialCaves[child]!! > 0) {
                specialCaves[child] = specialCaves[child]!! - 1
                depthSearch(graph, specialCaves, child, end, newVisited, result)
                specialCaves[child] = specialCaves[child]!! + 1 // restore state
            } else if (child.isBigCave() || child !in newVisited) {
                depthSearch(graph, specialCaves, child, end, newVisited, result)
            }
        }
    }

    fun part1(input: String): Int {
        val graph = getGraph(input)
        val result = mutableSetOf<List<String>>()
        depthSearch(
            graph,
            mutableMapOf(),
            "start",
            "end",
            listOf(),
            result
        )
        return result.size
    }

    fun part2(input: String): Int {
        val graph = getGraph(input)
        val result = mutableSetOf<List<String>>()
        graph.keys.forEach { cave ->
            if (cave != "start" && cave != "end" && !cave.isBigCave()) {
                depthSearch(
                    graph,
                    mutableMapOf(cave to 2),
                    "start",
                    "end",
                    listOf(),
                    result
                )
            }
        }
        return result.size
    }

    val input = readResource("day12") ?: ""
    println(part1(input))
    println(part2(input))
}
