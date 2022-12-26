import java.io.File

class CPU{
    var register = 1
    var cycle = 0
    var checkStrength = 40
    val signals = mutableListOf<Int>()

    var line : String = ""

    fun execute(instruction: CPUInstruction){
        instruction.unitOperations.forEach { op ->
            println("OPERATION: $op")
            playFullCycle(op.getOp())
        }
    }
    private fun playFullCycle(operation: (Int) -> Int){
        // Start cycle
        cycle ++

        // During cycle
        if(cycle == checkStrength){
            val signalStrength = cycle * register
            signals.add(signalStrength)
            checkStrength += 40
        }
        buildLine(cycle, register)

        // End cycle
        register = operation(register)

    }

    private fun buildLine(cycle: Int, register: Int) {
        val x = (cycle-1) % 40
        val pixel = if(x >= register-1 && x <= register+1) "#" else "."
        line += pixel
    }
}

class CPUInstruction(val unitOperations: List<UnitOp>) {
    companion object{
        fun parseString(line: String): CPUInstruction{
            val splitted = line.split(' ')
            return when(splitted[0]){
                "addx" -> CPUInstruction(listOf(NO(), ADD(splitted[1].toInt())))
                else -> CPUInstruction(listOf(NO()))
            }
        }
    }

    override fun toString(): String {
        return "CPUInstruction(unitOperations=$unitOperations)"
    }

}
interface UnitOp{
    fun getOp(): (Int) -> Int
}
class ADD(private val x: Int): UnitOp{
    override fun getOp(): (Int) -> Int = { register -> x + register}
    override fun toString(): String {
        return "ADD(x=$x)"
    }
}
class NO: UnitOp{
    override fun getOp(): (Int) -> Int = { register -> register}
    override fun toString(): String {
        return "NO()"
    }
}

fun main() {
    val lines: List<String> = File("10-input").readLines()
    val instructions = lines.map { line -> CPUInstruction.parseString(line) }
    val cpu = CPU()
    instructions.forEach { cpu.execute(it) }
    //val sum = cpu.signals.sum()
    val linesCRT = cpu.line.chunked(40)
    linesCRT.forEach { println(it) }

}