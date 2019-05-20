package de.codecentric.worblehat.acceptancetests.adapter.wrapper;

public enum Page {
    HOME("/"),
    BOOKLIST("bookList"),
    INSERTBOOKS("insertBooks"),
    BORROWBOOK("borrow"),
    RETURNBOOKS("returnAllBooks"),
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
