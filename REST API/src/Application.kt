package com.example

import com.mongodb.MongoClient
import com.typesafe.config.ConfigException
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import org.litote.kmongo.*
import kotlin.random.Random

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
data class Device(var analog:Double=0.0,var digital:String=" ", var _id:String?=null)

lateinit var parameters:Parameters;
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }
    routing {

        //Registers new device
        post("/regdevice"){


            var client = KMongo.createClient()
            var database = client.getDatabase("imad-server")
            var colDevice = database.getCollection<Device>()
            //Creates Device object and inserts it into database
            val insertDevice=Device(0.0, " ")
            colDevice.insertOne(insertDevice)

            //Closes connection to database
            client.close()
            //Sends id of registered device to client
            call.respondText(insertDevice._id!!, contentType = ContentType.Text.Plain)
        }

        //Writes digital and/or analog value to IoT device
        put("/device/{devID}"){
            //Reads parameters from PUT request

            try {
                parameters= call.receiveParameters()
            }catch (e: Throwable){
            call.respondText("ERROR");

        }

            val deviceID = call.parameters["devID"]
            val digital = parameters["digital"].toString()
            var analog= parameters["analog"].toString()


            //Objects to handle MongoDB
            var client = KMongo.createClient()
            var database = client.getDatabase("imad-server")
            var colDevice = database.getCollection<Device>()

            //Finds requested device
            val curDevice = colDevice.findOne(Device::_id eq deviceID)
            //If requested device does not exist
            if (curDevice == null) {

                //Closes Mongo DB client
                client.close()

                //Sends response to client
                call.respondText("ERROR", contentType = ContentType.Text.Plain)
            }
            else {
                //If device exists, updates value in auxiliary Device object
                var flag = 0
                if(analog != "null")
                curDevice.analog = analog.toDouble()
                else {
                    flag++
                }
                if (digital != "null")
                curDevice.digital = digital;
                else if (flag == 1){
                    call.respondText("ERROR", contentType = ContentType.Text.Plain)
                }


                //Updates device in database
                colDevice.updateOneById(deviceID!!,curDevice)

                //Closes Mongo DB client
                client.close()
                //Sends "OK" as response to client
                call.respondText("OK", contentType = ContentType.Text.Plain)

            }
            println("${call.request.httpMethod.value} value of $deviceID analog is ${curDevice?.analog.toString()} and value of $deviceID digital is ${curDevice?.digital.toString()}")
         }

        //Reads analog and digital values from IoT device
        get("/device/{devID}") {

                val deviceID = call.parameters["devID"]
                //Objects to handle MongoDB
                var client = KMongo.createClient()
                var database = client.getDatabase("imad-server")
                var colDevice = database.getCollection<Device>()

                //Finds requested device
                val curDevice = colDevice.findOne(Device::_id eq deviceID)

                //Closes connection to database
                client.close()

                //If requested device does not exist
                if (curDevice == null) {

                    //Sends response to client
                    call.respondText("ERROR", contentType = ContentType.Text.Plain)
                } else {
                    //If device exists, dends value of device to user
                    call.respond(mapOf("analog" to curDevice.analog, "digital" to curDevice.digital))
                }
        }

        //Reads analog value from IoT device
        get("/device/{devID}/analog") {

                    val deviceID = call.parameters["devID"]
                    //Objects to handle MongoDB
                    var client = KMongo.createClient()
                    var database = client.getDatabase("imad-server")
                    var colDevice = database.getCollection<Device>()

                    //Finds requested device
                    val curDevice = colDevice.findOne(Device::_id eq deviceID)

                    //Closes connection to database
                    client.close()

                    //If requested device does not exist
                    if (curDevice == null) {

                        //Sends response to client
                        call.respondText("ERROR", contentType = ContentType.Text.Plain)


                    } else {
                        //If device exists, dends value of device to user
                        call.respond(mapOf("analog" to curDevice.analog))

                    }
        }
        // Reads digital value from IoT device
        get("/device/{devID}/digital") {
                    val deviceID = call.parameters["devID"]
                    //Objects to handle MongoDB
                    var client = KMongo.createClient()
                    var database = client.getDatabase("imad-server")
                    var colDevice = database.getCollection<Device>()
                    //Finds requested device
                    val curDevice = colDevice.findOne(Device::_id eq deviceID)
                    //Closes connection to database
                    client.close()
                    //If requested device does not exist
                    if (curDevice == null) {
                        //Sends response to client
                        call.respondText("ERROR", contentType = ContentType.Text.Plain)
                    } else {
                        //If device exists, dends value of device to user
                        call.respond(mapOf("digital" to curDevice.digital))
                    }
            }
        }
}
