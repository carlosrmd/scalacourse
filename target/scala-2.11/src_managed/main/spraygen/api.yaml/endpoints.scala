package com.synergygb.zordon.gen {
  package object endpoints {
    import spray.routing.Directives._
    import scala.concurrent.ExecutionContext.Implicits.global
    import spray.json.DefaultJsonProtocol._
    import com.synergygb.zordon.common.data.directives.DataDirectives._
    import com.synergygb.zordon.common.data.ZordonJsonSupport._
    import com.synergygb.zordon.core.CoreDirectives._
    import com.synergygb.zordon.gen.models._
    trait WeatherEndpoints {
      def getWeatherCityCityName = get & path("weather" / "city" / Segment)
    }
  }
}