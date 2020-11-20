package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

public enum Page {
    HOME("/"),
    BOOKLIST("bookList"),
    BORROWED_BOOK_LIST("borrowedBookList"),
    INSERTBOOKS("insertBooks"),
    BORROWBOOK("borrow"),
    RETURNBOOKS("returnAllBooks"),
    REMOVEBOOK("removeBook?isbn=%s"),
    BOOKDETAILS("bookDetails?isbn=%s");

    private String url;

    Page(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getUrl(String parameter) {
        return String.format(url, parameter);
    }
}
