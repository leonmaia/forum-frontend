package services

import clients.ForumServiceClient
import models.{PagedResult, Topic}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class TopicServiceSpec extends PlaySpec with MockitoSugar {

  private val mockedClient = mock[ForumServiceClient]
  private val service = new TopicService(mockedClient)

  "TopicService#list" should {
    "return a paged result of topic" in {

      when(mockedClient.listTopics(1, 20))
        .thenReturn(
          Future(
            PagedResult[Topic](List(Topic("lyn@gmail.com", "What is happening?", "", Option(1))), 1, 1)
          )
        )
      val result = Await.result(service.list(1, 20), 500.millis)
      result.entries.length mustBe 1
      result.totalPages mustBe 1
      result.pageNumber mustBe 1
    }
  }

  "TopicService#get" should {
    "return a topic" in {
      val topic = Topic("lyn@gmail.com", "What is happening?", "", Option(1))
      when(mockedClient.getTopic(topic.id.get))
        .thenReturn(
          Future(
            topic
          )
        )
      val result = Await.result(service.get(1), 500.millis)

      result.id.get mustBe topic.id.get
      result.email mustBe topic.email
      result.body mustBe topic.body
    }
  }

  "TopicService#save" should {
    "save a topic" in {
      val topic = Topic("lyn@gmail.com", "What is happening?", "", Option.empty)
      when(mockedClient.saveTopic(topic))
        .thenReturn(
          Future(
            topic.copy(id = Option(1))
          )
        )
      val result = Await.result(service.save(topic), 500.millis)

      result.id.get mustBe 1
      result.email mustBe topic.email
      result.body mustBe topic.body
    }
  }
}
