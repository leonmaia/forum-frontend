package models

import play.api.libs.json.JsonNaming.SnakeCase

case class PagedResult[A](entries: Seq[A],
                          pageNumber: Int,
                          totalPages: Int)

object PagedResult {
  import play.api.libs.json._

  implicit val config = JsonConfiguration(SnakeCase)

  implicit def pagedResultReads[A](implicit fmt: Reads[A]) = Json.reads[PagedResult[A]]

  implicit def pagedResultWrites[A](implicit fmt: Writes[A]) = Json.writes[PagedResult[A]]
}
