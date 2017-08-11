/* Copyright (C) 2016 Synergy-GB LLC.
 * All rights reserved.
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 */
package org.scalacourse

import spray.json.DefaultJsonProtocol

import spray.json._

/**
  * Created by saul on 2/10/16.
  */
trait DataModel extends DefaultJsonProtocol {

  implicit val itemFormat = jsonFormat2(Item)

  case class Item(name : String, value : Int)

  implicit val myRequestFormat = jsonFormat3(MyRequest)

  case class MyRequest(itemId : Int, name : String, description : Option[String])

  implicit  val myResponseFormat = jsonFormat2(MyResponse)

  case class MyResponse(message : String, items : Seq[Item])

  implicit val ElemFormat = jsonFormat3(Elem)

  case class Elem(userId: Int, id: Int, title: String)


  implicit object RootCollectionFormat extends RootJsonReader[RootCollection] {
  	def read(value: JsValue) = RootCollection(value.convertTo[List[Elem]])
  }

  case class RootCollection(items: List[Elem])

  implicit val Elem2Format = jsonFormat4(Elem2)

  case class Elem2(userId: Int, id: Int, title: String, hash: String)

  //implicit val AdmisionGetResponseFormat1 = jsonFormat1(AdmisionGetResponse)

  implicit object AdmisionGetResponseFormat extends RootJsonFormat[AdmisionGetResponse] {
  	def read(value: JsValue) = AdmisionGetResponse(value.convertTo[List[Elem2]])
  	def write(f: AdmisionGetResponse) = {
  		JsArray(f.items.map(_.toJson).toVector)
  	}
  }

  case class AdmisionGetResponse(items: List[Elem2])

  implicit val DatosRequestFormat = jsonFormat3(DatosRequest)

  case class DatosRequest(nombre: String, apellido: String, correo: String)


}
