package controllers

import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, InjectedController}
import services.ShowService

import scala.concurrent.ExecutionContext.Implicits.global

class ShowController @Inject()(showService: ShowService)(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  def index(id: Int, page: Int): Action[AnyContent] = Action.async { request =>
    showService.show(id, page) map { result =>
      Ok(views.html.show.render(result._1, result._2))
    }
  }
}
