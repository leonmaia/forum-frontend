package services

import javax.inject.Inject
import models.{PagedResult, Reply, Topic}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ShowService @Inject()(topicService: TopicService, replyService: ReplyService) {
  def show(id: Int, page: Int): Future[(PagedResult[Reply], Option[Topic])] = {
    page match {
      case p if p == 1 =>
        for {
          topic <- topicService.get(id)
          replies <- replyService.list(id, page)
        } yield (replies, Option(topic))
      case p =>
        for {
          replies <- replyService.list(id, page)
        } yield (replies, Option.empty[Topic])
    }
  }
}
