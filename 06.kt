import java.io.File
import java.util.Random



fun main() {
    val lines: List<String> = File("06-input").readLines()
    val line = lines.first()
    var hasPacketStarted = false
    var startOfPacket=3
    while(!hasPacketStarted){
        startOfPacket++

        if(line.substring(startOfPacket-4, startOfPacket).toSet().size == 4){
            hasPacketStarted = true
        }
    }

    println("Start of packet : $startOfPacket")

    var hasMessageStarted = false
    var startOfMessage=startOfPacket+13
    while(!hasMessageStarted){
        startOfMessage++

        if(line.substring(startOfMessage-14, startOfMessage).toSet().size == 14){
            hasMessageStarted = true
        }
    }
    println("Start of message : $startOfMessage")
}