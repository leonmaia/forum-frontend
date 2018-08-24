package services

import javax.inject.Inject
import models.{PagedResult, Reply, Topic}
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TopicService @Inject()(ws: WSClient) {
  private val host: String = Option(System.getenv("FORUM_SERVICE_HOST")).getOrElse("localhost")
  private val port: String = Option(System.getenv("FORUM_SERVICE_PORT")).getOrElse("7719")

  def getWithReplies(id: Int, page: Int, limit: Int = 20): Future[PagedResult[Reply]] = {
    println(host)
    println(port)
    ws.url(s"http://$host:$port/topics/$id/replies?page=$page&limit=$limit").get().map {
      response =>
        response.json.as[PagedResult[Reply]]
    }
  }

  def get(id: Int): Future[Topic] = {
    println(host)
    println(port)
    ws.url(s"http://$host:$port/topics/$id").get().map {
      response =>
        response.json.as[Topic]
    }
  }

  def list(page: Int, limit: Int = 20): Future[PagedResult[Topic]] = {
    println(host)
    println(port)
    ws.url(s"http://$host:$port/topics?page=$page&limit=$limit").get().map {
      response =>
        response.json.as[PagedResult[Topic]]
    }
  }
}

