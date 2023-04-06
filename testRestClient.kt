/*
You have to create RESTClient class which has to behave in a such way it can be used as described in main.
*/
import com.beust.klaxon.JsonArray
import kotlin.random.Random
import com.beust.klaxon.Klaxon
import com.beust.klaxon.json
import java.io.StringReader
fun main() {

    //Creates REST client object, argument is API URL 
    val restClient = RESTClient("http://192.168.1.70:8080")

    //User API key
    val auth= "5e592d52351ecc549689d882"

    //Registers new user
    restClient.httpPostAsync("/register/user","user=john&password=doe")
    println("You registered a new user ${restClient.wait()}")


    //Gets list of devices
    restClient.httpGetAsync("/devices?auth=$auth")

    //Parses JSON array
    val response = restClient.wait()

    /******        By using Klaxon, parse JSON object to obtain devices array ************/   
    /******	   this has to be coded by you 				      ************/
    val devices = Klaxon().parseArray<String>(response)

    val device = devices?.get(0)

    println("You have registered ${devices?.size} devices")

    //Registers new device
    restClient.httpPostAsync("/register/device?auth=$auth","1")
    println("You registered a new device ${restClient.wait()}")


    //Writes device value
    restClient.httpPutAsync("/device/${device}?auth=$auth", "value=${Random.nextDouble()}")
    println(restClient.wait())

    //Reads device value
    restClient.httpGetAsync("/device/${device}/value?auth=$auth")
    println("Value of device $device is ${restClient.wait()}")

}
