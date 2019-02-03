package services

import java.nio.charset.StandardCharsets

import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.scaladsl._
import com.google.inject._
import com.typesafe.config.Config
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}
import play.api.Configuration

@ImplementedBy(classOf[KafkaServiceImpl])
trait KafkaService {
  def source(topic: String): Source[String, _]
}

@Singleton
class KafkaServiceImpl @Inject()(config: Configuration) extends KafkaService {
  val url: String = config.get[String]("kafka.url")

  val consumerGroup: String = config.get[String]("kafka.consumer-group")

  val kafkaConfig: Config = config.underlying.getConfig("akka.kafka.consumer")

  private lazy val consumerSettings = {
    ConsumerSettings(kafkaConfig, new StringDeserializer, new ByteArrayDeserializer)
      .withBootstrapServers(url)
      .withGroupId(consumerGroup)
  }

  override def source(topic: String): Source[String, _] = {
    val subscriptions = Subscriptions.topics(topic)
    Consumer.plainSource(consumerSettings, subscriptions).map(r => new String(r.value(), StandardCharsets.UTF_8))
  }
}
