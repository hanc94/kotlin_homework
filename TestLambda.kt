


fun main() {

    val value = 0.37;

    val myFun1 = fun(x: Double): Double { return x * x }

    val myFun2 = { x: Double -> x * x }


    println(myFun1(value) + myFun2(value))

}

