package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

public enum PageElement {
  ADD_BOOK_BUTTON("addBook"),
  BOOK_LIST("bookList"),
  BORROW_BOOK_BUTTON("borrowBook"),
  ISBN_ERROR("isbn.error"),
  EMAIL_ERROR("email.error"),
  AUTHOR_ERROR("author.error"),
  EDITION_ERROR("edition.error"),
  TITLE_ERROR("title.error"),
  YEAR_ERROR("yearOfPublication.error"),
  RETURN_ALL_BOOKS_BUTTON("returnAllBooks"),
  BORROWING_LIST("borrowingsList"),
  SHOW_BORROWED_BOOKS_BUTTON("showBorrowedBooks"),
  ERROR("error");

  private String elementId;

  PageElement(String elementId) {
    this.elementId = elementId;
  }

  public static PageElement errorFor(String field) {
    switch (field) {
      case "isbn":
        return PageElement.ISBN_ERROR;
      case "edition":
        return PageElement.EDITION_ERROR;
      case "year":
        return PageElement.YEAR_ERROR;
      default:
        throw new IllegalArgumentException("Could not find error element for " + field);
    }
  }

  public String getElementId() {
    return elementId;
  }
}
