package services

import org.scalatestplus.play.PlaySpec

class PaginationServiceSpec extends PlaySpec {

  "A PaginationService" must {
    "calculate the correct end page" in {
      PaginationService.calculateEndPage(1, 3) mustBe 3
      PaginationService.calculateEndPage(2, 3) mustBe 3
      PaginationService.calculateEndPage(3, 3) mustBe 3
    }

    "calculate the correct start page when total pages is 3" in {
      PaginationService.calculateStartPage(1) mustBe 1
      PaginationService.calculateStartPage(2) mustBe 1
      PaginationService.calculateStartPage(3) mustBe 1
    }

    "calculate if a next page is necessary" in {
      PaginationService.isNextLinkEnabled(3, 3, 100) mustBe true
      PaginationService.isNextLinkEnabled(1, 3, 3) mustBe true
      PaginationService.isNextLinkEnabled(3, 3, 3) mustBe false
    }

    "calculate if a previous page is necessary" in {
      PaginationService.isPreviousLinkEnabled(3, 3) mustBe true
      PaginationService.isPreviousLinkEnabled(1, 1) mustBe false
    }
  }
}
