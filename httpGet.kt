import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

fun httpGet(url: String, callback: (String)->Unit){


    GlobalScope.launch{
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

        callback( bufferedReader.readLine())
    }

}


