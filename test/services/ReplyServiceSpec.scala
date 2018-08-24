package services

import clients.ForumServiceClient
import models.{PagedResult, Reply}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class ReplyServiceSpec extends PlaySpec with MockitoSugar {

  private val mockedClient = mock[ForumServiceClient]
  private val service = new ReplyService(mockedClient)

  "ReplyService#getRepliesForTopic" should {
    "return a paged result of reply" in {

      when(mockedClient.getRepliesForTopic(1, 1, 20))
        .thenReturn(
          Future(
            PagedResult[Reply](List(Reply("lyn@gmail.com", "What is happening?", Option(1))), 1, 1)
          )
        )
      val result = Await.result(service.list(1, 1), 500.millis)
      result.entries.length mustBe 1
      result.totalPages mustBe 1
      result.pageNumber mustBe 1
    }
  }

  "ReplyService#get" should {
    "save a reply" in {
      val reply = Reply("lyn@gmail.com", "What is happening?", Option(1))
      when(mockedClient.saveReply(reply))
        .thenReturn(
          Future(201)
        )
      val result = Await.result(service.save(reply), 500.millis)

      result mustBe 201
    }
  }
}
