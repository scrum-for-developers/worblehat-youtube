package de.codecentric.psd.worblehat.domain;

/**
 * Exception for borrowing a book
 */
public class BookAlreadyBorrowedException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookAlreadyBorrowedException(String message) {
        super(message);
    }

}
