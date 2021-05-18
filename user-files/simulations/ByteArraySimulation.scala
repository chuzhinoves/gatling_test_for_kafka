package com.github.mnogu.gatling.kafka.test

import io.gatling.core.Predef._
import org.apache.kafka.clients.producer.ProducerConfig
import scala.concurrent.duration._

import com.github.mnogu.gatling.kafka.Predef._

class ByteArraySimulation extends Simulation {
  var jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
  var jaasCfg = String.format(jaasTemplate, "test", "12345678");
  val kafkaConf = kafka
    .topic("test")
    .properties(
      Map(
        ProducerConfig.ACKS_CONFIG -> "all",
        "security.protocol" -> "SASL_SSL",
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG ->
          "rc1a-sdjhh30tidk5s62u.mdb.yandexcloud.net:9091",
        "sasl.mechanism" -> "SCRAM-SHA-512",
        "sasl.jaas.config"-> jaasCfg,
        "ssl.truststore.location"->"/etc/security/ssl",
        "ssl.truststore.password"->"pass",
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.ByteArraySerializer",
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.ByteArraySerializer"))

  val scn = scenario("Kafka Test")
    .exec(kafka("request").send("foo".getBytes: Array[Byte]))

  setUp(
    scn.inject(atOnceUsers(10)))
    .throttle(jumpToRps(10), holdFor(30.seconds))
    .protocols(kafkaConf)
}
