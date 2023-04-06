package com.example

import com.google.gson.Gson
import com.typesafe.config.ConfigException
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.http.content.MultiPartData
import java.lang.Double.NaN
import java.lang.NullPointerException
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

data class devID(var ID:Int,var digital:String,var analog:Double)

var i = 0
var flag = 0
lateinit var Devid:devID
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        post ("/regdevice"){
            Devid = devID(i,"LOW",0.0)
            call.respond("devID=$i")
            i++;
        }
        put("/device/{devID}"){

            Devid.analog = call.request.queryParameters["analog"]!!.toDouble()
            Devid.digital = call.request.queryParameters["digital"]!!
            flag = if (Devid.analog.isNaN() && Devid.digital.isEmpty() ){0} else{1}
            if(flag==1)call.respondText("OK")
            else{ call.respondText("ERROR")}
        }
        get("/device/{devID}") {
            val array = arrayOf(Devid.digital , Devid.analog)
            val myObj = Gson().toJson(array)
            flag = if (Devid.analog.isNaN() && Devid.digital.isEmpty() ){0} else{1}
            if(flag==1)call.respondText(myObj)
            else{ call.respondText("ERROR")}

        }
        get("/device/{devID}/analog") {
            val array = arrayOf(Devid.analog)
            val myObj = Gson().toJson(array)
            flag = if (Devid.analog.isNaN()){0} else{1}
            if(flag==1)call.respondText(myObj)
            else{ call.respondText("ERROR")}
        }
        get("/device/{devID}/digital") {
            val array = arrayOf(Devid.digital)
            val myObj = Gson().toJson(array)
            flag = if (Devid.digital.isEmpty()){0} else{1}
            if(flag==1)call.respondText(myObj)
            else{ call.respondText("ERROR")}
        }
    }
}

