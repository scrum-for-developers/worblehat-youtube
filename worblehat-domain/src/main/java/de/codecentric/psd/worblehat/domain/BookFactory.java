package de.codecentric.psd.worblehat.domain;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * 
 * A Factory for Book Enitty
 * 
 * @author mahmut.can
 * 
 */
@Service
public class BookFactory {

	private final BookRepository bookRepository;

	@Inject
	BookFactory(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	};

	public Book createBook(String title, String author, String edition,
			String isbn, int year) {
		Book book = new Book(title, author, edition, isbn, year);
		bookRepository.store(book);
		return book;
	}

}
