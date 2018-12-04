package com.streaming.streams

import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, Tweet}
import com.typesafe.config.{Config, ConfigFactory}

object TwClient {

  private val config: Config = ConfigFactory.load()

  private val consumerKey: String = config.getString("twitter.consumer.key")
  private val consumerSecret: String = config.getString("twitter.consumer.secret")

  private val accessKey: String = config.getString("twitter.access.key")
  private val accessSecret: String = config.getString("twitter.access.secret")

  private val consumerToken = ConsumerToken(key = consumerKey, secret = consumerSecret)
  private val accessToken = AccessToken(key = accessKey, secret = accessSecret)

  private val streamingClient = TwitterStreamingClient(consumerToken, accessToken)

  // Must implement using Akka actors
  def track(keyword: String): Unit ={
    streamingClient.filterStatuses(tracks = Seq(keyword)){
      case tweet: Tweet => KProducer.push_tweet_to_topic(keyword, tweet)
    }
  }

}



