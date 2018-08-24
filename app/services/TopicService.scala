package services

import clients.ForumServiceClient
import javax.inject.{Inject, Singleton}
import models.{PagedResult, Topic}

import scala.concurrent.Future

@Singleton
class TopicService @Inject()(client: ForumServiceClient) {
  def get(id: Int): Future[Topic] = client.getTopic(id)

  def list(page: Int, limit: Int = 20): Future[PagedResult[Topic]] = client.listTopics(page, limit)

  def save(topic: Topic): Future[Topic] = client.saveTopic(topic)
}

