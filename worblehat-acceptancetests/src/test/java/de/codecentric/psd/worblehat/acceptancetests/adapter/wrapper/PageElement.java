package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

public enum PageElement {
    ADDBOOKBUTTON("addBook"), BOOKLIST("bookList"), BORROWBOOKBUTTON("borrowBook"),
    ISBN_ERROR("isbn.error"),
    EMAIL_ERROR("email.error"),
    AUTHOR_ERROR("author.error"),
    EDITION_ERROR("edition.error"),
    TITLE_ERROR("title.error"),
    YEAR_ERROR("year.error"),
    RETURNALLBOOKSBUTTON("returnAllBooks"), ERROR("error");


    private String elementId;

    PageElement(String elementId) {
        this.elementId = elementId;
    }

    public String getElementId() {
        return elementId;
    }
}
