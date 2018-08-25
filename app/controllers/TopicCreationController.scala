package controllers

import javax.inject._
import models.Topic
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import services.TopicService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class TopicCreationController @Inject()(topicService: TopicService)(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  val topicForm = Form(
    mapping(
      "email" -> text,
      "title" -> text,
      "body" -> text,
      "id" -> ignored(Option.empty[Int])
    )(Topic.apply)(Topic.unapply)
  )

  def index = Action.async { _ =>
    Future(Ok(views.html.topic()))
  }

  def submit = Action.async { implicit request =>
    val form = topicForm.bindFromRequest.get
    topicService.save(form) map { topic =>
      Redirect(routes.ShowController.index(topic.id.get))
    }
  }
}