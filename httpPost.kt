import java.io.*
import java.net.HttpURLConnection
import java.net.URL

//Function to perform POST requests to server
fun httpPost(url: String, data: String): String {

    //Builds URL object
    val url = URL(url)

    //String to return response
    val response: String
    //Creates HTTP connection
    var conn = url.openConnection() as HttpURLConnection

    //Parameters of connection
    conn.readTimeout = 10000
    conn.connectTimeout = 15000
    conn.requestMethod = "POST"
    conn.doInput = true
    conn.doOutput = true

    //Objects to manage data to server
    val outputStream: OutputStream = conn.getOutputStream()
    val outputStreamWriter = OutputStreamWriter(outputStream)
    val bufferedWriter = BufferedWriter(outputStreamWriter)

    //Writes POST data to server
    bufferedWriter.write(data)
    bufferedWriter.flush()

    //Objects to manage response from server
    val inputStream: InputStream = conn.getInputStream()
    val inputStreamReader = InputStreamReader(inputStream)
    val bufferedReader = BufferedReader(inputStreamReader)


    //Returns server response
    return bufferedReader.readLine()
}




