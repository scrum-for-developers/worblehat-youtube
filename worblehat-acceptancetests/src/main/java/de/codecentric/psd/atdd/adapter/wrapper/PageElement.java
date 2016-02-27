package de.codecentric.psd.atdd.adapter.wrapper;

public enum PageElement {
    ADDBOOKBUTTON("addBook"), BOOKLIST("bookList"), BORROWBOOKBUTTON("borrowBook"), ISBNERROR("isbn-error");


    private String elementId;

    PageElement(String elementId){
        this.elementId = elementId;
    }

    public String getElementId(){
        return elementId;
    }
}
