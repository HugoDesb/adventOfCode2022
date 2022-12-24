import java.io.File

class Elf(
    var foodItems: MutableList<Int> = mutableListOf()
){
    val total: Int
        get() = foodItems.fold(0){ a,b -> a + b }
}

fun main() {
    val lines: List<String> = File("01-input").readLines()

    val elfs = lines.foldRight(mutableListOf(Elf())) { line, acc ->
        if (line.isEmpty()) {
            acc.add(Elf())
            acc
        } else {
            acc.last().foodItems.add(line.toInt())
            acc
        }
    }

    val maxElf = elfs.maxBy { elf -> elf.total }
    println("The elf carrying the most Calories carries ${maxElf.total}")

    val sortedElfs = elfs.sortedByDescending { elf -> elf .total }
    println("The elf carrying the most Calories carries ${sortedElfs[0].total}")
    println("The second elf carrying the most Calories carries ${sortedElfs[1].total}")
    println("The third elf carrying the most Calories carries ${sortedElfs[2].total}")
    println("They carry ${sortedElfs[0].total+ sortedElfs[1].total + sortedElfs[2].total}")

}

