


fun main(){

    httpGet("http://postman-echo.com/get?foo1=bar1&foo2=bar2"){response ->

        println(response)

    }

    Thread.sleep(1000)



}