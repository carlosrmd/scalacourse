package com.synergygb.zordon.gen {
  import spray.routing.Route
  import com.synergygb.zordon.core._
  package object routes {
    trait WeatherRoutes extends com.synergygb.zordon.gen.endpoints.WeatherEndpoints with Routable with spray.routing.Directives with com.synergygb.zordon.common.ZordonDirectives with com.synergygb.zordon.core.CoreDirectives with com.synergygb.zordon.common.data.ZordonJsonSupport {
      implicit def executionContext: scala.concurrent.ExecutionContext
      private def handleGetWeatherCityCityNameInternal(cityName: String): Route = handleGetWeatherCityCityName(cityName)()
      def handleGetWeatherCityCityName(cityName: String)(): Route
      def route = super[WeatherEndpoints].getWeatherCityCityName(handleGetWeatherCityCityNameInternal _)
    }
    trait ApplicationRoutesConsolidated extends WeatherRoutes {
      override def route = super[WeatherRoutes].route
    }
    class ApplicationRoutesFragmented(val weatherRoutes: WeatherRoutes) extends Routable with spray.routing.Directives {
      def route = weatherRoutes.route
    }
  }
}