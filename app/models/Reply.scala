package models

import play.api.libs.json.Json

case class Reply(email: String, body: String)

object Reply {
  implicit val replyReads = Json.reads[Reply]
}
