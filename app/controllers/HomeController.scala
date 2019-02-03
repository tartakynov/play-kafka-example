package controllers

import akka.stream.scaladsl._
import com.google.inject._
import play.api.mvc._
import services.KafkaService

import scala.concurrent.Future
import scala.util.{Failure, Success}


@Singleton
class HomeController @Inject()(kafka: KafkaService) extends InjectedController {

  def index = Action { implicit request =>
    Ok(views.html.index(routes.HomeController.ws().webSocketURL()))
  }

  def ws: WebSocket = WebSocket.acceptOrResult[Any, String] { _ =>
    kafka.source("RandomNumbers") match {
      case Failure(e) =>
        Future.successful(Left(InternalServerError(s"Cannot connect to Kafka: ${e.getMessage}")))
      case Success(source) =>
        val flow = Flow.fromSinkAndSource(Sink.ignore, source)
        Future.successful(Right(flow))
    }
  }

}