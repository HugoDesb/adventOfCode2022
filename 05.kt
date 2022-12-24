import java.io.File
import java.util.Random

data class Move(val count: Int, val from : Int, val to: Int)

class Cargo(val stacks: List<MutableList<Char>>){

    fun perform9001(move: Move){
        println(move)

        val crates = stacks[move.from - 1].takeLast(move.count)
        repeat(move.count){
            stacks[move.from - 1].removeLast()
        }
        stacks[move.to - 1].addAll(crates)

        print()
    }

    fun perform9000(move: Move){
        println(move)
        repeat(move.count) {
            val c = stacks[move.from - 1].removeLast()
            stacks[move.to - 1].add(c)
        }
        print()
    }

    fun print(){
        println("---------------------------------------------------------------------------------")
        stacks.forEachIndexed { index, it ->
            val strCrates = it.fold(""){ acc, crate ->
                "$acc$crate "
            }
            println("[${index+1}] $strCrates")
        }
    }

    companion object {
        fun parseString(lines: List<String>): Cargo{
            val stackNumber= lines.last().last().digitToInt()
            println(stackNumber)
            val stacks: List<MutableList<Char>> = List(stackNumber){
                mutableListOf()
            }
            val reversedStacksLines = lines.dropLast(1).reversed()

            reversedStacksLines.forEach { line ->
                println(line)
                var index = 0
                while (index*4<line.length){
                    if(line[index*4] == '['){
                        stacks[index].add(line[index*4+1])
                    }
                    index++
                }
            }
            return Cargo(stacks)
        }
    }
}


fun main() {
    val lines: List<String> = File("05-input").readLines()
    val cargoLines = lines.takeWhile { it != "" }
    val instructionLines = lines.dropWhile { it != "" }.drop(1)
    val moves : List<Move> = instructionLines.map {
        //move 6 from 2 to 6
        val elems = it.split(' ')
        Move(elems[1].toInt(), elems[3].toInt(), elems[5].toInt())
    }
    println(cargoLines.size)
    val cargo = Cargo.parseString(cargoLines)
    cargo.print()

    moves.forEach { cargo.perform9001(it) }

    cargo.print()

    val top = cargo.stacks.fold("") { acc, it ->
        "$acc${it.last()}"
    }
    println(top)
}