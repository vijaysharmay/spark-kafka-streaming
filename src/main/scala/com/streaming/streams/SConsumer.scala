package com.streaming.streams

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object SConsumer{
    val streamingConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("SparkKafkaStreaming")
    val streamingContext = new StreamingContext(streamingConf, Seconds(1))

    val kafkaParams: Map[String, Object] = Map[String, Object](
        "bootstrap.servers" -> "localhost:9092",
        "key.deserializer" -> classOf[StringDeserializer],
        "value.deserializer" -> classOf[StringDeserializer]
    )

    def consumeStream(topic: String): DStream[(String, String)] = {
        val topics = List(topic)
        val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
            streamingContext,
            PreferConsistent,
            Subscribe[String, String](topics, kafkaParams)
        )

        println("Reached here")

        stream.map(record => {
            println(record.key())
            println(record.value())
            (record.key, record.value)
        })
    }
}