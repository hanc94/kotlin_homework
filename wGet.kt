import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//Function that connects to URL in url argument and returns server response
fun wGet( url:String): String {
    //Builds URL object
    val url = URL(url)

    //String to return response
    val response: String

    //Creates HTTP connection
    var conn= url.openConnection() as HttpURLConnection

    //Objects to manage response from server
    val inputStream : InputStream = conn.getInputStream()
    val inputStreamReader = InputStreamReader(inputStream)
    val bufferedReader = BufferedReader(inputStreamReader)

    //Reads response from server
    response=bufferedReader.readLine()

    //Returns response
    return response

}

fun main(){
	

}
