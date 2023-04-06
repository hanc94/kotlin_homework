import kotlinx.coroutines.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class RESTClient ( private var u:String){
    var url= u;
    var deferred: Deferred<String> = TODO()
    var user: String = ""
    fun httpPostAsync(url: String, data: String){
        deferred = GlobalScope.async {

            //Builds URL object
            val url = URL(url)
            user = data;
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
            conn.disconnect()
            bufferedReader.readLine()
        }
    }

    fun httpGetAsync(url: String){
        deferred= GlobalScope.async {

                //Builds URL object
                val url = URL(url)
                //user =data;
                //String to return response
                val response: String
                //Creates HTTP connection
                var conn = url.openConnection() as HttpURLConnection

                //Parameters of connection
                conn.readTimeout = 10000
                conn.connectTimeout = 15000
                conn.requestMethod = "GET"
                conn.doInput = true
                conn.doOutput = true

                //Objects to manage data to server
                val outputStream: OutputStream = conn.getOutputStream()
                val outputStreamWriter = OutputStreamWriter(outputStream)
                val bufferedWriter = BufferedWriter(outputStreamWriter)

                //Writes POST data to server
                //bufferedWriter.write(data)
                //bufferedWriter.flush()

                //Objects to manage response from server
                val inputStream: InputStream = conn.getInputStream()
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                conn.disconnect()
                bufferedReader.readLine()
            }
        }
    fun httpPutAsync(url: String, data: String): Deferred<String>{

        return GlobalScope.async {

            //Builds URL object
            val url = URL(url)

            //String to return response
            val response: String
            //Creates HTTP connection
            var conn = url.openConnection() as HttpURLConnection

            //Parameters of connection
            conn.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "PUT"
            conn.doInput = true
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

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
            bufferedReader.readLine()

        }

    }
    fun wait(): String{

        return deferred.onAwait.toString()

    }
}


