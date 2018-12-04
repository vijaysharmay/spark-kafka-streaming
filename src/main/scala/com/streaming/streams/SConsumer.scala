package com.streaming.streams

import org.apache.spark.sql.SparkSession

object SConsumer{

  val spark: SparkSession = SparkSession
    .builder
    .appName("SentimentAnalysis")
    .getOrCreate()

  import spark.implicits._

  def process(topic: String): Unit = {
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9192")
      .option("subscribe", topic)
      .load()

    df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]


  }

}