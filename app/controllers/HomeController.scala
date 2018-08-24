package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

@Singleton
class HomeController @Inject()(indexTemplate: views.html.index)(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  def index(page: Int) = Action.async { _ =>
    Future(Ok(indexTemplate()))
  }
}