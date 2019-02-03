package services

import akka.stream.scaladsl._
import com.google.inject._
import play.api.Configuration

import scala.util.{Success, Try}

@ImplementedBy(classOf[KafkaServiceImpl])
trait KafkaService {
  def source(topic: String): Try[Source[String, _]]
}

@Singleton
class KafkaServiceImpl @Inject()(config: Configuration) extends KafkaService {
  val url: String = config.get[String]("kafka.url")

  override def source(topic: String): Try[Source[String, _]] = {
    Success(Source.empty[String])
  }
}
