import java.io.File

enum class Strategy {WIN, LOSE, DRAW}
class Instruction(private val opponent: Choice, private val strategy: Strategy){

    fun createRound(): Round {
        return when(strategy){
            Strategy.WIN ->  Round(opponent, opponent.win())
            Strategy.LOSE ->  Round(opponent, opponent.lose())
            else -> Round(opponent, opponent.draw())
        }
    }

    companion object {
        fun parseString(line: String): Instruction {
            val opponent = when(line[0]){
                'A' -> Choice(1)
                'B' -> Choice(2)
                else -> Choice(3)
            }

            val strategy = when(line[2]){
                'X' -> Strategy.LOSE
                'Y' -> Strategy.DRAW
                else -> Strategy.WIN
            }
            return Instruction(opponent, strategy)
        }
    }
}
class Round(private val opponent: Choice,
            private val you: Choice){
    val score: Int
        get() = you.scoreAgainst(opponent)
}
class Choice(private val weight: Int): Comparable<Choice> {

    fun win():Choice{
        val newWeight = if (weight == 3) 1 else weight+1
        return Choice(newWeight)
    }

    fun lose():Choice{
        val newWeight = if (weight == 1) 3 else weight-1
        return Choice(newWeight)
    }

    fun draw():Choice{
        return Choice(weight)
    }

    fun scoreAgainst(opponent: Choice): Int {
        return weight + compareTo(opponent)
    }

    override fun compareTo(other: Choice): Int {
        return if(weight == other.weight) 3
        else if ((weight+1)%3 == other.weight%3) 0
        else 6
    }
}

fun main(){
    val lines: List<String> = File("02-input").readLines()

    val instructions = lines.map { line -> Instruction.parseString(line)}
    val rounds = instructions.map { instruction -> instruction.createRound() }
    val score = rounds.fold(0){ acc, round -> acc + round.score }
    println("The final score is $score")
}