import java.io.File

val top = Direction(-1,0)
val right = Direction(0, 1)
val bottom = Direction(1, 0)
val left = Direction(0, -1)

data class Direction(val x: Int, val y: Int)

fun scenicScore(x: Int, y : Int, trees: List<List<Int>>) =
    viewingDistanceDirection(x,y,top, trees) *
            viewingDistanceDirection(x,y,right, trees) *
            viewingDistanceDirection(x,y,bottom, trees) *
            viewingDistanceDirection(x,y,left, trees)

fun viewingDistanceDirection(x: Int , y : Int, direction: Direction, trees: List<List<Int>>): Int{
    return if(x == 0 || x == trees.size-1 || y == 0 || y == trees.first().size-1){
        0
    }else{
        var xPlusOne = x
        var yPlusOne = y
        var score = 0
        do{
            xPlusOne += direction.x
            yPlusOne += direction.y
            score++
        } while((xPlusOne > 0 && xPlusOne < trees.size-1) && (yPlusOne > 0 && yPlusOne < trees.first().size-1) && trees[x][y] > trees[xPlusOne][yPlusOne])
        score
    }
}

fun checkIfVisibleFromDirection(x: Int, y: Int, direction: Direction, trees: List<List<Int>>): Boolean{
    return if(x == 0 || x == trees.size-1 || y == 0 || y == trees.first().size-1){
        true
    }else{
        var xPlusOne = x
        var yPlusOne = y
        val treesInLine = mutableListOf<Int>()
        do{
            xPlusOne += direction.x
            yPlusOne += direction.y
            treesInLine.add(trees[xPlusOne][yPlusOne])
        } while((xPlusOne > 0 && xPlusOne < trees.size-1) && (yPlusOne > 0 && yPlusOne < trees.first().size-1))

        trees[x][y] > treesInLine.max()
    }
}

fun main() {
    val lines: List<String> = File("08-input").readLines()
    val matrix = lines.map { it.toList().map { c -> c.digitToInt() } }

    val highTrees = matrix
        .flatMapIndexed { x, line ->
            line.filterIndexed { y, _ ->
                checkIfVisibleFromDirection(x, y, top, matrix)
                        || checkIfVisibleFromDirection(x, y, right, matrix)
                        || checkIfVisibleFromDirection(x, y, bottom, matrix)
                        || checkIfVisibleFromDirection(x, y, left, matrix)
            }
        }
    println(highTrees)
    println("# of visible : ${highTrees.size}")

    val scenicScores = matrix
        .mapIndexed { x, line ->
            line.mapIndexed { y, _ ->
                scenicScore(x, y, matrix)
            }
        }

    val maxViewingDistance = scenicScores.maxOf { it.max() }
    println("Max of scenic score : $maxViewingDistance")

}