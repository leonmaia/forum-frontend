package controllers


import javax.inject._
import models.Reply
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import services.ReplyService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ReplyCreationController @Inject()(showTemplate: views.html.show, replycService: ReplyService)(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  val replyForm = Form(
    mapping(
      "email" -> text,
      "body" -> text,
      "topic_id" -> ignored(Option.empty[Int])
    )(Reply.apply)(Reply.unapply)
  )

  def submit(id: Int) = Action.async { implicit request =>
    val reply = replyForm.bindFromRequest.get

    replycService.save(reply) map { _ =>
      Redirect(routes.ShowController.index(id))
    }
  }
}