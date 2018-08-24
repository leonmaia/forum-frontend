package services

import clients.ForumServiceClient
import javax.inject.{Inject, Singleton}
import models.{PagedResult, Reply}
import play.api.libs.ws.WSResponse

import scala.concurrent.Future

@Singleton
class ReplyService @Inject()(client: ForumServiceClient) {
  def save(reply: Reply): Future[Int] = client.saveReply(reply)

  def list(topicId: Int, page: Int, limit: Int = 20): Future[PagedResult[Reply]] = client.getRepliesForTopic(topicId, page, limit)
}
