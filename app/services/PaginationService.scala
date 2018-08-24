package services

object PaginationService {
  val LIMIT = 3

  def calculateEndPage(currentPage: Int, totalPages: Int): Int = currentPage match {
    case page if page % 3 == 1 && totalPages > 1 => currentPage + 2
    case page if page % 3 == 2 && totalPages > 1 => currentPage + 1
    case _=> currentPage

  }

  def calculateStartPage(currentPage: Int): Int = currentPage match {
    case page if page % 3 == 2 => currentPage - 1
    case page if page % 3 == 1 => currentPage
    case page if page % 3 == 0 => currentPage - 2
  }

  def isPreviousLinkEnabled(currentPage: Int, startPage: Int): Boolean =
    currentPage != startPage || startPage != 1

  def isNextLinkEnabled(currentPage: Int, endPage: Int, totalPages: Int): Boolean =
    currentPage < endPage || endPage < totalPages
}