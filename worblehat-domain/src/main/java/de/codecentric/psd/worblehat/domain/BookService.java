package de.codecentric.psd.worblehat.domain;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * The interface of the domain service for books.
 */
public interface BookService {

	void returnAllBooksByBorrower(String string);

	void borrowBook(Book book, String borrower) throws BookAlreadyBorrowedException;

	void borrowOneBook(@Nonnull Set<Book> books, String borrower) throws BookAlreadyBorrowedException;

	Set<Book> findBooksByIsbn(String isbn);

	List<Book> findAllBooks();

	Book createBook(String title, String author, String edition, String isbn, int yearOfPublication);

	boolean bookExists(String isbn);

	void deleteAllBooks();
}
