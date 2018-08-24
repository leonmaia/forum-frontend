package clients

import javax.inject.Inject
import models.{PagedResult, Reply, Topic}
import play.api.libs.ws.WSClient

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

case class ForumServiceClient @Inject()(ws: WSClient)(implicit ec: ExecutionContext) {
  val host: String = Option(System.getenv("FORUM_SERVICE_HOST")).getOrElse("localhost")
  val port: String = Option(System.getenv("FORUM_SERVICE_PORT")).getOrElse("7719")

  val baseUrl: String = s"http://$host:$port"

  def listTopics(page: Int, limit: Int): Future[PagedResult[Topic]] = {
    ws.url(s"$baseUrl/topics")
      .addQueryStringParameters("page" -> s"$page", "limit" -> s"$limit")
      .withRequestTimeout(500.millis)
      .get()
      .map {
        response =>
          response.json.as[PagedResult[Topic]]
      }
  }

  def saveTopic(topic: Topic): Future[Topic] = {
    ws.url(s"$baseUrl/topics")
      .post(topic) map {
      response =>
        response.json.as[Topic]
    }
  }

  def getTopic(id: Int): Future[Topic] = {
    ws.url(s"$baseUrl/topics/$id")
      .get() map {
      response =>
        response.json.as[Topic]
    }
  }

  def getRepliesForTopic(topicId: Int, page: Int, limit: Int): Future[PagedResult[Reply]] = {
    ws.url(s"$baseUrl/topics/$topicId/replies")
      .addQueryStringParameters("page" -> s"$page", "limit" -> s"$limit")
      .get() map {
      response =>
        response.json.as[PagedResult[Reply]]
    }
  }

  def saveReply(reply: Reply): Future[Int] = ws.url(s"$baseUrl/replies").post(reply).map {
    response =>
      response.status
  }
}
