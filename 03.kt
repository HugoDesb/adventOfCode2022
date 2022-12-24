import java.io.File

fun computePriority(c: Char): Int{
    return if (c.isLowerCase()){
        c.code - 96
    }else{
        c.code - 38
    }
}

fun main(){
    val lines: List<String> = File("03-input").readLines()
    val pairs = lines.map{ line ->
        Pair(line.substring(0,line.length/2), line.substring(line.length/2, line.length))
    }
    val chars = pairs.map { pair ->
        pair.first.find { c -> pair.second.indexOf(c) != -1  }
    }
    val priorities = chars.map { item -> computePriority(item!!) }
    val sum = priorities.reduce { acc, i -> acc + i }

    println("Priorities for every rucksack: $sum")

    val groupedLines = lines.chunked(3)
    val badges = groupedLines.map {
        val (first, second, third) = it
        first.find { c -> second.indexOf(c) != -1 && third.indexOf(c) != -1 }
    }
    val badgesSum = badges.map { c -> computePriority(c!!) }.reduce { acc, unit -> acc + unit }
    println("Priorities for the badges: $badgesSum")
}