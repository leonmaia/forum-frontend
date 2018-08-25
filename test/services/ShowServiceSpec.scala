package services

import models.{PagedResult, Reply, Topic}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class ShowServiceSpec extends PlaySpec with MockitoSugar {

  private val mockedReplyService = mock[ReplyService]
  private val mockedTopicService = mock[TopicService]
  private val service = new ShowService(mockedTopicService, mockedReplyService)

  "ShowService#show" should {
    "return a paged result of replies and topic when on first page" in {

      when(mockedReplyService.list(1, 1))
        .thenReturn(
          Future(
            PagedResult[Reply](List(Reply("lyn@gmail.com", "What is happening?", Option(1))), 1, 1)
          )
        )

      when(mockedTopicService.get(1))
        .thenReturn(
          Future(
            Topic("lyn@gmail.com", "What is happening?", "", Option(1))
          )
        )

      val result = Await.result(service.show(1, 1), 500.millis)
      result._1.entries.length mustBe 1
      result._2.get.email mustBe "lyn@gmail.com"
    }
  }

  "ShowService#show" should {
    "return a paged result of replies and no topic when not on first page" in {
      when(mockedReplyService.list(1, 2))
        .thenReturn(
          Future(
            PagedResult[Reply](List(Reply("lyn@gmail.com", "What is happening?", Option(1))), 1, 1)
          )
        )

      val result = Await.result(service.show(1, 2), 500.millis)
      result._1.entries.length mustBe 1
    }
  }
}
