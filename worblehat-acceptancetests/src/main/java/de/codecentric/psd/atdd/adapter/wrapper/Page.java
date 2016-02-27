package de.codecentric.psd.atdd.adapter.wrapper;

public enum Page {
    BOOKLIST("booklist"),
    INSERTBOOKS("insertBooks"),
    BORROWBOOK("borrowBook"),
    RETURNBOOKS("returnAllBooks");

    private String url;

    Page(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
