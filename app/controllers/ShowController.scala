package controllers

import javax.inject.Inject
import play.api.mvc.InjectedController
import services.TopicService
import scala.concurrent.ExecutionContext.Implicits.global

class ShowController @Inject()(topicService: TopicService, showTemplate: views.html.show)(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  def index(id: Int) = Action.async { request =>
    request.getQueryString("page").fold(1)(_.toInt) match {
      case page if page == 1 =>
        for {
          topic <- topicService.get(id)
          replies <- topicService.getWithReplies(id, page)
        } yield Ok(showTemplate.render(replies, Option(topic)))
      case page =>
        for {
          replies <- topicService.getWithReplies(id, page)
        } yield Ok(showTemplate.render(replies, Option.empty))
    }
  }
}
