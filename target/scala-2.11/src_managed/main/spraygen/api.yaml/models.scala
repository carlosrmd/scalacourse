import spray.json.DefaultJsonProtocol._
package com.synergygb.zordon.gen {
  import spray.json.DefaultJsonProtocol._
  import com.synergygb.zordon.common.data.directives.DataDirectives._
  import com.synergygb.zordon.common.data.ZordonJsonSupport._
  import com.synergygb.zordon.core.CoreDirectives._
  package object models {
    implicit val WeatherResponseFormat = jsonFormat3(WeatherResponse)
    case class WeatherResponse(cityName: Option[String] = None, temperature: Option[Double] = None, pressure: Option[Double] = None)
  }
}