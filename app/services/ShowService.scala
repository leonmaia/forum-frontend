package services

import javax.inject.Inject
import models.{PagedResult, Reply, Topic}
import play.api.mvc.{AnyContent, Request}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ShowService @Inject()(topicService: TopicService, replyService: ReplyService) {
  def show(id: Int, request: Request[AnyContent]): Future[(PagedResult[Reply], Option[Topic])] = {
    request.getQueryString("page").fold(1)(_.toInt) match {
      case page if page == 1 =>
        for {
          topic <- topicService.get(id)
          replies <- replyService.list(id, page)
        } yield (replies, Option(topic))
      case page =>
        for {
          replies <- replyService.list(id, page)
        } yield (replies, Option.empty[Topic])
    }
  }
}
