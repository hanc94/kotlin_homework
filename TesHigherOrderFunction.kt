import kotlin.random.Random

fun main() {

    val n: Int = 1048576//1.shl(20)
    val myList = List<Double>(n ,{ item ->
        Random.nextDouble()
    })

    println(myList[n-1])

    var squaredList= myList.map { item ->
        item*item
    }

    println(squaredList[n-1])

}

