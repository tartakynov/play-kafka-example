package controllers

import akka.stream.scaladsl._
import com.google.inject._
import play.api.mvc._
import services.KafkaService

import scala.concurrent.Future


@Singleton
class HomeController @Inject()(kafka: KafkaService) extends InjectedController {

  def index = Action { implicit request =>
    Ok(views.html.index(routes.HomeController.ws().webSocketURL()))
  }

  def ws: WebSocket = WebSocket.acceptOrResult[Any, String] { _ =>
    val source = kafka.source("test")
    val flow = Flow.fromSinkAndSource(Sink.ignore, source)
    Future.successful(Right(flow))
  }

}
