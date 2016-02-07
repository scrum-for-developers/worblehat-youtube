package de.codecentric.psd.worblehat.domain.dto;

import de.codecentric.psd.worblehat.domain.Book;

public class BookListDTO {

    private String title;
    private String author;
    private String edition;
    private String isbn;
    private int yearOfPublication;
    private String borrower;

    public static BookListDTO createBookListDTO(Book book, String borrower){
        BookListDTO bookListDTO = new BookListDTO();
        bookListDTO.title = book.getTitle();
        bookListDTO.author = book.getAuthor();
        bookListDTO.edition = book.getEdition();
        bookListDTO.isbn = book.getIsbn();
        bookListDTO.yearOfPublication = book.getYearOfPublication();
        bookListDTO.borrower = borrower;
        return bookListDTO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }
}
