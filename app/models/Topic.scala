package models

import akka.util.ByteString
import play.api.libs.json._
import play.api.libs.ws.{BodyWritable, InMemoryBody}

case class Topic(email: String, title: String, body: String, id: Option[Int])


object Topic {
  implicit val topicReads = Json.reads[Topic]
  implicit val topicWrites = Json.writes[Topic]

  implicit val urlBodyWritable = BodyWritable[Topic]({ topic =>
    val json = Json.toJson(topic)
    val str = ByteString.fromString(Json.stringify(json))
    InMemoryBody(str)
  }, "application/json")
}
