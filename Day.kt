import java.io.File

abstract class Day(private val day: Int) {
    val input: List<String> = File("${String.format("%02d", day)}-input").readLines()

    abstract fun solvePart1(): Any?

    abstract fun solvePart2(): Any?

    fun solve(){
        println(" ------------------------- Day $day ------------------------- ")
        println("Solution for part 1: ")
        println("${solvePart1()?: "Not Yet Implemented"}")
        println("Solution for part 2: ")
        println("${solvePart2()?: "Not Yet Implemented"}")
    }
}
