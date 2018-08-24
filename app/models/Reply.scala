package models

import akka.util.ByteString
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.ws.{BodyWritable, InMemoryBody}

case class Reply(email: String, body: String, topicId: Option[Int])

object Reply {
  import play.api.libs.json._

  implicit val config = JsonConfiguration(SnakeCase)

  implicit val replyReads = Json.reads[Reply]
  implicit val replyWrites = Json.writes[Reply]

  implicit val urlBodyWritable = BodyWritable[Reply]({ reply =>
    val json = Json.toJson(reply)
    val str = ByteString.fromString(Json.stringify(json))
    InMemoryBody(str)
  }, "application/json")
}
