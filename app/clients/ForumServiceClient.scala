package clients

import javax.inject.Inject
import models.{PagedResult, Reply, Topic}
import play.api.libs.ws.WSClient

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import play.api.Logger

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
          Try(response.json.as[PagedResult[Topic]]) match {
            case Success(topics) => topics
            case Failure(f) =>
              val errorMessage = "Error while retrieving topic list"
              Logger.error(errorMessage, f)
              throw new IllegalStateException(errorMessage)
          }
      }
  }

  def saveTopic(topic: Topic): Future[Topic] = {
    ws.url(s"$baseUrl/topics")
      .post(topic) map {
      response =>
        Try(response.json.as[Topic]) match {
          case Success(resultTopic) => resultTopic
          case Failure(f) =>
            val errorMessage = "Error while saving topic"
            Logger.error(errorMessage, f)
            throw new IllegalStateException(errorMessage)
        }
    }
  }

  def getTopic(id: Int): Future[Topic] = {
    ws.url(s"$baseUrl/topics/$id")
      .get() map {
      response =>
        Try(response.json.as[Topic]) match {
          case Success(resultTopic) => resultTopic
          case Failure(f) =>
            val errorMessage = "Error while retrieving topic"
            Logger.error(errorMessage, f)
            throw new IllegalStateException(errorMessage)
        }
    }
  }

  def getRepliesForTopic(topicId: Int, page: Int, limit: Int): Future[PagedResult[Reply]] = {
    ws.url(s"$baseUrl/topics/$topicId/replies")
      .addQueryStringParameters("page" -> s"$page", "limit" -> s"$limit")
      .get() map {
      response =>
        Try(response.json.as[PagedResult[Reply]]) match {
          case Success(entries) => entries
          case Failure(f) =>
            val errorMessage = "Error while retrieving replies for topic"
            Logger.error(errorMessage, f)
            throw new IllegalStateException(errorMessage)
        }
    }
  }

  def saveReply(reply: Reply): Future[Int] = ws.url(s"$baseUrl/replies").post(reply).map {
    response =>
      response.status
  }
}
