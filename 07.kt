import java.io.File

abstract class Node(val name: String, val parent: Node?){
    abstract fun getPath(): String
    abstract fun getSize(): Int
}
class Directory(name: String, parent: Node? = null, val contents: MutableList<Node> = mutableListOf()): Node(name, parent){

    fun getFolders(): List<Directory>{
        return listOf(this).union( contents.filterIsInstance<Directory>().toList().flatMap { it.getFolders() }).toList()
    }

    override fun getPath(): String {
        return (parent?.getPath() ?: "") + name + "/"
    }

    override fun getSize(): Int {
        return contents.sumOf { it.getSize() }
    }

    fun addFile(name: String, size: Int){
        contents.add(File(name, size, this))
    }

    fun addFolder(name: String){
        contents.add(Directory(name, this))
    }
}
class File(name: String, private val size: Int, parent: Node?): Node(name, parent) {

    override fun getPath(): String {
        return parent?.getPath()+name
    }

    override fun getSize(): Int {
        return size
    }
}

fun main() {
    val lines: List<String> = File("07-input").readLines()

    val root = Directory("")
    println(root.getPath())
    var pwd: Directory = root
    lines.forEach { it ->
        if (it.first() == '$'){
            if(it.substring(2..3) == "cd"){
                val dest = it.split(' ')[2]
                pwd = if(dest == ".."){
                    (pwd.parent as Directory?)!!
                }else{
                    pwd.contents.single { it.name == dest } as Directory
                }
            }
        }else{
            val content = it.split(" ")
            if(content[0] == "dir"){
                pwd.addFolder(content[1])
            }else{
                pwd.addFile(content[1], content[0].toInt())
            }
        }
    }

    val spaceToFree = 30000000-(70000000-root.getSize())
    val sizes = root.getFolders().map { folder -> folder.getSize() }
    val filtered = sizes.filter { it <= 100000 }
    val sumFiltered = filtered.sum()
    println("Sum folder <= 100000: $sumFiltered")

    val minToDeleteSize = sizes.filter { it >= spaceToFree }.min()
    println(sizes.filter { it >= spaceToFree })
    println("Min to delete : $minToDeleteSize")



}