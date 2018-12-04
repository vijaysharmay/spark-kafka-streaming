package com.streaming.streams

import java.util.Properties

import com.danielasfregola.twitter4s.entities.Tweet
import org.apache.kafka.clients.producer._


object KProducer{
  val props = new Properties

  props.put("bootstrap.servers", "localhost:9192")
  props.put("client.id", "Producer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  def push_tweet_to_topic(keyword: String, tweet: Tweet): Unit = {
    val producer = new KafkaProducer[String, String](props)
    val message = new ProducerRecord[String, String](keyword, tweet.text)
    producer.send(message)
    producer.close()
  }
}