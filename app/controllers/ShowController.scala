package controllers

import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, InjectedController}
import services.ShowService

import scala.concurrent.ExecutionContext.Implicits.global

class ShowController @Inject()(showService: ShowService, showTemplate: views.html.show)(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  def index(id: Int): Action[AnyContent] = Action.async { request =>
    showService.show(id, request) map { result =>
      Ok(showTemplate.render(result._1, result._2))
    }
  }
}
