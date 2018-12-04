# Real Time Streaming with Kafka & Spark

This repo contains code for the session on "Spark Streaming with Kafka" for HydPy meetup group.

This repo aims to be a reference for both Scala & Python developers.

Built using:

- Python 3.7 / Scala 2.12
- Kafka 2.1.0
- Spark 2.4.0
- D3 4.x
- React 16.6+

The app allows a user to track multiple hashtags and observe for each hashtag, 
whether tweets are positive or negative.

### Approach/Flow

1. User enters a hashtag to track in the UI
2. Using the twitter streaming API, we create a topic for the hashtag if it doesn't exists and publish to it
3. Using kafka streams API we send the stream to Spark for sentiment analysis/online training
4. We send back the sentiment scores and use akka streams to send it to the UI using websockets
5. User should see the tweets with sentiment scores


