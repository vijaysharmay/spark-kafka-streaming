package com.streaming.streams

import java.util
import java.util.Properties
import java.util.concurrent.TimeUnit

import com.danielasfregola.twitter4s.entities.Tweet
import de.heikoseeberger.akkahttpjson4s.Json4sSupport.ShouldWritePretty.True
import org.apache.kafka.clients.producer._
import org.apache.kafka.clients.admin._

import scala.collection.JavaConverters._

object KProducer{
  val props = new Properties

  props.put("bootstrap.servers", "localhost:9092")
  props.put("client.id", "Producer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  def create_topic_if_not_exists(keyword: String): Unit = {
    val adminClient = AdminClient.create(props)
    val topic = new NewTopic(keyword, 1, 1)
    val existingTopics = adminClient.listTopics().names().get().asScala
    if (!existingTopics.contains(keyword)) {
      println("creating topic ...")
      val result: CreateTopicsResult = adminClient.createTopics(Seq(topic).asJava)
      result.all().get(10, TimeUnit.SECONDS)
      println(adminClient.listTopics().names().get().asScala)
    } else {
      println("topic already exists")
    }
  }

  def push_tweet_to_topic(keyword: String, tweet: Tweet): Unit = {
    val producer = new KafkaProducer[String, String](props)
    val message = new ProducerRecord[String, String](keyword, tweet.text)
    producer.send(message)
    producer.close()
  }
}