package controllers

import javax.inject._
import play.api.mvc._
import services.TopicService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(indexTemplate: views.html.index, topicService: TopicService)(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  def index(page: Int) = Action.async { _ =>
    topicService.list(page) map { topics =>
      Ok(indexTemplate.render(topics))
    }
  }
}