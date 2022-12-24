import java.awt.Point
import java.io.File

data class Motion(val direction: Point, val times: Int)

fun Point.minus(other: Point): Point {
    return Point(this.x - other.x, this.y - other.y)
}

fun Point.plus(other: Point): Point {
    return Point(this.x + other.x, this.y + other.y)
}

fun Point.moveTo(next: Point): Point = when (next.minus(this)) {
    Point(-1, 2), Point(-2, 1), Point(-2, 2) -> Point(-1, 1)
    Point(0, 2) -> Point(0, 1)
    Point(1, 2), Point(2, 1), Point(2, 2) -> Point(1, 1)
    Point(2, 0) -> Point(1, 0)
    Point(2, -1), Point(1, -2), Point(2, -2)-> Point(1, -1)
    Point(0, -2) -> Point(0, -1)
    Point(-2, -1), Point(-1, -2), Point(-2, -2) -> Point(-1, -1)
    Point(-2, 0) -> Point(-1, 0)
    else -> Point(0,0 )
}

class KnottedBridge(private val knotsCount: Int = 2) {
    private var knots: MutableList<Point> = MutableList(knotsCount) { Point() }
    val knotsHistory: List<MutableSet<Point>> = List(knotsCount) { mutableSetOf(Point()) }

    fun move(motion: Motion) {
        (1..motion.times).forEach { _ ->
            // Move the head
            knots[0] = knots[0].plus(motion.direction)
            knotsHistory[0].add(knots[0])
            // For any subsequent knot, move it regarding its predecessor's positions
            (1 until knotsCount).forEach { k ->
                val move = knots[k].moveTo(knots[k - 1])
                // Move it if necessary towards its predecesoor
                knots[k] = knots[k].plus(move)
                // add the new knot to the history
                knotsHistory[k].add(knots[k])
            }
        }
    }
}

fun main() {
    val lines: List<String> = File("09-input").readLines()
    val motions = lines.map { line ->
        val direction = line.split(' ')[0]
        val times = line.split(' ')[1].toInt()
        val result = when (direction) {
            "U" -> Motion(Point(0, 1), times)
            "D" -> Motion(Point(0, -1), times)
            "L" -> Motion(Point(-1, 0), times)
            else -> Motion(Point(1, 0), times)
        }
        result
    }

    val bridge = KnottedBridge()
    for (motion in motions) {
        bridge.move(motion)
    }
    println("Number of knots the tail went on a rope with two knots: ${bridge.knotsHistory.last().size}")

    val knottedBridge = KnottedBridge(10)
    for (motion in motions) {
        knottedBridge.move(motion)
    }
    println("Number of knots the tail went on a rope with 10 knots: ${knottedBridge.knotsHistory.last().size}")


}