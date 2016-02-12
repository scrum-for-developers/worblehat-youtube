package de.codecentric.psd.worblehat.domain;

import de.codecentric.psd.worblehat.domain.dto.BookWithBorrowerDTO;

import java.util.List;

/**
 * The interface of the domain service for books.
 */
public interface BookService {

	void returnAllBooksByBorrower(String string);

	void borrowBook(Book book, String borrower) throws BookAlreadyBorrowedException;

	Book findBookByIsbn(String isbn);

	List<Book> findAllBooks();

	List<BookWithBorrowerDTO> findBooksWithBorrower();

	void createBook(String title, String author, String edition, String isbn, int yearOfPublication);
}
