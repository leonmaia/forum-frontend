package models

import play.api.libs.json._

import scala.util.Random

case class Topic(email: String, title: String, body: String, id: Int)

object Topic {
  implicit val topicReads = Json.reads[Topic]
}
