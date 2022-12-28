
class Day11: Day(11){

    private val monkeys = input.chunked(7).map { Monkey.of(it) }

    override fun solvePart1(): Long {
        rounds(20) { it / 3}
        return monkeys.business()
    }

    override fun solvePart2() = null
    /*{
        val divisorProduct = monkeys.map { it.test }.reduce(Long::times)
        rounds(10_000) { it % divisorProduct }
        return monkeys.business()
    }*/

    private fun List<Monkey>.business(): Long =
        sortedByDescending { it.inspections }.let { it[0].inspections * it[1].inspections }

    private fun rounds(numRounds: Int, changeWorryLevel: (Long) -> Long){
        repeat(numRounds){
            monkeys.forEach { it.inspectItems(monkeys, changeWorryLevel) }
        }
    }

    private class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val test: Long,
        val trueMonkey: Int,
        val falseMonkey: Int){
        var inspections: Long = 0

        fun inspectItems(monkeys: List<Monkey>, changeWorryLevel: (Long) -> Long) {
            items.forEach { item ->
                val newWorry = changeWorryLevel(operation(item))
                val target = if (newWorry % test == 0L) trueMonkey else falseMonkey
                monkeys[target].items.add(newWorry)
            }
            inspections += items.size
            items.clear()
        }

        override fun toString(): String {
            return "Monkey(items=$items, operation=$operation, test=$test, trueMonkey=$trueMonkey, falseMonkey=$falseMonkey, inspections=$inspections)"
        }

        companion object {
            fun of(lines: List<String>): Monkey{
                val items = parseItems(lines[1])
                val operation = parseOperation(lines[2])
                val divisor = lines[3].split(" ").last().toLong()
                val trueMonkey = lines[4].split(" ").last().toInt()
                val falseMonkey = lines[5].split(" ").last().toInt()

                return Monkey(items.toMutableList(), operation, divisor, trueMonkey, falseMonkey)
            }
            private fun parseItems(line: String): List<Long> =
                line.trim().removePrefix("Starting items: ").split(",").map { it.trim().toLong() }
            private fun parseOperation(line: String): (Long)->Long {
                val splitted = line.split(" ")
                val lastNumber = splitted.last()
                return when(splitted[splitted.size - 2]){
                    "*" -> {x -> x * (lastNumber.toLongOrNull() ?: x)}
                    else -> {x -> x + (lastNumber.toLongOrNull() ?: x)}
                }
            }
        }
    }
}


fun main() {
    val today = Day11()
    today.solve()
}