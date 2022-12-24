import java.io.File

fun main() {
    val lines: List<String> = File("04-input").readLines()
    val pairs = lines
        .map {
            val elfs = it.split(",")
            val first = elfs[0].split('-')
            val second = elfs[1].split('-')
            Pair(Pair(first[0].toInt(), first[1].toInt()),Pair(second[0].toInt(), second[1].toInt()))
        }
    val fullyOverlapped = pairs.filter {
        it.first.first <= it.second.first && it.first.second >= it.second.second
            || it.second.first <= it.first.first && it.second.second >= it.first.second
    }
    println("Count of fully overlapped: ${fullyOverlapped.size}")

    val overlapped = pairs.filter {
        (it.first.first..it.first.second).intersect((it.second.first..it.second.second)).isNotEmpty()
    }
    println("Count of overlapped: ${overlapped.size}")

}