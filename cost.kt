import kotlin.math.*

fun main(){

	var y = IntArray(5)
	var z = arrayOf("Hola","Mundo","Cruel")

	y[0] = 25

	var w = sin(y[0].toDouble()).toString()+z[0]

	print("Result is: $w")
}
