package org.scalacourse

import akka.event.Logging
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

import spray.http.StatusCodes._

import spray.httpx.unmarshalling._
import spray.httpx.marshalling._
import spray.httpx.marshalling.Marshaller
import spray.http._
import HttpCharsets._
import MediaTypes._
import spray.util._

import java.sql.{Connection,DriverManager}


/**
  * Created by saul on 2/10/16.
  */

          

trait MainService extends HttpService with SprayJsonSupport with DataModel {

  def route = {
    path("admision") {
      get{
        var response_list = List[Elem2]()
        val myjson = scala.io.Source.fromURL("http://jsonplaceholder.typicode.com/albums").mkString
        val body = HttpEntity(contentType = ContentType(`application/json`, `UTF-8`), string = myjson)
        val p = body.as[RootCollection] match {
          case Left(myElems) => println("algo salio mal")
          case Right(myElems) => {
            def sha1it(m: String) = 
              java.security.MessageDigest.getInstance("SHA-1").digest(m.getBytes("UTF-8")).map("%02x".format(_)).mkString

            def shaMyElemList(l: List[Elem]): List[Elem2] = l match {
              case Nil => Nil
              case (a::as) => Elem2(a.userId,a.id,a.title,sha1it(a.title))::shaMyElemList(as)
            }
            response_list = shaMyElemList(myElems.items)
          }
        }
        complete(AdmisionGetResponse(response_list))
      } ~ 
      post {  
        entity(as[DatosRequest]) { datosrequest =>
          println(s"${datosrequest.nombre} - ${datosrequest.apellido} - ${datosrequest.correo}")
          if(dbit(datosrequest.nombre,datosrequest.apellido,datosrequest.correo)==1)
            complete(s"Persona ${datosrequest.nombre} ${datosrequest.apellido} agregada satisfactoramente")
          else
            complete("Algo salio mal.")
        }
      }

    }


  }

  def loggedRoute = (logRequest("request", Logging.InfoLevel) & logResponse("response", Logging.InfoLevel)) {
    route
  }

  def dbit(nombre: String,apellido: String,correo: String): Int = {
    val itsASelect = true
    val insert_query = s"""INSERT INTO persona (nombre, apellido, correo) VALUES ("${nombre}","${apellido}","${correo}")"""
    val select_query = "SELECT nombre, apellido, correo FROM persona"
    val url = "jdbc:mysql://db4free.net:3306/prueba_admision"
    val driver = "com.mysql.jdbc.Driver"
    val username = "sql9144354"
    val password = "7TW2ccREF1"
    var status = 0
    try {
        Class.forName(driver)
        var connection = DriverManager.getConnection(url, username, password)
        val statement = connection.createStatement
        if (itsASelect){
          val rs = statement.executeQuery(select_query)
          println("Done.")
          while (rs.next) {
              println(s"${rs.getString("nombre")} | ${rs.getString("apellido")} | ${rs.getString("correo")}")
          }
        } else {
          val rs = statement.executeUpdate(insert_query)
        }
        connection.close
        status = 1
    } catch {
        case e: Exception => println(e)
        status = -1
    }
    
    return status
  }

}

