package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

public enum PageElement {
  ADDBOOKBUTTON("addBook"),
  BOOKLIST("bookList"),
  BORROWBOOKBUTTON("borrowBook"),
  ISBN_ERROR("isbn.error"),
  EMAIL_ERROR("email.error"),
  AUTHOR_ERROR("author.error"),
  EDITION_ERROR("edition.error"),
  TITLE_ERROR("title.error"),
  YEAR_ERROR("yearOfPublication.error"),
  RETURNALLBOOKSBUTTON("returnAllBooks"),
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
