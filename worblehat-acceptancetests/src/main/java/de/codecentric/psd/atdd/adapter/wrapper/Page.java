package de.codecentric.psd.atdd.adapter.wrapper;

public enum Page {
    BOOKLIST("bookList"),
    INSERTBOOKS("insertBooks"),
    BORROWBOOK("borrow"),
    RETURNBOOKS("returnAllBooks");

    private String url;

    Page(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
